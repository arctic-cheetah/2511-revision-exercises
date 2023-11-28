package unsw.database.query;

import unsw.database.Database;

public class GreaterThanOperator extends ComparisonOperator {

    public GreaterThanOperator(String p1, String p2, Database db) {
        super();
        getParam().add(p1);
        getParam().add(p2);
        setFunc((col, val) -> db.queryGreaterThan(col, val));
        setOperatorName(">");
    }
}
