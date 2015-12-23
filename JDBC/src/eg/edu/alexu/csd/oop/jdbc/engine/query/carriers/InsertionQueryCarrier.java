package eg.edu.alexu.csd.oop.jdbc.engine.query.carriers;

import eg.edu.alexu.csd.oop.jdbc.handler.Facade;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.QueryValidatorAndParser;
import eg.edu.alexu.csd.oop.jdbc.xml.XmlHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * The Class InsertionQueryCarrier.
 */
// class for insertion in tables
public class InsertionQueryCarrier {

  /** The single instance. */
  private static InsertionQueryCarrier singleInstance;

  /**
   * Instantiates a new insertion query carrier.
   */
  private InsertionQueryCarrier() {

  }

  /**
   * Gets the instance.
   *
   * @return the single instance
   */
  public static InsertionQueryCarrier getInstance() {
    if (singleInstance == null) {
      singleInstance = new InsertionQueryCarrier();
    }
    return singleInstance;
  }

  /**
   * Carry insertion query.
   *
   * @param query
   *          the query
   * @param tablesNamesAndColumnsCount
   *          the tables names and columns count
   * @param currentDataBaseDirectory
   *          the current data base directory
   * @return the int
   * @throws SQLException
   *           the SQL exception
   */
  public int carryInsertionQuery(String query, Map<String, Integer> tablesNamesAndColumnsCount,
      String currentDataBaseDirectory) throws SQLException {

    String[] columnsNames, columnsDataTypes;
    QueryValidatorAndParser parserAndValidator = new QueryValidatorAndParser();
    XmlHandler saveAndLoadHandler = new XmlHandler();
    ArrayList<Object[]> currentTableRows = new ArrayList<Object[]>();

    if (parserAndValidator.queryIsInsertIntoTableColumnWithValues(query)) {
      Facade temp = parserAndValidator.getInsertIntoTableColumnsWithValuesParameters(query);
      String tableName = temp.getTableName();
      ArrayList<String> columns = temp.getColumnsNames();
      ArrayList<Object> values = temp.getValues();
      if (tablesNamesAndColumnsCount.containsKey(tableName.toLowerCase())
          && tablesNamesAndColumnsCount.get(tableName.toLowerCase()) >= columns.size()) {
        Object[][] tempTable = saveAndLoadHandler.readXml(currentDataBaseDirectory, tableName
            .toLowerCase());
        columnsNames = saveAndLoadHandler.getFieldsNames();
        columnsDataTypes = saveAndLoadHandler.getFieldsTypes();
        currentTableRows.clear();
        for (int counter = 0; counter < tempTable.length; counter++) {
          currentTableRows.add(tempTable[counter]);
        }
        Object[] dummyRow = new Object[columnsNames.length];
        for (int counter = 0; counter < columns.size(); counter++) {
          boolean found = false;
          for (int counter2 = 0; counter2 < columnsNames.length; counter2++) {
            if (columns.get(counter).equalsIgnoreCase(columnsNames[counter2])) {
              dummyRow[counter2] = values.get(counter);
              found = true;
            }
          }
          if (!found) {
            throw new SQLException("column not found name = " + columns.get(counter));
          }
        }
        currentTableRows.add(dummyRow);
        Object[][] newTable = new Object[currentTableRows.size()][];
        for (int counteri = 0; counteri < currentTableRows.size(); counteri++) {
          newTable[counteri] = currentTableRows.get(counteri);
        }
        saveAndLoadHandler.writeXml(columnsNames, columnsDataTypes, currentDataBaseDirectory,
            newTable, tableName.toLowerCase());
        return 1;
      }
    } else if (parserAndValidator.queryIsInsertIntoTableValues(query)) {
      Facade temp = parserAndValidator.getInsertIntoTableValuesParameters(query);
      String tableName = temp.getTableName();
      ArrayList<Object> values = temp.getValues();
      if (tablesNamesAndColumnsCount.containsKey(tableName.toLowerCase())
          && tablesNamesAndColumnsCount.get(tableName.toLowerCase()) == values.size()) {
        Object[][] tempTable = saveAndLoadHandler.readXml(currentDataBaseDirectory, tableName
            .toLowerCase());
        columnsNames = saveAndLoadHandler.getFieldsNames();
        columnsDataTypes = saveAndLoadHandler.getFieldsTypes();
        currentTableRows.clear();
        for (int counter = 0; counter < tempTable.length; counter++) {
          currentTableRows.add(tempTable[counter]);
        }
        Object[] columns = new Object[values.size()];
        for (int counter = 0; counter < values.size(); counter++) {
          columns[counter] = values.get(counter);
        }
        currentTableRows.add(columns);

        Object[][] newTable = new Object[currentTableRows.size()][];
        for (int counter = 0; counter < currentTableRows.size(); counter++) {
          newTable[counter] = currentTableRows.get(counter);
        }
        saveAndLoadHandler.writeXml(columnsNames, columnsDataTypes, currentDataBaseDirectory,
            newTable, tableName.toLowerCase());
        return 1;
      }

    }
    return 0;
  }

}
