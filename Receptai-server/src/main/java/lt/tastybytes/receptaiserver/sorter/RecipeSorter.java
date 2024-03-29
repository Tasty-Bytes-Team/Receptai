package lt.tastybytes.receptaiserver.sorter;

public enum RecipeSorter implements BaseSorter {
    NAME("name"),
    DATE_CREATED("dateCreated");

    private final String columnName;

    RecipeSorter(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public BaseSorter findByColumn(String columnName) {
        for (var entry : values()) {
            if (entry.getColumnName().equals(columnName))
                return entry;
        }
        return null;
    }
}
