package eg.edu.alexu.csd.oop.jdbc.engine;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.jdbc.engine.query.carriers.DeletionQueryCarrier;
import eg.edu.alexu.csd.oop.jdbc.engine.query.carriers.InsertionQueryCarrier;
import eg.edu.alexu.csd.oop.jdbc.engine.query.carriers.SelectionQueryCarrier;
import eg.edu.alexu.csd.oop.jdbc.engine.query.carriers.UpdateQueryCarrier;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.MyEntry;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.QueryValidatorAndParser;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.ResultSetParameters;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.TableCreationParameters;
import eg.edu.alexu.csd.oop.jdbc.xml.XmlHandler;

public class Engine {

  private QueryValidatorAndParser parserAndValidator;
  private String databaseDirectory, currentDataBaseDirectory;
  private Map<String, Integer> tablesNamesAndColumnsCount;
  private XmlHandler saveAndLoadHandler;
  // objects to perform update, select, insert and delete operations
  private SelectionQueryCarrier selectObj;
  private InsertionQueryCarrier insertObj;
  private DeletionQueryCarrier deleteObj;
  private UpdateQueryCarrier updateObj;

  public Engine(String homePath) {
    File home = new File(homePath);
    databaseDirectory = home.getAbsolutePath() + File.separatorChar;
    parserAndValidator = new QueryValidatorAndParser();
    tablesNamesAndColumnsCount = new HashMap<String, Integer>();
    saveAndLoadHandler = new XmlHandler();
    selectObj = SelectionQueryCarrier.getInst();
    updateObj = UpdateQueryCarrier.getInst();
    deleteObj = DeletionQueryCarrier.getInst();
    insertObj = InsertionQueryCarrier.getInst();
  }

  public boolean executeStructureQuery(String query) throws SQLException {
    if (parserAndValidator.queryIsCreateDataBase(query)) {
      String databaseName = parserAndValidator.getDatabaseName(query);
      createDatabase(databaseName, false);
      return true;
    }
    if (parserAndValidator.queryIsDropDataBase(query)) {
      String databaseName = parserAndValidator.getDataBaseNameToDrop(query);
      createDatabase(databaseName, true);
      return true;
    }
    if (parserAndValidator.queryIsCreateTable(query)) {
      return createTable(query);
    } else if (parserAndValidator.queryIsDropTable(query)) {
      String tableName = parserAndValidator.getTableToDropName(query);
      if (!tablesNamesAndColumnsCount.containsKey(tableName.toLowerCase())) {
        return false;
      }
      if (saveAndLoadHandler.clearTable(currentDataBaseDirectory, tableName)) {
        tablesNamesAndColumnsCount.remove(tableName.toLowerCase());
        return true;
      } else {
        return false;
      }
    } else {
      throw new SQLException();
    }

  }

  public int executeUpdateQuery(String query) throws SQLException {
    if (parserAndValidator.queryIsInsertion(query)) {
      return insertObj.carryInsertionQuery(query, tablesNamesAndColumnsCount,
          currentDataBaseDirectory);
    } else if (parserAndValidator.queryIsDeletion(query)) {
      return deleteObj.carryDeleteQuery(query, tablesNamesAndColumnsCount,
          currentDataBaseDirectory);
    } else if (parserAndValidator.queryIsUpdate(query)) {
      return updateObj.carryUpdateQuery(query, tablesNamesAndColumnsCount,
          currentDataBaseDirectory);
    }

    return 0;
  }

  public ResultSetParameters executeQuery(String query) throws SQLException {
    if (parserAndValidator.queryIsSelection(query)) {
      return selectObj.carrySelectionQuery(query, tablesNamesAndColumnsCount,
          currentDataBaseDirectory);
    } else {
      throw new RuntimeException("Invalid query " + query);
    }
  }

  private boolean createDatabase(String databaseName, boolean dropIfExists) {
    String databasePath = databaseDirectory + databaseName.toLowerCase() + File.separatorChar;
    ;
    tablesNamesAndColumnsCount.clear();
    File file = new File(databasePath);
    if (!dropIfExists) {
      if (!file.exists()) {
        if (file.mkdirs()) {
          currentDataBaseDirectory = databasePath;
          return true;
        } else {
          throw new RuntimeException("unable to create database" + databasePath + " ");
        }
      } else {
        currentDataBaseDirectory = databasePath;
        return true;
      }
    } else {
      if (file.exists()) {
        if (file.isDirectory()) {
          // list of all files in it
          String[] files = file.list();
          for (String temp : files) {
            File dummyFile = new File(file, temp);
            dummyFile.delete();
          }
        }
        currentDataBaseDirectory = databasePath;
        return true;
      } else {
        return false;
      }
    }
  }

  private boolean createTable(String query) throws SQLException {
    TableCreationParameters creationParameters = parserAndValidator.getAddedTableParameters(query);
    String tableName = creationParameters.getTableName();
    if (tablesNamesAndColumnsCount.containsKey(tableName.toLowerCase())) {
      return false;
    }
    if (currentDataBaseDirectory == null) {
      throw new RuntimeException("No database exist");
    }
    ArrayList<MyEntry<String, Class<?>>> columnsNamesAndClasses = creationParameters
        .getColumnsNamesAndClasses();
    String[] columnsNames = new String[columnsNamesAndClasses.size()];
    String[] columnsDataTypes = new String[columnsNamesAndClasses.size()];
    int counter = 0;
    for (MyEntry<String, Class<?>> dummyEntry : columnsNamesAndClasses) {
      columnsNames[counter] = dummyEntry.getFirst();
      if (dummyEntry.getSecond().getSimpleName().equals(String.class.getSimpleName())) {
        columnsDataTypes[counter] = "varchar";
      } else if (dummyEntry.getSecond().getSimpleName().equals(Integer.class.getSimpleName())) {
        columnsDataTypes[counter] = "int";
      }
      counter++;
    }
    Object[][] table = new Object[1][columnsNames.length];
    tablesNamesAndColumnsCount.put(tableName.toLowerCase(), columnsNames.length);
    saveAndLoadHandler.writeXml(columnsNames, columnsDataTypes, currentDataBaseDirectory, table,
        tableName.toLowerCase());
    return true;
  }

}
