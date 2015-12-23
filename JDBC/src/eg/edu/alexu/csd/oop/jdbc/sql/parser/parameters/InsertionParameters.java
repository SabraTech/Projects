package eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class InsertionParameters.
 */
public class InsertionParameters {

  /** The table name. */
  private String tableName;

  /** The columns names. */
  private ArrayList<String> columnsNames;

  /** The values. */
  private ArrayList<Object> values;

  /** The boolean to know if column names were specified. */
  boolean columnNamesSpecified;

  /**
   * Instantiates a new insertion parameters when columns names are specified.
   *
   * @param tableName
   *          the table name
   * @param columnsNames
   *          the columns names
   * @param values
   *          the values
   */
  public InsertionParameters(String tableName, ArrayList<String> columnsNames,
      ArrayList<Object> values) {
    this.tableName = tableName;
    this.columnsNames = columnsNames;
    this.values = values;
    columnNamesSpecified = true;
  }

  /**
   * Instantiates a new insertion parameters when columns names are not
   * specified.
   *
   * @param tableName
   *          the table name
   * @param values
   *          the values
   */
  public InsertionParameters(String tableName, ArrayList<Object> values) {
    this.tableName = tableName;
    this.values = values;
    columnNamesSpecified = false;
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
   * Gets the columns names.
   *
   * @return the columns names throws a runtime exception if the columns names
   *         are not specified
   */
  public ArrayList<String> getColumnsNames() {
    return columnsNames;
  }

  /**
   * Gets the values.
   *
   * @return the values
   */
  public ArrayList<Object> getValues() {
    return values;
  }

  /**
   * Column names specified.
   *
   * @return true, if column names are specified
   */
  public boolean columnNamesSpecified() {
    return columnNamesSpecified;
  }
}
