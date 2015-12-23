package eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters;

import eg.edu.alexu.csd.oop.jdbc.sql.parser.MyEntry;

import java.util.ArrayList;

/**
 * The Class TableCreationParameters.
 */
public class TableCreationParameters {

  /** The table name. */
  private String tableName;

  /** The columns and their classes. */
  private ArrayList<MyEntry<String, Class<?>>> columnsAndClasses;

  /**
   * Instantiates a new table creation parameters.
   *
   * @param tableName
   *          the table name
   * @param columnsAndClasses
   *          the columns and their classes
   */
  public TableCreationParameters(String tableName,
      ArrayList<MyEntry<String, Class<?>>> columnsAndClasses) {
    this.tableName = tableName;
    this.columnsAndClasses = columnsAndClasses;
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
   * Gets the columns names and classes.
   *
   * @return the columns names and classes
   */
  public ArrayList<MyEntry<String, Class<?>>> getColumnsNamesAndClasses() {
    return columnsAndClasses;
  }
}
