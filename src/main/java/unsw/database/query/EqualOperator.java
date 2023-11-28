package unsw.database.query;

import unsw.database.Database;

public class EqualOperator extends ComparisonOperator {

    public EqualOperator(String p1, String p2, Database db) {
        super();
        getParam().add(p1);
        getParam().add(p2);
        setFunc((col, val) -> db.querySimpleToRow(col, val));
        setOperatorName("=");
    }
}
