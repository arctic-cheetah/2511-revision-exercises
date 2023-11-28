package unsw.database.query;

import java.util.ArrayList;
import java.util.List;

import unsw.database.Database;
import unsw.database.Query;

public class QueryFactory {
    public static Query queryFactory(List<String> tokens, Database db, Query q) {

        List<Query> queries = new ArrayList<>();
        String prev = "";
        String next = "";
        int i = 0;
        while (i < tokens.size()) {
            String s = tokens.remove(i);
            // Check if an operator or value
            if (isBooleanOperator(s)) {
                if (s.equals("AND")) {
                } else {
                }
            } else if (isComparatorOperator(s)) {
                // Comparator operators need to obtain the prev and next values to be
                // initialised
                i++;
                next = tokens.remove(i);
                if (s.equals("=")) {
                    queries.add(new EqualOperator(prev, next, db));
                } else {
                    queries.add(new GreaterThanOperator(next, s, db));
                }
                // Reset pointers
                prev = "";
                next = "";
                continue;

            } else {
                // It's a value!
                prev = s;
            }

            i++;
        }

        return q;
    }

    private static boolean isBooleanOperator(String s) {
        return Query.BOOLEAN_OPERATOR.contains(s);
    }

    private static boolean isComparatorOperator(String s) {
        return Query.COMPARISON_OPERATOR.contains(s);
    }
}
