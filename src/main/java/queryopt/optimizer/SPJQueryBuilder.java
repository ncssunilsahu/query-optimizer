package queryopt.optimizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;

import queryopt.entities.Atribute;
import queryopt.entities.Relation;
import queryopt.optimizer.entities.AggregateFunction;
import queryopt.optimizer.entities.Clause;
import queryopt.optimizer.entities.CompareSingleRowClause;
import queryopt.optimizer.entities.InClause;
import queryopt.optimizer.entities.Operator;
import queryopt.optimizer.entities.SPJQuery;
import queryopt.optimizer.entities.Term;
import queryopt.parser.SelectQueryGrammarLexer;
import queryopt.parser.SelectQueryGrammarParser;

public class SPJQueryBuilder {
	private HashMap<String, HashMap<String, Atribute>> relationsAtributes;

	public SPJQueryBuilder(List<Relation> allRelations) {
		relationsAtributes = new HashMap<String, HashMap<String, Atribute>>();
		for (Relation r : allRelations) {
			relationsAtributes.put(r.getName().toUpperCase(), new HashMap<String, Atribute>());
			for (Atribute a : r.getAtributes())
				relationsAtributes.get(r.getName().toUpperCase()).put(a.getName().toUpperCase(), a);
		}
	}

	public SPJQuery build(String query) throws Exception {
		CommonTree ast = getAstFromString(query.toUpperCase() + ";");
		return buildSpjQueryFromAst(ast);
	}

	private CommonTree getAstFromString(String query) throws Exception {
		SelectQueryGrammarLexer lexer = new SelectQueryGrammarLexer(new ANTLRStringStream(query));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		SelectQueryGrammarParser parser = new SelectQueryGrammarParser(tokens);
		CommonTree ast = (CommonTree) parser.query().getTree();
		if (parser.hasError())
			throw new Exception("Error at postition " + parser.getErrorPosition() + ": " + parser.getErrorMessage());
		return ast;
	}

	private SPJQuery buildSpjQueryFromAst(CommonTree ast) {
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
			case SelectQueryGrammarParser.SELECT:
				buildSelect(child, query);
				break;
			case SelectQueryGrammarParser.FROM:
				buildFrom(child);
				break;
			case SelectQueryGrammarParser.WHERE:
				buildWhere(child, query);
				break;
			default:
				return null;
			}
		}
		return null;
	}

	private void buildSelect(CommonTree selectTree, SPJQuery query) {
		query.setProjectionTerms(new ArrayList<Term>());

		for (Object child : selectTree.getChildren()) {

			switch (((CommonTree) child).getType()) {

			case SelectQueryGrammarParser.AGGREGATE_FUNC:
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

			case SelectQueryGrammarParser.NAME:
				query.getProjectionTerms().add(getAtribute(child.toString()));
				break;
			default:
				throw new IllegalArgumentException("Should not be here");
			}
		}
	}

	private void buildFrom(CommonTree fromTree) {
		for (Object child : fromTree.getChildren()) {
			switch (((CommonTree) child).getType()) {
			case SelectQueryGrammarParser.NAME:
				if (!relationsAtributes.containsKey(child.toString()))
					throw new IllegalArgumentException("Table " + child.toString() + " is not defined.");
				break;
			default:
				throw new IllegalArgumentException("Should not be here");
			}
		}
	}

	private void buildWhere(CommonTree whereTree, SPJQuery query) {
		query.setSelectionCnfClauses(new ArrayList<Clause>());
		for (Object child : whereTree.getChildren()) {
			switch (((CommonTree) child).getType()) {
			case SelectQueryGrammarParser.AND:
				for (Object whereBlockTree : whereTree.getChildren()) {
					buildWhereBlock((CommonTree) whereBlockTree, query);
				}
				break;
			case SelectQueryGrammarParser.OP:
			case SelectQueryGrammarParser.IN:
				buildWhereBlock((CommonTree) child, query);
				break;
			default:
				throw new IllegalArgumentException("Should not be here");
			}
		}
	}

	private void buildWhereBlock(CommonTree whereBlockTree, SPJQuery query) {
		switch (whereBlockTree.getType()) {
		case SelectQueryGrammarParser.OP:
			Operator operator = Operator.EQ;
			if (whereBlockTree.toString().equals("="))
				operator = Operator.EQ;
			else if (whereBlockTree.toString().equals("<"))
				operator = Operator.LS;
			else if (whereBlockTree.toString().equals(">"))
				operator = Operator.GT;
			else if (whereBlockTree.toString().equals("<="))
				operator = Operator.LS_EQ;
			else if (whereBlockTree.toString().equals(">="))
				operator = Operator.GT_EQ;
			else if (whereBlockTree.toString().equals("<>"))
				operator = Operator.DIFF;
			else
				throw new IllegalArgumentException("Should not be here");

			Term operand1 = null;
			Term operand2 = null;
			CommonTree leftOperand = (CommonTree) whereBlockTree.getChild(0);
			CommonTree rightOperand = (CommonTree) whereBlockTree.getChild(1);

			if (leftOperand.getType() == SelectQueryGrammarParser.NAME)
				operand1 = getAtribute(leftOperand.toString());
			else if (leftOperand.getType() == SelectQueryGrammarParser.QUERY)
				operand1 = buildSpjQueryFromAst(leftOperand);
			else
				throw new IllegalArgumentException("Should not be here");

			if (rightOperand.getType() == SelectQueryGrammarParser.NAME)
				operand2 = getAtribute(rightOperand.toString());
			else if (rightOperand.getType() == SelectQueryGrammarParser.QUERY)
				operand2 = buildSpjQueryFromAst(rightOperand);
			else
				throw new IllegalArgumentException("Should not be here");

			query.getSelectionCnfClauses().add(new CompareSingleRowClause(operator, operand1, operand2));
			break;
		case SelectQueryGrammarParser.IN:
			CommonTree atribute = (CommonTree) whereBlockTree.getChild(0);
			CommonTree subquery = (CommonTree) whereBlockTree.getChild(1);
			if (atribute.getType() == SelectQueryGrammarParser.QUERY)
				throw new IllegalArgumentException("A sub query cannot be on the left side of an IN clause");
			if (subquery.getType() != SelectQueryGrammarParser.QUERY)
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
		for (String rname : relationsAtributes.keySet()) {
			System.out.println("rel:" + rname);
			for (String aname : relationsAtributes.get(rname).keySet()) {
				System.out.print("a:" + aname + " ");
			}
		}
		if (a == null)
			throw new IllegalArgumentException("Atribute name " + atributeName + " not defined");
		return a;
	}
}