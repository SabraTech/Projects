package eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters;

import java.util.ArrayList;

/**
 * The Class SelectionParameters.
 */
public class SelectionParameters {

  /** The table name. */
  private String tableName;

  /** The columns to select. */
  private ArrayList<String> columnsToSelect;

  /** The column to select name. */
  private String columnToSelectName;

  /** The column to compare. */
  private String columnToCompare;

  /** The compare char. */
  private String compareChar;

  /** The value to compare to. */
  private String valueToCompareTo;

  /** The boolean to know if query is select column with condition. */
  private boolean queryIsSelectColumnWithCondition;

  /** The boolean to know query is select all. */
  private boolean queryIsSelectAll;

  /**
   * Instantiates a new selection parameters for a select column with condition
   * query.
   *
   * @param columnName
   *          the column name
   * @param tableName
   *          the table name
   * @param columnToCompareWith
   *          the column to compare with
   * @param comparingChar
   *          the comparing char
   * @param value
   *          the value
   */
  public SelectionParameters(String columnName, String tableName, String columnToCompareWith,
      String comparingChar, String value) {
    this.columnToSelectName = columnName;
    this.tableName = tableName;
    this.columnToCompare = columnToCompareWith;
    this.compareChar = comparingChar;
    this.valueToCompareTo = value;
    queryIsSelectColumnWithCondition = true;
    queryIsSelectAll = false;
  }

  /**
   * Instantiates a new selection parameters for a select all query.
   *
   * @param columnsToSelect
   *          the columns to select
   * @param tableName
   *          the table name
   */
  public SelectionParameters(ArrayList<String> columnsToSelect, String tableName) {
    this.columnsToSelect = columnsToSelect;
    this.tableName = tableName;
    queryIsSelectColumnWithCondition = false;
    queryIsSelectAll = true;
  }

  /**
   * Instantiates a new selection parameters for a select all with condition
   * query.
   *
   * @param tableName
   *          the table name
   * @param columnName
   *          the column name
   * @param compareChar
   *          the compare char
   * @param value
   *          the value
   */
  public SelectionParameters(String tableName, String columnName, String compareChar,
      String value) {
    this.tableName = tableName;
    this.columnToCompare = columnName;
    this.compareChar = compareChar;
    this.valueToCompareTo = value;
    queryIsSelectColumnWithCondition = false;
    queryIsSelectAll = false;
  }

  /**
   * Gets the table name.
   *
   * @return the table name
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * Gets the column to select name.
   *
   * @return the column to select name
   */
  public String getColumnToSelectName() {
    return columnToSelectName;
  }

  /**
   * Gets the column to compare name.
   *
   * @return the column to compare name
   */
  public String getColumnToCompareName() {
    return columnToCompare;
  }

  /**
   * Gets the comparing char.
   *
   * @return the comparing char
   */
  public String getComparingChar() {
    return compareChar;
  }

  /**
   * Gets the value to compare to.
   *
   * @return the value to compare to
   */
  public String getValueToCompareTo() {
    return valueToCompareTo;
  }

  /**
   * Gets the columns to select names.
   *
   * @return the columns to select names
   */
  public ArrayList<String> getColumnsToSelectNames() {
    return columnsToSelect;
  }

  public boolean getqueryIsSelectAll() {
    return queryIsSelectAll;
  }

  public boolean getqueryIsSelectColumnWithCondition() {
    return queryIsSelectColumnWithCondition;
  }
}
