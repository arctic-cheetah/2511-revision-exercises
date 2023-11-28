package unsw.database.query;

import java.util.ArrayList;
import java.util.List;

import unsw.database.Database;
import unsw.database.Query;

public class QueryFactory {
    public static Query queryFactory(List<String> tokens, Database db, Query q) {
        // Convert the queries into a tree!
        List<Query> qs = queryFactoryIntialiser(tokens, db);
        return queryFactoryRecursion(qs);
    }

    // Make the query into a tree!
    // Assign the left and right nodes for the boolean Operators
    // Split the tree in half! to get the root

    public static Query queryFactoryRecursion(List<Query> qs) {

        if (qs.size() == 1) {
            return qs.get(0);
        }
        int half = qs.size() / 2;
        BooleanOperator root;
        Query checkRoot = qs.get(half);
        if (checkRoot instanceof BooleanOperator) {
            root = (BooleanOperator) qs.get(half);

        } else {
            // The centre root is a comparison node. Find the next node that is boolean operator
            // and take it from there
            half--;
            root = (BooleanOperator) qs.get(half);
        }
        Query left = queryFactoryRecursion(qs.subList(0, half));
        Query right = queryFactoryRecursion(qs.subList(half + 1, qs.size()));
        root.setLeft(left);
        root.setRight(right);

        return root;
    }

    public static List<Query> queryFactoryIntialiser(List<String> tokens, Database db) {

        List<Query> queries = new ArrayList<>();
        String prev = "";
        String next = "";
        while (!tokens.isEmpty()) {
            String s = tokens.remove(0);
            // Check if an operator or value
            if (isBooleanOperator(s)) {
                // Check the type of boolean operator. We will also need to defer assignment of
                // left and right as we are parsing the tokens
                if (s.equals("AND")) {
                    queries.add(new AndOperator(db));
                } else {
                    queries.add(new OrOperator(db));
                }
            } else if (isComparatorOperator(s)) {
                // Comparator operators need to obtain the prev and next values to be
                // initialised
                next = tokens.remove(0);
                if (s.equals("=")) {
                    queries.add(new EqualOperator(prev, next, db));
                } else {
                    queries.add(new GreaterThanOperator(prev, next, db));
                }
                // Reset pointers
                prev = "";
                next = "";

            } else {
                // It's a value!
                prev = s;
            }
        }
        return queries;
    }

    private static boolean isBooleanOperator(String s) {
        return Query.BOOLEAN_OPERATOR.contains(s);
    }

    private static boolean isComparatorOperator(String s) {
        return Query.COMPARISON_OPERATOR.contains(s);
    }
}
