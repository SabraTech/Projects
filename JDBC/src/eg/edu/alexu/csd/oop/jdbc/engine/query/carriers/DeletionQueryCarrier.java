package eg.edu.alexu.csd.oop.jdbc.engine.query.carriers;

//done
import eg.edu.alexu.csd.oop.jdbc.handler.Facade;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.QueryValidatorAndParser;
import eg.edu.alexu.csd.oop.jdbc.xml.XmlHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * The Class DeletionQueryCarrier.
 */
// class for delete from tables
public class DeletionQueryCarrier {

  /** The single instance. */
  private static DeletionQueryCarrier singleInstance;

  /**
   * Instantiates a new deletion query carrier.
   */
  private DeletionQueryCarrier() {

  }

  /**
   * Gets the instance.
   *
   * @return the single instance
   */
  public static DeletionQueryCarrier getInstance() {
    if (singleInstance == null) {
      singleInstance = new DeletionQueryCarrier();
    }
    return singleInstance;
  }

  /**
   * Carry delete query.
   *
   * @param query
   *          the query
   * @param tablesNamesAndColumnsCount
   *          the tables names and columns count
   * @param currentDataBaseDirectory
   *          the current data base directory
   * @return the integer indicating number of deleted columns
   * @throws SQLException
   *           the SQL exception
   */
  public int carryDeleteQuery(String query, Map<String, Integer> tablesNamesAndColumnsCount,
      String currentDataBaseDirectory) throws SQLException {

    String[] columnsNames, columnsDataTypes;
    QueryValidatorAndParser parserAndValidator = new QueryValidatorAndParser();
    XmlHandler saveAndLoadHandler = new XmlHandler();
    ArrayList<Object[]> currentTableRows = new ArrayList<Object[]>();

    if (parserAndValidator.queryIsConditionalDelete(query)) {
      Facade temp = parserAndValidator.getConditionalDeleteParameters(query);
      String tableName = temp.getTableName();
      String columnToCompareTo = temp.getColumnToCompareToName();
      String compareChar = temp.getComparingChar();
      String value = temp.getValueToCompareTo();
      if (tablesNamesAndColumnsCount.containsKey(tableName.toLowerCase())) {
        Object[][] tempTable = saveAndLoadHandler.readXml(currentDataBaseDirectory, tableName
            .toLowerCase());
        columnsNames = saveAndLoadHandler.getFieldsNames();
        columnsDataTypes = saveAndLoadHandler.getFieldsTypes();
        currentTableRows.clear();
        for (int counter = 0; counter < tempTable.length; counter++) {
          currentTableRows.add(tempTable[counter]);
        }
        int index = -1;
        for (int counter = 0; counter < columnsNames.length; counter++) {
          if (columnsNames[counter].equalsIgnoreCase(columnToCompareTo)) {
            index = counter;
            break;
          }
        }
        if (index == -1) {
          throw new SQLException("column with name " + columnToCompareTo + " not found ");
        }

        String typeOfColumnToDelete = columnsDataTypes[index];
        if (typeOfColumnToDelete.equalsIgnoreCase("varchar")) {
          for (int counter = 1; counter < currentTableRows.size(); counter++) {
            if (((String) currentTableRows.get(counter)[index]).equalsIgnoreCase(value)) {
              currentTableRows.remove(counter);
              counter--;
            }
          }
          Object[][] newTable = new Object[currentTableRows.size()][];
          for (int counter = 0; counter < currentTableRows.size(); counter++) {
            newTable[counter] = currentTableRows.get(counter);
          }
          saveAndLoadHandler.writeXml(columnsNames, columnsDataTypes, currentDataBaseDirectory,
              newTable, tableName.toLowerCase());
          return tempTable.length - currentTableRows.size();
        } else {
          int compareInt = Integer.parseInt(value);
          int comparingFlag;
          if (compareChar.equals("<")) {
            comparingFlag = -1;
          } else if (compareChar.equals("=")) {
            comparingFlag = 0;
          } else {
            comparingFlag = 1;
          }
          for (int counter = 1; counter < currentTableRows.size(); counter++) {
            int dummy = (Integer) currentTableRows.get(counter)[index];
            switch (comparingFlag) { // less than
            case -1: {
              if (dummy < compareInt) {
                currentTableRows.remove(counter);
                counter--;
              }
              break;
            }
            case 0: {
              if (dummy == compareInt) {
                currentTableRows.remove(counter);
                counter--;
              }
              break;
            }
            case 1: { // greater than
              if (dummy > compareInt) {
                currentTableRows.remove(counter);
                counter--;
              }
              break;
            }
            default:
              break;
            }

          }
          Object[][] newTable = new Object[currentTableRows.size()][];
          for (int counter = 0; counter < currentTableRows.size(); counter++) {
            newTable[counter] = currentTableRows.get(counter);
          }
          saveAndLoadHandler.writeXml(columnsNames, columnsDataTypes, currentDataBaseDirectory,
              newTable, tableName.toLowerCase());
          return tempTable.length - currentTableRows.size();
        }
      }

    } else if (parserAndValidator.queryIsDeleteAll(query)) {
      String tableName = parserAndValidator.getTableNameToDeleteAll(query);
      if (tablesNamesAndColumnsCount.containsKey(tableName.toLowerCase())) {
        Object[][] tempTable = saveAndLoadHandler.readXml(currentDataBaseDirectory, tableName
            .toLowerCase());
        columnsNames = saveAndLoadHandler.getFieldsNames();
        columnsDataTypes = saveAndLoadHandler.getFieldsTypes();
        int size = tempTable.length;
        Object[][] newTable = new Object[1][columnsNames.length];
        saveAndLoadHandler.writeXml(columnsNames, columnsDataTypes, currentDataBaseDirectory,
            newTable, tableName.toLowerCase());
        return size - 1;
      }

    }
    return 0;
  }

}
