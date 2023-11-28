package unsw.database;

public class Column {
    public enum ColumnType {
        MARK, // Int
        TEXT; // string
    }

    private String name;
    private ColumnType type;

    public Column(String name, ColumnType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ColumnType getType() {
        return type;
    }
}
