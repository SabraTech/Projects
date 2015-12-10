package eg.edu.alexu.csd.oop.jdbc.query.code;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import eg.edu.alexu.csd.oop.jdbc.MyResultSet;
import eg.edu.alexu.csd.oop.jdbc.engine.Engine;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.QueryValidatorAndParser;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.ResultSetParameters;

public class QueryRunnableCode implements Runnable {
  private Engine engine;
  private int queryType;
  private Statement statement;
  private String sql;
  private boolean executionResult;
  private int executionIntegerResult;
  private ResultSet executionResultSet;
  private QueryValidatorAndParser queryValidatorAndParser;
  private boolean errorFlag;// indicates that an sql exception was thrown
  // three integers for outer classes to use when calling the constructor
  // to indicate the exact
  // code that should be executed
  public static final int generalQuery = 1;
  public static final int updateQuery = 2;
  public static final int selectionQuery = 3;

  public QueryRunnableCode(Engine engine, int queryType, Statement statement, String sql,
      QueryValidatorAndParser queryValidatorAndParser) {
    this.engine = engine;
    this.queryType = queryType;
    this.statement = statement;
    this.sql = sql;
    this.queryValidatorAndParser = queryValidatorAndParser;

  }

  @Override
  public void run() {
    switch (queryType) {
    case QueryRunnableCode.generalQuery:
      try {
        executionResult = execute(sql, engine);
      } catch (SQLException e) {
        errorFlag = true;// an sql exception
      }
      break;
    case QueryRunnableCode.updateQuery:
      try {
        executionIntegerResult = executeUpdate(sql, engine);
      } catch (SQLException e) {
        errorFlag = true;// an sql exception
      }
      break;
    case QueryRunnableCode.selectionQuery:
      try {
        executionResultSet = executeQuery(sql, statement, engine);
      } catch (SQLException e) {
        errorFlag = true;// an sql exception
      }
      break;
    }

  }

  public boolean execute(String sql, Engine engine) throws SQLException {
    int type = queryValidatorAndParser.getQueryType(sql);
    if (type == QueryValidatorAndParser.structueQuery) {
      return engine.executeStructureQuery(sql);
    } else if (type == QueryValidatorAndParser.updateQuery) {
      int updateCount = engine.executeUpdateQuery(sql);
      if (updateCount > 0) {
        return true;
      } else {
        return false;
      }
    } else if (type == QueryValidatorAndParser.selectionQuery) {
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

  public int executeUpdate(String sql, Engine engine) throws SQLException {
    return engine.executeUpdateQuery(sql);
  }

  public ResultSet executeQuery(String sql, Statement statement, Engine engine)
      throws SQLException {
    ResultSetParameters data = engine.executeQuery(sql);
    ResultSet resultData = new MyResultSet(data, statement);
    return resultData;
  }

  public boolean getExecutionResult() throws SQLException {
    if (queryType != QueryRunnableCode.generalQuery) {
      throw new RuntimeException();
    }
    if (errorFlag) {
      throw new SQLException();// indicate you have an exception
    }
    return executionResult;
  }

  public int getIntegerExecutionResult() throws SQLException {
    if (queryType != QueryRunnableCode.updateQuery) {
      throw new RuntimeException();
    }
    if (errorFlag) {
      throw new SQLException();// indicate you have an exception
    }
    return executionIntegerResult;
  }

  public ResultSet getResultSetExecutionResult() throws SQLException {
    if (queryType != QueryRunnableCode.selectionQuery) {
      throw new RuntimeException();
    }
    if (errorFlag) {
      throw new SQLException();// indicate you have an exception
    }
    return executionResultSet;
  }

}
