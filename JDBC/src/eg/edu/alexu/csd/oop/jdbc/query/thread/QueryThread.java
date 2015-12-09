package eg.edu.alexu.csd.oop.jdbc.query.thread;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import eg.edu.alexu.csd.oop.jdbc.ResultSetImp;
import eg.edu.alexu.csd.oop.jdbc.engine.Engine;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.QueryValidatorAndParser;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.ResultSetParameters;

public class QueryThread implements Runnable {
  private Engine engine;
  private int queryType;
  private Statement statement;
  private String sql;
  private boolean executionResult;
  private int executionIntegerResult;
  private ResultSet executionResultSet;
  private QueryValidatorAndParser queryValidatorAndParser;
  public static final int generalQuery = 1;
  public static final int updateQuery = 2;
  public static final int selectionQuery = 3;

  public QueryThread(Engine engine, int queryType, Statement statement, String sql) {
    this.engine = engine;
    this.queryType = queryType;
    this.statement = statement;
    this.sql = sql;

  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    switch (queryType) {
    case QueryThread.generalQuery:
      try {
        executionResult = execute(sql, engine);
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        throw new RuntimeException();
      }
      break;
    case QueryThread.updateQuery:
      try {
        executionIntegerResult = executeUpdate(sql, engine);
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        throw new RuntimeException();
      }
      break;
    case QueryThread.selectionQuery:
      try {
        executionResultSet = executeQuery(sql, statement, engine);
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        throw new RuntimeException();
      }
      break;
    default:
      throw new RuntimeException("matched no where");
    }

  }

  public boolean execute(String sql, Engine engine) throws SQLException {
    int type = queryValidatorAndParser.isValidQuery(sql);
    if (type == QueryValidatorAndParser.structueQuery) {
      // return
      if (engine.executeStructureQuery(sql))
        return true;
      throw new RuntimeException("about to return false at structure query " + sql);
    } else if (type == QueryValidatorAndParser.updateQuery) {
      int updateCount = engine.executeUpdateQuery(sql);
      if (updateCount > 0) {
        return true;
      } else {
        // return false;
        throw new RuntimeException("about to return false at an update query " + sql);
      }
    } else if (type == QueryValidatorAndParser.selectionQuery) {
      ResultSetParameters result = engine.executeQuery(sql);
      if (result != null) {
        Object[][] tableData = result.getSelectedData();
        if (tableData.length != 0) {
          return true;
        } else {
          // return false;
          throw new RuntimeException("about to return false at a selection query " + sql);
        }
      } else {
        throw new RuntimeException("matched with non " + sql);
        // return false;
      }
    }
    return false;
  }

  public int executeUpdate(String sql, Engine engine) throws SQLException {
    return engine.executeUpdateQuery(sql);
  }

  public ResultSet executeQuery(String sql, Statement statement, Engine engine)
      throws SQLException {
    ResultSetParameters data = engine.executeQuery(sql);
    ResultSet resultData = new ResultSetImp(data, statement);
    return resultData;
  }

  public boolean getExecutionResult() {
    if (queryType != QueryThread.generalQuery) {
      throw new RuntimeException();
    }
    return executionResult;
  }

  public int getIntegerExecutionResult() {
    if (queryType != QueryThread.updateQuery) {
      throw new RuntimeException();
    }
    return executionIntegerResult;
  }

  public ResultSet getResultSetExecutionResult() {
    if (queryType != QueryThread.selectionQuery) {
      throw new RuntimeException();
    }
    return executionResultSet;
  }

}
