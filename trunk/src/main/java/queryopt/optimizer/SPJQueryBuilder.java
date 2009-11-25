package queryopt.optimizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;

import queryopt.entities.Atribute;
import queryopt.entities.ExecutionPlan;
import queryopt.entities.Relation;
import queryopt.optimizer.query.AggregateFunction;
import queryopt.optimizer.query.CompareSingleRowClause;
import queryopt.optimizer.query.InClause;
import queryopt.optimizer.query.JoinClause;
import queryopt.optimizer.query.Literal;
import queryopt.optimizer.query.Operator;
import queryopt.optimizer.query.SPJQuery;
import queryopt.optimizer.query.Term;
import queryopt.parser.SQL_grammarLexer;
import queryopt.parser.SQL_grammarParser;

public class SPJQueryBuilder {
	private HashMap<String, HashMap<String, Atribute>> relationsAtributes;
	private HashMap<String, Relation> namesRelations;
	private List<Relation> relationsInFrom;
	CommonTree ast;

	public SPJQueryBuilder(List<Relation> allRelations) {
		relationsAtributes = new HashMap<String, HashMap<String, Atribute>>();
		namesRelations = new HashMap<String, Relation>();
		for (Relation r : allRelations) {
			namesRelations.put(r.getName().toUpperCase(), r);
			relationsAtributes.put(r.getName().toUpperCase(), new HashMap<String, Atribute>());
			for (Atribute a : r.getAtributes())
				relationsAtributes.get(r.getName().toUpperCase()).put(a.getName().toUpperCase(), a);
		}
	}

	public void parse(String query) throws Exception {
		ast = getAstFromString(query.toUpperCase() + ";");
	}

	public SPJQuery build(ExecutionPlan executionPlan) throws Exception {
		SPJQuery spjQuery = buildSpjQueryFromAst(ast);
		spjQuery.setSystemInfo(executionPlan.getSystemInfo());
		spjQuery.getDisabledIndexes().clear();
		spjQuery.getDisabledIndexes().addAll(executionPlan.getDisabledIndexes());
		return spjQuery;
	}

	private CommonTree getAstFromString(String query) throws Exception {
		SQL_grammarLexer lexer = new SQL_grammarLexer(new ANTLRStringStream(query));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		SQL_grammarParser parser = new SQL_grammarParser(tokens);
		parser.query();
		CommonTree ast = (CommonTree) parser.query().getTree();
		if (parser.hasError())
			throw new Exception("Error at postition " + parser.getErrorPosition() + ": " + parser.getErrorMessage());
		return ast;
	}

	private SPJQuery buildSpjQueryFromAst(CommonTree ast) throws Exception {
		if (ast == null)
			throw new NullPointerException("ast");

		SPJQuery query = new SPJQuery();

		// nil -> QUERY -> [SELECT, FROM[, WHERE[,[GROUPBY[,HAVING]]]]]]
		CommonTree queryTree = ast;
		if (ast.toString().equals("nil"))
			ast = (CommonTree) ast.getChild(0);

		for (Object c : queryTree.getChildren()) {
			CommonTree child = (CommonTree) c;
			switch (child.getType()) {
			case SQL_grammarParser.SELECT:
				buildSelect(child, query);
				break;
			case SQL_grammarParser.FROM:
				relationsInFrom = buildFrom(child);
				break;
			case SQL_grammarParser.WHERE:
				buildWhere(child, query);
				break;
			default:
				throw new Exception("Should not be here child.getType()=" + child.getType());
			}
		}
		return query;
	}

	private void buildSelect(CommonTree selectTree, SPJQuery query) {
		for (Object child : selectTree.getChildren()) {

			switch (((CommonTree) child).getType()) {

			case SQL_grammarParser.AGGREGATE_FUNC:
				AggregateFunction.AggregateFunctionType type = null;
				if (child.toString().equals("SUM"))
					type = AggregateFunction.AggregateFunctionType.SUM;
				else if (child.toString().equals("AVG"))
					type = AggregateFunction.AggregateFunctionType.AVG;
				else if (child.toString().equals("MAX"))
					type = AggregateFunction.AggregateFunctionType.MAX;
				else if (child.toString().equals("MIN"))
					type = AggregateFunction.AggregateFunctionType.MIN;
				else
					throw new IllegalArgumentException("Should not be here");
				Atribute a = getAtribute(((CommonTree) child).getChild(0).toString());
				query.getProjectionTerms().add(new AggregateFunction(a, type));
				break;

			case SQL_grammarParser.NAME:
				query.getProjectionTerms().add(getAtribute(child.toString()));
				break;
			case SQL_grammarParser.LITERAL:
				query.getProjectionTerms().add(new Literal(((CommonTree) child).getChild(0).toString()));
				break;
			case SQL_grammarParser.STAR:
				for (Relation r : relationsInFrom)
					query.getProjectionTerms().addAll(r.getAtributes());
				break;
			default:
				throw new IllegalArgumentException("Should not be here");
			}
		}
	}

	private List<Relation> buildFrom(CommonTree fromTree) {
		List<Relation> relationsInFrom = new ArrayList<Relation>();
		for (Object child : fromTree.getChildren()) {
			switch (((CommonTree) child).getType()) {
			case SQL_grammarParser.NAME:
				if (!relationsAtributes.containsKey(child.toString()))
					throw new IllegalArgumentException("Table " + child.toString() + " is not defined.");
				else
					relationsInFrom.add(namesRelations.get(child.toString()));
				break;
			default:
				throw new IllegalArgumentException("Should not be here");
			}
		}
		return relationsInFrom;
	}

	private void buildWhere(CommonTree whereTree, SPJQuery query) throws Exception {
		for (Object whereBlockTree : whereTree.getChildren())
			buildWhereBlock((CommonTree) whereBlockTree, query);

	}

	private void buildWhereBlock(CommonTree whereBlockTree, SPJQuery query) throws Exception {
		Operator operator;
		switch (whereBlockTree.getType()) {

		case SQL_grammarParser.EQ:
			operator = Operator.EQ;
		case SQL_grammarParser.DIFF:
			operator = Operator.DIFF;
		case SQL_grammarParser.GT:
			operator = Operator.GT;
		case SQL_grammarParser.LS:
			operator = Operator.LS;
		case SQL_grammarParser.GT_EQ:
			operator = Operator.GT_EQ;
		case SQL_grammarParser.LS_EQ:
			operator = Operator.LS_EQ;

			Term operand1 = null;
			Term operand2 = null;
			CommonTree leftOperand = (CommonTree) whereBlockTree.getChild(0);
			CommonTree rightOperand = (CommonTree) whereBlockTree.getChild(1);

			if (leftOperand.getType() == SQL_grammarParser.NAME)
				operand1 = getAtribute(leftOperand.toString());
			else if (leftOperand.getType() == SQL_grammarParser.LITERAL)
				operand1 = new Literal(leftOperand.getChild(0).toString());
			else if (leftOperand.getType() == SQL_grammarParser.QUERY)
				operand1 = buildSpjQueryFromAst(leftOperand);
			else
				throw new IllegalArgumentException("Should not be here");

			if (rightOperand.getType() == SQL_grammarParser.NAME)
				operand2 = getAtribute(rightOperand.toString());
			else if (rightOperand.getType() == SQL_grammarParser.LITERAL)
				operand2 = new Literal(rightOperand.getChild(0).toString());
			else if (rightOperand.getType() == SQL_grammarParser.QUERY)
				operand2 = buildSpjQueryFromAst(rightOperand);
			else
				throw new IllegalArgumentException("Should not be here");

			if (operand1 instanceof Atribute && operand2 instanceof Atribute)
				query.getJoinClauses().add(new JoinClause(operator, (Atribute) operand1, (Atribute) operand2));
			else
				query.getSelectionCnfClauses().add(new CompareSingleRowClause(operator, operand1, operand2));

			break;

		case SQL_grammarParser.IN:
			CommonTree atribute = (CommonTree) whereBlockTree.getChild(0);
			CommonTree subquery = (CommonTree) whereBlockTree.getChild(1);
			if (atribute.getType() == SQL_grammarParser.QUERY)
				throw new IllegalArgumentException("A sub query cannot be on the left side of an IN clause");
			if (subquery.getType() != SQL_grammarParser.QUERY)
				throw new IllegalArgumentException("The right side of an IN clause must be a subquery");
			Atribute atr = getAtribute(atribute.toString());
			SPJQuery subq = buildSpjQueryFromAst(subquery);
			query.getSelectionCnfClauses().add(new InClause(atr, subq));
			break;
		default:
			throw new IllegalArgumentException("Should not be here");
		}
	}

	private Atribute getAtribute(String atributeName) {
		Atribute a = null;
		if (atributeName.contains("\\.")) {
			String[] split = atributeName.split("\\.");
			String tableName = split[0];
			String atrName = split[1];
			if (relationsAtributes.containsKey(tableName))
				if (relationsAtributes.get(tableName).containsKey(atrName))
					return relationsAtributes.get(tableName).get(atrName);
				else
					throw new IllegalArgumentException("Atribute " + atributeName + " is not defined");
			else
				throw new IllegalArgumentException("Atribute " + atributeName + " is not defined");
		}
		for (String r : relationsAtributes.keySet())
			if (relationsAtributes.get(r).containsKey(atributeName))
				if (a == null)
					a = relationsAtributes.get(r).get(atributeName);
				else
					throw new IllegalArgumentException("Atribute name " + atributeName + " is ambiguous");
		if (a == null)
			throw new IllegalArgumentException("Atribute name " + atributeName + " is not defined");
		return a;
	}
}
