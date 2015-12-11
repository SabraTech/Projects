package eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters;

/**
 * The Class ConditionalDeleteParameters.
 */
public class DeleteParameters {

  /** The table name. */
  private String tableName;

  /** The column to compare name. */
  private String columnToCompareName;

  /** The comparing char. */
  private String comparingChar;

  /** The value to compare to. */
  private String valueToCompareTo;

  /**
   * Instantiates a new conditional delete parameters.
   *
   * @param tableName
   *          the table name
   * @param columnToCompareName
   *          the column to compare name
   * @param comparingChar
   *          the comparing character
   * @param valueToCompareTo
   *          the value to compare to
   */
  public DeleteParameters(String tableName, String columnToCompareName, String comparingChar,
      String valueToCompareTo) {
    this.tableName = tableName;
    this.columnToCompareName = columnToCompareName;
    this.comparingChar = comparingChar;
    this.valueToCompareTo = valueToCompareTo;
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
   * Gets the column to compare to name.
   *
   * @return the column to compare to name
   */
  public String getColumnToCompareToName() {
    return columnToCompareName;
  }

  /**
   * Gets the comparing char.
   *
   * @return the comparing char
   */
  public String getComparingChar() {
    return comparingChar;
  }

  /**
   * Gets the value to compare to.
   *
   * @return the value to compare to
   */
  public String getValueToCompareTo() {
    return valueToCompareTo;
  }
}
