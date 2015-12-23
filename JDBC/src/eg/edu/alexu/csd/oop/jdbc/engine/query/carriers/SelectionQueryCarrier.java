package eg.edu.alexu.csd.oop.jdbc.engine.query.carriers;

import eg.edu.alexu.csd.oop.jdbc.handler.Facade;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.QueryValidatorAndParser;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.ResultSetParameters;
import eg.edu.alexu.csd.oop.jdbc.xml.XmlHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * The Class SelectionQueryCarrier.
 */
// class for select tables
public class SelectionQueryCarrier {

  /** The single instance. */
  private static SelectionQueryCarrier singleInstance;

  /**
   * Instantiates a new selection query carrier.
   */
  private SelectionQueryCarrier() {

  }

  /**
   * Gets the instance.
   *
   * @return the single instance
   */
  public static SelectionQueryCarrier getInstance() {
    if (singleInstance == null) {
      singleInstance = new SelectionQueryCarrier();
    }
    return singleInstance;
  }

  /**
   * Carry selection query.
   *
   * @param query
   *          the query
   * @param tablesNamesAndColumnsCount
   *          the tables names and columns count
   * @param currentDataBaseDirectory
   *          the current data base directory
   * @return the result set parameters
   * @throws SQLException
   *           the SQL exception
   */
  public ResultSetParameters carrySelectionQuery(String query,
      Map<String, Integer> tablesNamesAndColumnsCount, String currentDataBaseDirectory)
          throws SQLException {

    String[] columnsNames, columnsDataTypes;
    QueryValidatorAndParser parserAndValidator = new QueryValidatorAndParser();
    XmlHandler saveAndLoadHandler = new XmlHandler();
    ArrayList<Object[]> currentTableRows = new ArrayList<Object[]>();
    String currentTableName;

    if (parserAndValidator.queryIsSelectAll(query)) {
      String tableName = parserAndValidator.getSelectAllFromTableParameters(query);
      if (tablesNamesAndColumnsCount.containsKey(tableName.toLowerCase())) {
        Object[][] dataTable = saveAndLoadHandler.readXml(currentDataBaseDirectory, tableName
            .toLowerCase());
        columnsNames = saveAndLoadHandler.getFieldsNames();
        columnsDataTypes = saveAndLoadHandler.getFieldsTypes();
        currentTableName = saveAndLoadHandler.getTableName();
        Object[][] returnedTable = new Object[dataTable.length - 1][dataTable[0].length];
        for (int counter = 1; counter < dataTable.length; counter++) {
          for (int counter2 = 0; counter2 < columnsNames.length; counter2++) {
            returnedTable[counter - 1][counter2] = dataTable[counter][counter2];
          }
        }
        return new ResultSetParameters(returnedTable, columnsNames, columnsDataTypes,
            currentTableName);
      }
    } else if (parserAndValidator.queryIsSelectWithCondition(query)) {
      Facade temp = parserAndValidator.getSelectColumnWithConditionParameters(query);
      String columnToSelect = temp.getColumnToSelectName();
      String tableName = temp.getTableName();
      String columnToCompareWithName = temp.getColumnToCompareToName();
      String compareChar = temp.getComparingChar();
      String value = temp.getValueToCompareTo();
      String[] columnName = new String[] { columnToSelect };
      String[] columnDataType = null;
      if (tablesNamesAndColumnsCount.containsKey(tableName.toLowerCase())) {
        Object[][] tempTable = saveAndLoadHandler.readXml(currentDataBaseDirectory, tableName
            .toLowerCase());
        columnsNames = saveAndLoadHandler.getFieldsNames();
        columnsDataTypes = saveAndLoadHandler.getFieldsTypes();
        currentTableName = saveAndLoadHandler.getTableName();
        currentTableRows.clear();
        for (int counter = 0; counter < tempTable.length; counter++) {
          currentTableRows.add(tempTable[counter]);
        }
      } else {
        throw new SQLException("no table with such a name");
      }
      int columnToCompareWithIndex = -1;
      int columnToSelectIndex = -1;
      for (int counter2 = 0; counter2 < columnsNames.length; counter2++) {
        if (columnsNames[counter2].equalsIgnoreCase(columnToCompareWithName)) {
          columnToCompareWithIndex = counter2;
          columnDataType = new String[] { columnsDataTypes[counter2] };
        }
        if (columnsNames[counter2].equalsIgnoreCase(columnToSelect)) {
          columnToSelectIndex = counter2;

        }
      }
      if (columnToCompareWithIndex == -1) {
        throw new SQLException("no column with such a name exists" + columnToCompareWithName);
      }
      if (columnToSelectIndex == -1) {
        throw new SQLException("no column with such a name exists" + columnToSelect);
      }
      String typeOfColumnToCompareWith = columnsDataTypes[columnToCompareWithIndex];
      ArrayList<Object> selected = new ArrayList<Object>();
      if (typeOfColumnToCompareWith.equalsIgnoreCase("varChar")) {
        for (int counter = 1; counter < currentTableRows.size(); counter++) {
          if (((String) currentTableRows.get(counter)[columnToCompareWithIndex]).equalsIgnoreCase(
              value)) {
            selected.add((String) currentTableRows.get(counter)[columnToSelectIndex]);
          }
        }
        Object[][] finalTable = new Object[selected.size()][1];
        for (int counter = 0; counter < selected.size(); counter++) {
          finalTable[counter][0] = selected.get(counter);
        }
        return new ResultSetParameters(finalTable, columnName, columnDataType, currentTableName);
      } else {
        int compareInt = Integer.parseInt(value);
        if (compareChar.equals("<")) {
          for (int counter = 1; counter < currentTableRows.size(); counter++) {
            if (((Integer) currentTableRows.get(counter)[columnToCompareWithIndex]) < compareInt) {
              selected.add(currentTableRows.get(counter)[columnToSelectIndex]);
            }
          }
        } else if (compareChar.equals("=")) {
          for (int counter = 1; counter < currentTableRows.size(); counter++) {
            if (((Integer) currentTableRows.get(counter)[columnToCompareWithIndex]) == compareInt) {
              selected.add(currentTableRows.get(counter)[columnToSelectIndex]);
            }
          }
        } else if (compareChar.equals(">")) {
          for (int counter = 1; counter < currentTableRows.size(); counter++) {
            if (((Integer) currentTableRows.get(counter)[columnToCompareWithIndex]) > compareInt) {
              selected.add(currentTableRows.get(counter)[columnToSelectIndex]);
            }
          }
        }
        Object[][] finalTable = new Object[selected.size()][1];
        for (int counter = 0; counter < selected.size(); counter++) {
          finalTable[counter][0] = selected.get(counter);
        }
        return new ResultSetParameters(finalTable, columnName, columnDataType, currentTableName);
      }

    } else if (parserAndValidator.queryIsSelectAllWithCondition(query)) {
      Facade temp = parserAndValidator.getSelectAllWithConditionParameters(query);
      String tableName = temp.getTableName();
      String columnToCompareWithName = temp.getColumnToCompareToName();
      columnToCompareWithName = columnToCompareWithName.replace(" ", "");
      String compareChar = temp.getComparingChar();
      String value = temp.getValueToCompareTo();
      value = value.replace(" ", "");
      if (tablesNamesAndColumnsCount.containsKey(tableName.toLowerCase())) {
        Object[][] tempTable = saveAndLoadHandler.readXml(currentDataBaseDirectory, tableName
            .toLowerCase());
        columnsNames = saveAndLoadHandler.getFieldsNames();
        columnsDataTypes = saveAndLoadHandler.getFieldsTypes();
        currentTableName = saveAndLoadHandler.getTableName();
        currentTableRows.clear();
        for (int counter = 0; counter < tempTable.length; counter++) {
          currentTableRows.add(tempTable[counter]);
        }
      } else {
        throw new SQLException("no table with such a name");
      }
      int columnToCompareWithIndex = -1;
      for (int counter2 = 0; counter2 < columnsNames.length; counter2++) {
        if (columnsNames[counter2].equalsIgnoreCase(columnToCompareWithName.replace(" ", ""))) {
          columnToCompareWithIndex = counter2;
        }
      }
      if (columnToCompareWithIndex == -1) {
        throw new SQLException("no column with such a name exists" + columnToCompareWithName);
      }
      String typeOfColumnToCompareWith = columnsDataTypes[columnToCompareWithIndex];
      ArrayList<Object[]> selected = new ArrayList<Object[]>();
      if (typeOfColumnToCompareWith.equalsIgnoreCase("varChar")) {
        for (int counter = 1; counter < currentTableRows.size(); counter++) {
          if (((String) currentTableRows.get(counter)[columnToCompareWithIndex]).equalsIgnoreCase(
              value)) {
            selected.add(currentTableRows.get(counter));
          }
        }
        Object[][] finalTable = new Object[selected.size()][];
        for (int counter = 0; counter < selected.size(); counter++) {
          finalTable[counter] = selected.get(counter);
        }
        return new ResultSetParameters(finalTable, columnsNames, columnsDataTypes,
            currentTableName);
      } else {
        int compareInt = Integer.parseInt(value);
        if (compareChar.equals("<")) {
          for (int counter = 1; counter < currentTableRows.size(); counter++) {
            if (((Integer) currentTableRows.get(counter)[columnToCompareWithIndex]) < compareInt) {
              selected.add(currentTableRows.get(counter));
            }
          }
        } else if (compareChar.equals("=")) {
          for (int counter = 1; counter < currentTableRows.size(); counter++) {
            if (((Integer) currentTableRows.get(counter)[columnToCompareWithIndex]) == compareInt) {
              selected.add(currentTableRows.get(counter));
            }
          }
        } else if (compareChar.equals(">")) {
          for (int counter = 1; counter < currentTableRows.size(); counter++) {
            if (((Integer) currentTableRows.get(counter)[columnToCompareWithIndex]) > compareInt) {
              selected.add(currentTableRows.get(counter));
            }
          }
        }
        Object[][] finalTable = new Object[selected.size()][];
        for (int counter = 0; counter < selected.size(); counter++) {
          finalTable[counter] = selected.get(counter);
        }
        return new ResultSetParameters(finalTable, columnsNames, columnsDataTypes,
            currentTableName);
      }

    } else if (parserAndValidator.queryIsSelectWithNames(query)) {
      Facade queryParameters = parserAndValidator.getSelectWithNameParameters(query);
      ArrayList<String> columnsToBeSelectedNames = queryParameters.getColumnsNames();
      String[] selectedColumnsNames = new String[columnsToBeSelectedNames.size()];
      String[] selectedColumnsDatatypes = new String[columnsToBeSelectedNames.size()];
      selectedColumnsNames = columnsToBeSelectedNames.toArray(selectedColumnsNames);
      String tableName = queryParameters.getTableName();
      if (tablesNamesAndColumnsCount.containsKey(tableName.toLowerCase())) {
        Object[][] tableData = saveAndLoadHandler.readXml(currentDataBaseDirectory, tableName
            .toLowerCase());
        columnsNames = saveAndLoadHandler.getFieldsNames();
        columnsDataTypes = saveAndLoadHandler.getFieldsTypes();
        currentTableName = saveAndLoadHandler.getTableName();
        int[] index = new int[columnsToBeSelectedNames.size()];
        Arrays.fill(index, -1);
        for (int counter = 0; counter < columnsToBeSelectedNames.size(); counter++) {
          for (int counter2 = 0; counter2 < columnsNames.length; counter2++) {
            if (columnsToBeSelectedNames.get(counter).equalsIgnoreCase(columnsNames[counter2])) {
              index[counter] = counter2;
              selectedColumnsDatatypes[counter] = columnsDataTypes[counter2];
            }
          }
          if (index[counter] == -1) {
            throw new SQLException("invalid column name " + columnsToBeSelectedNames.get(counter));
          }
        }
        Object[][] requiredTable = new Object[tableData.length - 1][columnsToBeSelectedNames
            .size()];
        for (int counter = 1; counter < columnsNames.length; counter++) {
          for (int counter2 = 0; counter2 < index.length; counter2++) {
            requiredTable[counter - 1][counter2] = tableData[counter][index[counter2]];
          }
        }
        return new ResultSetParameters(requiredTable, selectedColumnsNames,
            selectedColumnsDatatypes, currentTableName);
      }
    }
    return null;
  }

}
