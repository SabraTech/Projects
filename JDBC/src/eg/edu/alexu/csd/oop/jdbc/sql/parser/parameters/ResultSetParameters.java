package eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters;

/**
 * The Class ResultSetParameters.
 */
public class ResultSetParameters {

  /** The selected data. */
  private Object[][] selectedData;

  /** The columns names. */
  private String[] columnsNames;

  /** The columns data types. */
  private String[] columnsDatatypes;

  /** The table name. */
  private String tableName;

  /**
   * Instantiates a new result set parameters.
   *
   * @param data
   *          the data
   * @param columnsNames
   *          the columns names
   * @param columnsDatatypes
   *          the columns data types
   * @param tableName
   *          the table name
   */
  public ResultSetParameters(Object[][] data, String[] columnsNames, String[] columnsDatatypes,
      String tableName) {
    this.selectedData = data;
    this.columnsNames = columnsNames;
    this.columnsDatatypes = columnsDatatypes;
    this.tableName = tableName;
  }

  /**
   * Gets the selected data.
   *
   * @return the selected data
   */
  public Object[][] getSelectedData() {
    return selectedData;
  }

  /**
   * Gets the columns names.
   *
   * @return the columns names
   */
  public String[] getColumnsNames() {
    return columnsNames;
  }

  /**
   * Gets the columns datatypes.
   *
   * @return the columns datatypes
   */
  public String[] getColumnsDatatypes() {
    return columnsDatatypes;
  }

  /**
   * Gets the table name.
   *
   * @return the table name
   */
  public String getTableName() {
    return tableName;
  }

}
