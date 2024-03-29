package lt.tastybytes.receptaiserver.sorter;

public interface BaseSorter {
    String getColumnName();
    BaseSorter findByColumn(String columnName);
}
