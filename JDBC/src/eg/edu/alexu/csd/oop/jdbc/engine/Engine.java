package eg.edu.alexu.csd.oop.jdbc.engine;
//done

import eg.edu.alexu.csd.oop.jdbc.engine.query.carriers.DeletionQueryCarrier;
import eg.edu.alexu.csd.oop.jdbc.engine.query.carriers.InsertionQueryCarrier;
import eg.edu.alexu.csd.oop.jdbc.engine.query.carriers.SelectionQueryCarrier;
import eg.edu.alexu.csd.oop.jdbc.engine.query.carriers.UpdateQueryCarrier;
import eg.edu.alexu.csd.oop.jdbc.handler.Facade;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.MyEntry;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.QueryValidatorAndParser;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.ResultSetParameters;
import eg.edu.alexu.csd.oop.jdbc.xml.XmlHandler;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class Engine.
 */
public class Engine {

  /** The parser and validator. */
  private QueryValidatorAndParser parserAndValidator;

  /** The current data base directory. */
  private String databasesDirectory, currentDataBaseDirectory;

  /** The tables names and columns count. */
  private Map<String, Integer> tablesNamesAndColumnsCount;

  /** The save and load handler. */
  private XmlHandler saveAndLoadHandler;

  /** The selection query carrier. */
  // objects to perform update, select, insert and delete operations
  private SelectionQueryCarrier selectionQueryCarrier;

  /** The insertion query carrier. */
  private InsertionQueryCarrier insertionQueryCarrier;

  /** The deletion query carrier. */
  private DeletionQueryCarrier deletionQueryCarrier;

  /** The update query carrier. */
  private UpdateQueryCarrier updateQueryCarrier;

  /**
   * Instantiates a new engine.
   *
   * @param homePath
   *          the home path
   */
  public Engine(String homePath) {
    File home = new File(homePath);
    databasesDirectory = home.getAbsolutePath() + File.separatorChar;
    parserAndValidator = new QueryValidatorAndParser();
    tablesNamesAndColumnsCount = new HashMap<String, Integer>();
    saveAndLoadHandler = new XmlHandler();
    selectionQueryCarrier = SelectionQueryCarrier.getInstance();
    updateQueryCarrier = UpdateQueryCarrier.getInstance();
    deletionQueryCarrier = DeletionQueryCarrier.getInstance();
    insertionQueryCarrier = InsertionQueryCarrier.getInstance();
  }

  /**
   * Execute structure query.
   *
   * @param query
   *          the query
   * @return true, if successful
   * @throws SQLException
   *           the SQL exception
   */
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

  /**
   * Execute update query.
   *
   * @param query
   *          the query
   * @return the integer representing the affected rows by the update
   * @throws SQLException
   *           the SQL exception
   */
  public int executeUpdateQuery(String query) throws SQLException {
    if (parserAndValidator.queryIsInsertion(query)) {
      return insertionQueryCarrier.carryInsertionQuery(query, tablesNamesAndColumnsCount,
          currentDataBaseDirectory);
    } else if (parserAndValidator.queryIsDeletion(query)) {
      return deletionQueryCarrier.carryDeleteQuery(query, tablesNamesAndColumnsCount,
          currentDataBaseDirectory);
    } else if (parserAndValidator.queryIsUpdate(query)) {
      return updateQueryCarrier.carryUpdateQuery(query, tablesNamesAndColumnsCount,
          currentDataBaseDirectory);
    }

    return 0;
  }

  /**
   * Execute query.
   *
   * @param query
   *          the query
   * @return the result set parameters
   * @throws SQLException
   *           the SQL exception
   */
  public ResultSetParameters executeQuery(String query) throws SQLException {
    if (parserAndValidator.queryIsSelection(query)) {
      return selectionQueryCarrier.carrySelectionQuery(query, tablesNamesAndColumnsCount,
          currentDataBaseDirectory);
    } else {
      throw new RuntimeException("Invalid query " + query);
    }
  }

  /**
   * Creates the database.
   *
   * @param databaseName
   *          the database name
   * @param dropIfExists
   *          the boolean indicating a sign to drop the database if it exists
   * @return true, if successful
   */
  private boolean createDatabase(String databaseName, boolean dropIfExists) {
    String databasePath = databasesDirectory + databaseName.toLowerCase() + File.separatorChar;
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

  /**
   * Creates the table.
   *
   * @param query
   *          the query
   * @return true, if successfully created the table
   * @throws SQLException
   *           the SQL exception
   */
  private boolean createTable(String query) throws SQLException {
    Facade creationParameters = parserAndValidator.getAddedTableParameters(query);
    String tableName = creationParameters.getTableName();
    if (tablesNamesAndColumnsCount.containsKey(tableName.toLowerCase())) {
      return false;
    }
    if (currentDataBaseDirectory == null) {
      // throw new RuntimeException("No database exist");
      throw new SQLException();
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
