package eg.edu.alexu.csd.oop.jdbc.engine.query.carriers;

import eg.edu.alexu.csd.oop.jdbc.handler.Facade;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.MyEntry;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.QueryValidatorAndParser;
import eg.edu.alexu.csd.oop.jdbc.xml.XmlHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * The Class UpdateQueryCarrier.
 */
// class for update tables
public class UpdateQueryCarrier {

  /** The single instance. */
  private static UpdateQueryCarrier singleInstance;

  /**
   * Instantiates a new update query carrier.
   */
  private UpdateQueryCarrier() {

  }

  /**
   * Gets the instance.
   *
   * @return the single instance
   */
  public static UpdateQueryCarrier getInstance() {
    if (singleInstance == null) {
      singleInstance = new UpdateQueryCarrier();
    }
    return singleInstance;
  }

  /**
   * Carry update query.
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
  public int carryUpdateQuery(String query, Map<String, Integer> tablesNamesAndColumnsCount,
      String currentDataBaseDirectory) throws SQLException {

    String[] columnsNames, columnsDataTypes;
    QueryValidatorAndParser parserAndValidator = new QueryValidatorAndParser();
    XmlHandler saveAndLoadHandler = new XmlHandler();
    ArrayList<Object[]> currentTableRows = new ArrayList<Object[]>();

    if (parserAndValidator.queryIsConditionalUpdate(query)) {
      Facade parameters = parserAndValidator.getUpdateParameters(query);
      ArrayList<MyEntry<String, String>> columnsAndValues = parameters
          .getRequiredColumnsNamesAndValues();
      String[] columnsToChange = new String[columnsAndValues.size()];
      String[] newValues = new String[columnsAndValues.size()];
      int dummyCounter = 0;
      for (MyEntry<String, String> dummyEntry : columnsAndValues) {
        columnsToChange[dummyCounter] = dummyEntry.getFirst();
        newValues[dummyCounter] = dummyEntry.getSecond();
        dummyCounter++;
      }
      String tableName = parameters.getTableName();
      String columnToCompareWithName = parameters.getColumnToCompareToName();
      String compareChar = parameters.getComparingChar();
      String value = parameters.getValueToCompareTo();
      if (tablesNamesAndColumnsCount.containsKey(tableName.toLowerCase())) {
        Object[][] tempTable = saveAndLoadHandler.readXml(currentDataBaseDirectory, tableName
            .toLowerCase());
        columnsNames = saveAndLoadHandler.getFieldsNames();
        columnsDataTypes = saveAndLoadHandler.getFieldsTypes();
        currentTableRows.clear();
        for (int counter = 0; counter < tempTable.length; counter++) {
          currentTableRows.add(tempTable[counter]);
        }
      } else {
        throw new SQLException("no table with such a name");
      }
      int[] index = new int[columnsToChange.length];
      Arrays.fill(index, -1);
      for (int counter = 0; counter < columnsToChange.length; counter++) {
        for (int counter2 = 0; counter2 < columnsNames.length; counter2++) {
          if (columnsNames[counter2].equalsIgnoreCase(columnsToChange[counter])) {
            index[counter] = counter2;
          }
        }
        if (index[counter] == -1) {
          throw new SQLException("no column with such a name exists" + columnsToChange[counter]);
        }
      }
      int columnToCompareWithIndex = -1;
      for (int counter = 0; counter < columnsNames.length; counter++) {
        if (columnsNames[counter].equalsIgnoreCase(columnToCompareWithName)) {
          columnToCompareWithIndex = counter;
          break;
        }
      }
      if (columnToCompareWithIndex == -1) {
        throw new SQLException("no column with such a name exists" + columnToCompareWithName);
      }
      String typeOfColumnToCompareWith = columnsDataTypes[columnToCompareWithIndex];
      if (typeOfColumnToCompareWith.equalsIgnoreCase("varChar")) {
        int edited = 0;
        for (int counter = 1; counter < currentTableRows.size(); counter++) {
          if (((String) currentTableRows.get(counter)[columnToCompareWithIndex]).equalsIgnoreCase(
              value)) {
            edited++;
            Object[] dummy = currentTableRows.get(counter);
            for (int counter2 = 0; counter2 < columnsToChange.length; counter2++) {
              dummy[index[counter2]] = newValues[counter2];
            }
            currentTableRows.set(counter, dummy);
          }
        }
        Object[][] finalTable = new Object[currentTableRows.size()][];
        for (int counter = 0; counter < currentTableRows.size(); counter++) {
          finalTable[counter] = currentTableRows.get(counter);
        }
        saveAndLoadHandler.writeXml(columnsNames, columnsDataTypes, currentDataBaseDirectory,
            finalTable, tableName.toLowerCase());
        return edited;
      } else {
        int compareInt = Integer.parseInt(value);
        int edited = 0;
        if (compareChar.equals("<")) {
          for (int counter = 1; counter < currentTableRows.size(); counter++) {
            if (((Integer) currentTableRows.get(counter)[columnToCompareWithIndex]) < compareInt) {
              edited++;
              Object[] dummy = currentTableRows.get(counter);
              for (int counter2 = 0; counter2 < columnsToChange.length; counter2++) {
                dummy[index[counter2]] = newValues[counter2];
              }
              currentTableRows.set(counter, dummy);
            }
          }
        } else if (compareChar.equals("=")) {
          for (int counter = 1; counter < currentTableRows.size(); counter++) {
            if (((Integer) currentTableRows.get(counter)[columnToCompareWithIndex]) == compareInt) {
              edited++;
              Object[] dummy = currentTableRows.get(counter);
              for (int counter2 = 0; counter2 < columnsToChange.length; counter2++) {
                dummy[index[counter2]] = newValues[counter2];
              }
              currentTableRows.set(counter, dummy);
            }
          }
        } else if (compareChar.equals(">")) {
          for (int counter = 1; counter < currentTableRows.size(); counter++) {
            if (((Integer) currentTableRows.get(counter)[columnToCompareWithIndex]) > compareInt) {
              edited++;
              Object[] dummy = currentTableRows.get(counter);
              for (int counter2 = 0; counter2 < columnsToChange.length; counter2++) {
                dummy[index[counter2]] = newValues[counter2];
              }
              currentTableRows.set(counter, dummy);
            }
          }
        }
        Object[][] finalTable = new Object[currentTableRows.size()][];
        for (int counter = 0; counter < currentTableRows.size(); counter++) {
          finalTable[counter] = currentTableRows.get(counter);
        }
        saveAndLoadHandler.writeXml(columnsNames, columnsDataTypes, currentDataBaseDirectory,
            finalTable, tableName.toLowerCase());
        return edited;
      }
    } else if (parserAndValidator.queryIsUpdateWithoutCondition(query)) {
      Facade parameters = parserAndValidator.getUpdateWithoutConditionParameters(query);
      ArrayList<MyEntry<String, String>> columnsAndValues = parameters
          .getRequiredColumnsNamesAndValues();
      String[] columnsToChange = new String[columnsAndValues.size()];
      String[] newValues = new String[columnsAndValues.size()];
      int dummyCounter = 0;
      for (MyEntry<String, String> dummyEntry : columnsAndValues) {
        columnsToChange[dummyCounter] = dummyEntry.getFirst();
        newValues[dummyCounter] = dummyEntry.getSecond();
        dummyCounter++;
      }
      String tableName = parameters.getTableName();
      if (tablesNamesAndColumnsCount.containsKey(tableName.toLowerCase())) {
        Object[][] tempTable = saveAndLoadHandler.readXml(currentDataBaseDirectory, tableName
            .toLowerCase());
        columnsNames = saveAndLoadHandler.getFieldsNames();
        columnsDataTypes = saveAndLoadHandler.getFieldsTypes();
        currentTableRows.clear();
        for (int counter = 0; counter < tempTable.length; counter++) {
          currentTableRows.add(tempTable[counter]);
        }
      } else {
        throw new SQLException("no table with such a name");
      }
      int[] index = new int[columnsToChange.length];
      Arrays.fill(index, -1);
      for (int counter = 0; counter < columnsToChange.length; counter++) {
        for (int counter2 = 0; counter2 < columnsNames.length; counter2++) {
          if (columnsNames[counter2].equalsIgnoreCase(columnsToChange[counter])) {
            index[counter] = counter2;
          }
        }
        if (index[counter] == -1) {
          throw new SQLException("no column with such a name exists" + columnsToChange[counter]);
        }
      }
      int edited = 0;
      for (int counter = 1; counter < currentTableRows.size(); counter++) {
        edited++;
        Object[] dummy = currentTableRows.get(counter);
        for (int counter2 = 0; counter2 < columnsToChange.length; counter2++) {
          dummy[index[counter2]] = newValues[counter2];
        }
        currentTableRows.set(counter, dummy);
      }
      Object[][] finalTable = new Object[currentTableRows.size()][];
      for (int counter = 0; counter < currentTableRows.size(); counter++) {
        finalTable[counter] = currentTableRows.get(counter);
      }
      saveAndLoadHandler.writeXml(columnsNames, columnsDataTypes, currentDataBaseDirectory,
          finalTable, tableName.toLowerCase());
      return edited;
    }
    return 0;
  }

}
