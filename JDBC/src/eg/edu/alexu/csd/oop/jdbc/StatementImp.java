package eg.edu.alexu.csd.oop.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;

import eg.edu.alexu.csd.oop.jdbc.engine.Engine;
import eg.edu.alexu.csd.oop.jdbc.query.thread.QueryThread;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.MyEntry;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.QueryValidatorAndParser;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.ResultSetParameters;

/**
 * The Class StatementImp.
 */

public class StatementImp implements Statement {

  private Engine currentEngine;
  private Connection currentConnection;
  private ArrayList<MyEntry<String, Integer>> batchList;
  private int time;
  private QueryValidatorAndParser queryValidatorAndParser;
  private boolean isClosed;

  public StatementImp(Engine engine, Connection conct) {
    isClosed = false;
    currentEngine = engine;
    currentConnection = conct;
    batchList = new ArrayList<MyEntry<String, Integer>>();
    time = Integer.MAX_VALUE; // means no limit
    queryValidatorAndParser = new QueryValidatorAndParser();
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void addBatch(String sql) throws SQLException {
    // check that sql query is correct and then add it to listarray
    if (isClosed) {
      throw new SQLException();
    } else {
      int type = queryValidatorAndParser.isValidQuery(sql);
      batchList.add(new MyEntry<String, Integer>(sql, type));
    }

  }

  @Override
  public void cancel() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void clearBatch() throws SQLException {
    // Clear the list
    if (isClosed) {
      throw new SQLException();
    } else {
      batchList.clear();
    }

  }

  @Override
  public void clearWarnings() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void close() throws SQLException {
    isClosed = true;
  }

  @Override
  public void closeOnCompletion() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean execute(String sql) throws SQLException {
    if (isClosed) {
      throw new SQLException();
    } else {
      QueryThread queryCode = new QueryThread(currentEngine, QueryThread.generalQuery, this, sql);
      Thread queryThread = new Thread(queryCode);
      queryThread.start();
      try {
        queryThread.join(time * 1000);
        if (queryThread.isAlive()) {
          queryThread.interrupt();
          throw new RuntimeException("query timed-out");
        }
      } catch (InterruptedException e) {
        // shouldn't happen
        throw new RuntimeException("Interrupted");
      }
      // execution continued normally
      return queryCode.getExecutionResult();
    }
  }

  @Override
  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean execute(String sql, int[] columnIndexes) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean execute(String sql, String[] columnNames) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int[] executeBatch() throws SQLException {
    if (isClosed) {
      throw new SQLException();
    } else {
      int[] updateCount = new int[batchList.size()];
      int counter = 0;
      for (MyEntry<String, Integer> query : batchList) {
        String sql = query.getFirst();
        int type = query.getSecond();
        if (type == 1) {
          execute(sql);
        } else if (type == 2) {
          updateCount[counter] = executeUpdate(sql);
        } else if (type == 3) {
          executeQuery(sql);
        }
        counter++;
      }
      return updateCount;
    }

  }

  @Override
  public ResultSet executeQuery(String sql) throws SQLException {
    if (isClosed) {
      throw new SQLException();
    } else {
      ResultSetParameters data = currentEngine.executeQuery(sql);
      ResultSet resultData = new ResultSetImp(data, this);
      return resultData;
    }

  }

  @Override
  public int executeUpdate(String sql) throws SQLException {
    return currentEngine.executeUpdateQuery(sql);
  }

  @Override
  public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int executeUpdate(String sql, String[] columnNames) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Connection getConnection() throws SQLException {
    return this.currentConnection;
  }

  @Override
  public int getFetchDirection() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getFetchSize() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public ResultSet getGeneratedKeys() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getMaxFieldSize() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getMaxRows() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean getMoreResults() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean getMoreResults(int current) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getQueryTimeout() throws SQLException {
    if (isClosed) {
      throw new SQLException();
    } else {
      return this.time;
    }

  }

  @Override
  public ResultSet getResultSet() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getResultSetConcurrency() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getResultSetHoldability() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getResultSetType() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getUpdateCount() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean isCloseOnCompletion() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean isClosed() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean isPoolable() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setCursorName(String name) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setEscapeProcessing(boolean enable) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setFetchDirection(int direction) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setFetchSize(int rows) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setMaxFieldSize(int max) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setMaxRows(int max) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setPoolable(boolean poolable) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setQueryTimeout(int seconds) throws SQLException {
    if (isClosed) {
      throw new SQLException();
    } else {
      if (seconds > Integer.MAX_VALUE / 1000 || seconds <= 0) {
        throw new IllegalArgumentException();
      }
      this.time = seconds;
    }

  }

}
