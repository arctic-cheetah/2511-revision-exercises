package unsw.database.query;

import unsw.database.Database;
import unsw.database.Query;

public class OrOperator extends BooleanOperator {
    public OrOperator(Query q1, Query q2, Database db) {
        left.equals(q1);
        right.equals(q2);
        func = (r1, r2) -> db.orQuery(r1, r2);
        setOperatorName("OR");
    }

    public OrOperator(Database db) {
        func = (r1, r2) -> db.orQuery(r1, r2);
        setOperatorName("OR");
    }
}
