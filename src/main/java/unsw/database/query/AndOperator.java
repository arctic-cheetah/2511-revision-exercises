package unsw.database.query;

import unsw.database.Database;
import unsw.database.Query;

public class AndOperator extends BooleanOperator {
    public AndOperator(Query q1, Query q2, Database db) {
        left.equals(q1);
        right.equals(q2);
        func = (r1, r2) -> db.andQuery(r1, r2);
        setOperatorName("AND");
    }

    public AndOperator(Database db) {
        func = (r1, r2) -> db.andQuery(r1, r2);
        setOperatorName("AND");
    }
}
