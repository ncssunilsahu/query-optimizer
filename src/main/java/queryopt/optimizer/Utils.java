package queryopt.optimizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import queryopt.entities.Atribute;
import queryopt.entities.Index;
import queryopt.entities.IndexAtribute;
import queryopt.entities.Relation;
import queryopt.entities.SystemInfo;
import queryopt.optimizer.query.AggregateFunction;
import queryopt.optimizer.query.Clause;
import queryopt.optimizer.query.CompareSingleRowClause;
import queryopt.optimizer.query.Literal;
import queryopt.optimizer.query.SPJQuery;
import queryopt.optimizer.query.SingleRelationQuery;
import queryopt.optimizer.query.Term;

public class Utils {

	public static Relation getOutputRelation(SingleRelationQuery srquery) {
		Relation outputRelation = copy(srquery.getRelation());

		outputRelation.getAtributes().clear();
		outputRelation.getAtributes().addAll(getAllAtributesInSingleRelationQuery(srquery));

		double rowsFactor = 1.0;
		for (Clause clause : srquery.getSelectionCnfClauses())
			rowsFactor *= clause.getSelectivity();
		int rows = (int) (rowsFactor * srquery.getRelation().getNoOfRows());
		outputRelation.setNoOfRows(rows);
		return outputRelation;
	}

	private static Relation copy(Relation r) {
		Relation copy = new Relation();
		copy.setRelationId(r.getRelationId());
		copy.setName(r.getName());
		copy.setBlockingFactor(r.getBlockingFactor());
		copy.setNoOfRows(r.getNoOfRows());
		copy.setSchema(r.getSchema());

		copy.setIndexes(new ArrayList<Index>());
		copy.getIndexes().addAll(r.getIndexes());

		copy.setAtributes(new ArrayList<Atribute>());
		copy.getAtributes().addAll(r.getAtributes());
		return copy;
	}

	private static List<Atribute> getAllAtributesInSingleRelationQuery(SingleRelationQuery srquery) {
		List<Atribute> allAtributesInQuery = new ArrayList<Atribute>();

		allAtributesInQuery.addAll(srquery.getGroupingAtributes());
		allAtributesInQuery.addAll(srquery.getProjectionAtributes());

		for (Clause clause : srquery.getSelectionCnfClauses())
			allAtributesInQuery.addAll(clause.getAtributes());

		// Eliminate duplicate Attributes
		HashSet<Atribute> hs = new HashSet<Atribute>(allAtributesInQuery);
		allAtributesInQuery.clear();
		allAtributesInQuery.addAll(hs);

		return allAtributesInQuery;
	}

	/*
	 * // TODO private static Relation performSelection(Relation relation,
	 * List<Clause> selectionClauses) { return null; }
	 * 
	 * // TODO private static Relation projectOutSingleRelation(Relation
	 * relation, SPJQuery query) { return null; }
	 */

	// Treba i nesto kako project out atributes after Join
	public static HashMap<Clause, List<Index>> getMatchingSelectionIndexes(SingleRelationQuery srquery, List<Index> indexes)
			throws Exception {
		HashMap<Clause, List<Index>> matchingSelectionIndexes = new HashMap<Clause, List<Index>>();

		for (Clause clause : srquery.getSelectionCnfClauses()) {
			if (clause instanceof CompareSingleRowClause) {
				CompareSingleRowClause csrclause = (CompareSingleRowClause) clause;
				Atribute atribute = null;
				if (csrclause.getOperand1() instanceof Literal && csrclause.getOperand2() instanceof Atribute)
					atribute = (Atribute) csrclause.getOperand2();
				else if (csrclause.getOperand1() instanceof Atribute && csrclause.getOperand2() instanceof Literal)
					atribute = (Atribute) csrclause.getOperand1();

				if (atribute != null)
					for (Index index : indexes)
						if (atribute.equals(index.getIndexAtributes().get(0))) {
							if (!matchingSelectionIndexes.containsKey(clause))
								matchingSelectionIndexes.put(clause, new ArrayList<Index>());
							matchingSelectionIndexes.get(clause).add(index);
						}
			} else
				throw new Exception("Not implemented yet");
		}
		return matchingSelectionIndexes;
	}

	public static boolean isPkIndex(Index index) {
		for (Atribute a : index.getRelation().getAtributes()) {
			if (!a.isPk())
				continue;
			boolean found = false;
			for (IndexAtribute ia : index.getIndexAtributes())
				if (ia.getAtribute().equals(a)) {
					found = true;
					break;
				}
			if (!found)
				return false;
		}
		return true;
	}

	public static int getRowSizeInBytes(Relation r) {
		int rowSizeInBytes = 0;
		for (Atribute a : r.getAtributes())
			rowSizeInBytes += a.getSizeInBytes();
		return rowSizeInBytes;
	}

	public static long getRelationSizeInPages(Relation r, SystemInfo systemInfo) {
		int pagesize = systemInfo.getPageSizeInBytes();
		return getRelationSizeInBytes(r, systemInfo) / pagesize;
	}

	public static long getRelationSizeInBytes(Relation r, SystemInfo systemInfo) {
		long rowsize = Utils.getRowSizeInBytes(r);
		int rows = r.getNoOfRows();
		int blockingFactor = r.getBlockingFactor();
		return rowsize * rows * blockingFactor;
	}

	public static long getNoOfFirstLevelIndexPages(Index index, SystemInfo systemInfo) {
		int rows = index.getRelation().getNoOfRows();
		int blockingFactor = systemInfo.getBlockingFactorIndexFirstLevelRows();
		int ridSize = systemInfo.getRidSizeInBytes();
		int pageSize = systemInfo.getPageSizeInBytes();
		return (long) Math.ceil(rows * blockingFactor * ridSize / pageSize);
	}

	public static boolean areAllAtributesInIndexAndSelectionClauseIsNotPrefix(Index index, SingleRelationQuery srquery) {
		List<Atribute> allAtributesInQuery = getAllAtributesInSingleRelationQuery(srquery);

		for (Atribute a : allAtributesInQuery) {
			boolean found = false;
			for (IndexAtribute ia : index.getIndexAtributes())
				if (ia.getAtribute().equals(a))
					found = true;
			if (!found)
				return false;
		}

		return true;
	}

	public static boolean areAllAtributesInIndexAndSelectionClauseIsPrefix(Index index, SingleRelationQuery srquery) {
		List<Atribute> allAtributesInQuery = getAllAtributesInSingleRelationQuery(srquery);

		List<Atribute> selectionClause = new ArrayList<Atribute>();
		for (Clause clause : srquery.getSelectionCnfClauses())
			selectionClause.addAll(clause.getAtributes());

		for (Atribute a : allAtributesInQuery) {
			boolean found = false;
			for (IndexAtribute ia : index.getIndexAtributes())
				if (ia.getAtribute().equals(a))
					found = true;
			if (!found)
				return false;
		}

		// We should secure that the index contains all the selection (WHERE)
		// attributes in a prefix on its list of attributes
		boolean notContainPrevious = false;
		for (IndexAtribute ia : index.getIndexAtributes())
			if (selectionClause.contains(ia.getAtribute()))
				if (notContainPrevious == true)
					return false;
				else
					notContainPrevious = true;

		return true;
	}

	public static HashMap<Relation, SingleRelationQuery> getSingleRelationQueriesFromSPJQuery(SPJQuery query) {
		HashMap<Relation, SingleRelationQuery> srqueries = new HashMap<Relation, SingleRelationQuery>();

		for (Term term : query.getProjectionTerms())
			resolveTerm(term, srqueries, query.getSystemInfo());
		for (Atribute atribute : query.getGroupingAtributes())
			resolveTerm(atribute, srqueries, query.getSystemInfo());

		for (Clause clause : query.getSelectionCnfClauses())
			if (!clause.isJoinClause())
				for (Atribute a : clause.getAtributes()) {
					if (!srqueries.containsKey(a.getRelation()))
						srqueries.put(a.getRelation(), new SingleRelationQuery(query.getSystemInfo(), a.getRelation()));
					srqueries.get(a.getRelation()).getSelectionCnfClauses().add(clause);
				}

		return srqueries;
	}

	private static void resolveTerm(Term term, HashMap<Relation, SingleRelationQuery> srqueries, SystemInfo systemInfo) {
		Atribute atribute = null;
		if (term instanceof Atribute)
			atribute = (Atribute) term;
		else if (term instanceof AggregateFunction)
			atribute = ((AggregateFunction) term).getAtribute();
		if (atribute != null) {
			if (!srqueries.containsKey(atribute.getRelation()))
				srqueries.put(atribute.getRelation(), new SingleRelationQuery(systemInfo, atribute.getRelation()));
			srqueries.get(atribute.getRelation()).getProjectionAtributes().add(atribute);
		}
	}
}
