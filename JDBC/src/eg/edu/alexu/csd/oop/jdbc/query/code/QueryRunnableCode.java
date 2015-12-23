package eg.edu.alexu.csd.oop.jdbc.query.code;

import eg.edu.alexu.csd.oop.jdbc.MyResultSet;
import eg.edu.alexu.csd.oop.jdbc.engine.Engine;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.QueryValidatorAndParser;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.ResultSetParameters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The Class QueryRunnableCode.
 */
public class QueryRunnableCode implements Runnable {

  /** The engine. */
  private Engine engine;

  /** The query type. */
  private int queryType;

  /** The statement. */
  private Statement statement;

  /** The sql. */
  private String sql;

  /** The execution result. */
  private boolean executionResult;

  /** The execution integer result. */
  private int executionIntegerResult;

  /** The execution result set. */
  private ResultSet executionResultSet;

  /** The query validator and parser. */
  private QueryValidatorAndParser queryValidatorAndParser;

  /** The error flag. */
  private boolean errorFlag;// indicates that an sql exception was thrown
  // three integers for outer classes to use when calling the constructor
  // to indicate the exact
  /** The Constant GENERAL_QUERY. */
  // code that should be executed
  public static final int GENERAL_QUERY = 1;

  /** The Constant UPDATE_QUERY. */
  public static final int UPDATE_QUERY = 2;

  /** The Constant SELECTION_QUERY. */
  public static final int SELECTION_QUERY = 3;

  /**
   * Instantiates a new query runnable code.
   *
   * @param engine
   *          the engine
   * @param queryType
   *          the query type
   * @param statement
   *          the statement
   * @param sql
   *          the sql
   * @param queryValidatorAndParser
   *          the query validator and parser
   */
  public QueryRunnableCode(Engine engine, int queryType, Statement statement, String sql,
      QueryValidatorAndParser queryValidatorAndParser) {
    this.engine = engine;
    this.queryType = queryType;
    this.statement = statement;
    this.sql = sql;
    this.queryValidatorAndParser = queryValidatorAndParser;

  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {
    switch (queryType) {
    case QueryRunnableCode.GENERAL_QUERY:
      try {
        executionResult = execute(sql, engine);
      } catch (SQLException e) {
        errorFlag = true;// an sql exception
      }
      break;
    case QueryRunnableCode.UPDATE_QUERY:
      try {
        executionIntegerResult = executeUpdate(sql, engine);
      } catch (SQLException e) {
        errorFlag = true;// an sql exception
      }
      break;
    case QueryRunnableCode.SELECTION_QUERY:
      try {
        executionResultSet = executeQuery(sql, statement, engine);
      } catch (SQLException e) {
        errorFlag = true;// an sql exception
      }
      break;
    }

  }

  /**
   * Execute.
   *
   * @param sql
   *          the sql
   * @param engine
   *          the engine
   * @return true, if successful
   * @throws SQLException
   *           the SQL exception
   */
  public boolean execute(String sql, Engine engine) throws SQLException {
    int type = queryValidatorAndParser.getQueryType(sql);
    if (type == QueryValidatorAndParser.STRUCTURE_QUERY) {
      return engine.executeStructureQuery(sql);
    } else if (type == QueryValidatorAndParser.UPDATE_QUERY) {
      int updateCount = engine.executeUpdateQuery(sql);
      if (updateCount > 0) {
        return true;
      } else {
        return false;
      }
    } else if (type == QueryValidatorAndParser.SELECTION_QUERY) {
      ResultSetParameters result = engine.executeQuery(sql);
      if (result != null) {
        Object[][] tableData = result.getSelectedData();
        if (tableData.length != 0) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
    return false;
  }

  /**
   * Execute update.
   *
   * @param sql
   *          the sql
   * @param engine
   *          the engine
   * @return the int
   * @throws SQLException
   *           the SQL exception
   */
  public int executeUpdate(String sql, Engine engine) throws SQLException {
    return engine.executeUpdateQuery(sql);
  }

  /**
   * Execute query.
   *
   * @param sql
   *          the sql
   * @param statement
   *          the statement
   * @param engine
   *          the engine
   * @return the result set
   * @throws SQLException
   *           the SQL exception
   */
  public ResultSet executeQuery(String sql, Statement statement, Engine engine)
      throws SQLException {
    ResultSetParameters data = engine.executeQuery(sql);
    ResultSet resultData = new MyResultSet(data, statement);
    return resultData;
  }

  /**
   * Gets the execution result.
   *
   * @return the execution result
   * @throws SQLException
   *           the SQL exception
   */
  public boolean getExecutionResult() throws SQLException {
    if (queryType != QueryRunnableCode.GENERAL_QUERY) {
      throw new RuntimeException();
    }
    if (errorFlag) {
      throw new SQLException();// indicate you have an exception
    }
    return executionResult;
  }

  /**
   * Gets the integer execution result.
   *
   * @return the integer execution result
   * @throws SQLException
   *           the SQL exception
   */
  public int getIntegerExecutionResult() throws SQLException {
    if (queryType != QueryRunnableCode.UPDATE_QUERY) {
      throw new RuntimeException();
    }
    if (errorFlag) {
      throw new SQLException();// indicate you have an exception
    }
    return executionIntegerResult;
  }

  /**
   * Gets the result set execution result.
   *
   * @return the result set execution result
   * @throws SQLException
   *           the SQL exception
   */
  public ResultSet getResultSetExecutionResult() throws SQLException {
    if (queryType != QueryRunnableCode.SELECTION_QUERY) {
      throw new RuntimeException();
    }
    if (errorFlag) {
      throw new SQLException();// indicate you have an exception
    }
    return executionResultSet;
  }

}
