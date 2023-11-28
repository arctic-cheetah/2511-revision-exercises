package unsw.database;

import java.util.ArrayList;
import java.util.List;

public class Column {
    public enum ColumnType {
        MARK, // Int
        TEXT; // string
    }

    private String name;
    private ColumnType type;
    private List<Row> publisher = new ArrayList<>();
    List<String> dependencies = new ArrayList<>();
    private boolean isDerived = false;

    public Column(String name, ColumnType type) {
        this.name = name;
        this.type = type;
        isDerived = false;
    }

    public Column(String name, ColumnType type, List<String> dependencies) {
        this.name = name;
        this.type = type;
        this.dependencies = dependencies;
        isDerived = true;
    }

    public String getName() {
        return name;
    }

    public ColumnType getType() {
        return type;
    }

    public boolean isDerived() {
        return isDerived;
    }
    // public addPublisher() {

    // }
}
