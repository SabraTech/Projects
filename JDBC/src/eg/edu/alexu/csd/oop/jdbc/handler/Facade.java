package eg.edu.alexu.csd.oop.jdbc.handler;

import java.util.ArrayList;

import eg.edu.alexu.csd.oop.jdbc.sql.parser.MyEntry;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.DeleteParameters;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.InsertionParameters;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.SelectionParameters;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.TableCreationParameters;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.UpdateParameters;

public class Facade {

  private String tableName;

  /** The column to compare name. */
  private String columnToCompareName;

  /** The comparing char. */
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

  public Facade(TableCreationParameters create) {
    this.tableName = create.getTableName();
    this.columnsAndClasses = create.getColumnsNamesAndClasses();
  }

  public Facade(SelectionParameters select) {
    this.tableName = select.getTableName();
    this.columnsNames = select.getColumnsToSelectNames();
    this.columnToSelectName = select.getColumnToSelectName();
    this.columnToCompareName = select.getColumnToCompareName();
    this.comparingChar = select.getComparingChar();
    this.valueToCompareTo = select.getValueToCompareTo();
  }

  public Facade(UpdateParameters update) {
    this.tableName = update.getTableName();
    this.requiredToSetColumnsAndValues = update.getRequiredColumnsNamesAndValues();
    this.columnToCompareName = update.getColumnToCompareToName();
    this.comparingChar = update.getComparingChar();
    this.valueToCompareTo = update.getValueToCompareTo();
  }

  public Facade(DeleteParameters delete) {
    this.tableName = delete.getTableName();
    this.columnToCompareName = delete.getColumnToCompareToName();
    this.comparingChar = delete.getComparingChar();
    this.valueToCompareTo = delete.getValueToCompareTo();
  }

  public Facade(InsertionParameters insert) {
    this.tableName = insert.getTableName();
    this.columnsNames = insert.getColumnsNames();
    this.values = insert.getValues();
  }

  public String getTableName() {
    if (tableName == null) {
      throw new RuntimeException("wrong method called");
    }
    return tableName;
  }

  public String getColumnToSelectName() {
    if (columnToSelectName == null) {
      throw new RuntimeException("wrong method called");
    }
    return columnToSelectName;
  }

  // for insert
  public ArrayList<String> getColumnsNames() {
    if (columnsNames == null) {
      throw new RuntimeException("wrong method called");
    }
    return columnsNames;
  }

  public ArrayList<Object> getValues() {
    if (values == null) {
      throw new RuntimeException("wrong method called");
    }
    return values;
  }

  // for delete
  public String getColumnToCompareToName() {
    if (columnToCompareName == null) {
      throw new RuntimeException("wrong method called");
    }
    return columnToCompareName;
  }

  public String getComparingChar() {
    if (comparingChar == null) {
      throw new RuntimeException("wrong method called");
    }
    return comparingChar;
  }

  public String getValueToCompareTo() {
    if (valueToCompareTo == null) {
      throw new RuntimeException("wrong method called");
    }
    return valueToCompareTo;
  }

  public ArrayList<MyEntry<String, String>> getRequiredColumnsNamesAndValues() {
    if (requiredToSetColumnsAndValues == null) {
      throw new RuntimeException("wrong method called");
    }
    return requiredToSetColumnsAndValues;
  }

  // for Table
  public ArrayList<MyEntry<String, Class<?>>> getColumnsNamesAndClasses() {
    if (columnsAndClasses == null) {
      throw new RuntimeException("wrong method called");
    }
    return columnsAndClasses;
  }
}
