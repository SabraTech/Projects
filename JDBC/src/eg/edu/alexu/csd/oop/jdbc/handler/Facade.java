package eg.edu.alexu.csd.oop.jdbc.handler;

import eg.edu.alexu.csd.oop.jdbc.sql.parser.MyEntry;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.DeleteParameters;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.InsertionParameters;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.SelectionParameters;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.TableCreationParameters;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.UpdateParameters;

import java.util.ArrayList;

/**
 * The Class Facade.
 */
public class Facade {

  /** The table name. */
  private String tableName;

  /** The column to compare name. */
  private String columnToCompareName;

  /** The comparing character. */
  private String comparingChar;

  /** The value to compare to. */
  private String valueToCompareTo;

  /** The columns names. */
  private ArrayList<String> columnsNames;

  /** The values. */
  private ArrayList<Object> values;

  /** The column to select name. */
  private String columnToSelectName;

  /** The columns and their classes. */
  private ArrayList<MyEntry<String, Class<?>>> columnsAndClasses;

  /** The required. */
  private ArrayList<MyEntry<String, String>> requiredToSetColumnsAndValues;

  /**
   * Instantiates a new facade.
   *
   * @param parameters
   *          the parameters
   */
  public Facade(TableCreationParameters parameters) {
    this.tableName = parameters.getTableName();
    this.columnsAndClasses = parameters.getColumnsNamesAndClasses();
  }

  /**
   * Instantiates a new facade.
   *
   * @param parameters
   *          the parameters
   */
  public Facade(SelectionParameters parameters) {
    this.tableName = parameters.getTableName();
    this.columnsNames = parameters.getColumnsToSelectNames();
    this.columnToSelectName = parameters.getColumnToSelectName();
    this.columnToCompareName = parameters.getColumnToCompareName();
    this.comparingChar = parameters.getComparingChar();
    this.valueToCompareTo = parameters.getValueToCompareTo();
  }

  /**
   * Instantiates a new facade.
   *
   * @param parameters
   *          the parameters
   */
  public Facade(UpdateParameters parameters) {
    this.tableName = parameters.getTableName();
    this.requiredToSetColumnsAndValues = parameters.getRequiredColumnsNamesAndValues();
    this.columnToCompareName = parameters.getColumnToCompareToName();
    this.comparingChar = parameters.getComparingChar();
    this.valueToCompareTo = parameters.getValueToCompareTo();
  }

  /**
   * Instantiates a new facade.
   *
   * @param parameters
   *          the delete parameters
   */
  public Facade(DeleteParameters parameters) {
    this.tableName = parameters.getTableName();
    this.columnToCompareName = parameters.getColumnToCompareToName();
    this.comparingChar = parameters.getComparingChar();
    this.valueToCompareTo = parameters.getValueToCompareTo();
  }

  /**
   * Instantiates a new facade.
   *
   * @param parameters
   *          the insert parameters
   */
  public Facade(InsertionParameters parameters) {
    this.tableName = parameters.getTableName();
    this.columnsNames = parameters.getColumnsNames();
    this.values = parameters.getValues();
  }

  /**
   * Gets the table name.
   *
   * @return the table name
   */
  public String getTableName() {
    if (tableName == null) {
      throw new RuntimeException("wrong method called");
    }
    return tableName;
  }

  /**
   * Gets the column to select name.
   *
   * @return the column to select name
   */
  public String getColumnToSelectName() {
    if (columnToSelectName == null) {
      throw new RuntimeException("wrong method called");
    }
    return columnToSelectName;
  }

  /**
   * Gets the columns names.
   *
   * @return the columns names
   */
  // for insert
  public ArrayList<String> getColumnsNames() {
    if (columnsNames == null) {
      throw new RuntimeException("wrong method called");
    }
    return columnsNames;
  }

  /**
   * Gets the values.
   *
   * @return the values
   */
  public ArrayList<Object> getValues() {
    if (values == null) {
      throw new RuntimeException("wrong method called");
    }
    return values;
  }

  /**
   * Gets the column to compare to name.
   *
   * @return the column to compare to name
   */
  // for delete
  public String getColumnToCompareToName() {
    if (columnToCompareName == null) {
      throw new RuntimeException("wrong method called");
    }
    return columnToCompareName;
  }

  /**
   * Gets the comparing char.
   *
   * @return the comparing char
   */
  public String getComparingChar() {
    if (comparingChar == null) {
      throw new RuntimeException("wrong method called");
    }
    return comparingChar;
  }

  /**
   * Gets the value to compare to.
   *
   * @return the value to compare to
   */
  public String getValueToCompareTo() {
    if (valueToCompareTo == null) {
      throw new RuntimeException("wrong method called");
    }
    return valueToCompareTo;
  }

  /**
   * Gets the required columns names and values.
   *
   * @return the required columns names and values
   */
  public ArrayList<MyEntry<String, String>> getRequiredColumnsNamesAndValues() {
    if (requiredToSetColumnsAndValues == null) {
      throw new RuntimeException("wrong method called");
    }
    return requiredToSetColumnsAndValues;
  }

  /**
   * Gets the columns names and classes.
   *
   * @return the columns names and classes
   */
  // for Table
  public ArrayList<MyEntry<String, Class<?>>> getColumnsNamesAndClasses() {
    if (columnsAndClasses == null) {
      throw new RuntimeException("wrong method called");
    }
    return columnsAndClasses;
  }
}
