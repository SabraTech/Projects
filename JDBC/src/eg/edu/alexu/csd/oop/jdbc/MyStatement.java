package eg.edu.alexu.csd.oop.jdbc;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.jdbc.engine.Engine;
import eg.edu.alexu.csd.oop.jdbc.query.code.QueryRunnableCode;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.QueryValidatorAndParser;

public class MyStatement implements Statement {

  private Engine currentEngine;
  private Connection currentConnection;
  private ArrayList<String> batch;
  private int time;
  private QueryValidatorAndParser queryValidatorAndParser;
  private boolean isClosed;

  public MyStatement(Engine engine, Connection connection) {
    isClosed = false;
    currentEngine = engine;
    currentConnection = connection;
    batch = new ArrayList<String>();
    time = Integer.MAX_VALUE; // means no limit
    queryValidatorAndParser = new QueryValidatorAndParser();
  }

  @Override
  // Adds the given SQL command to the current list of commands for this
  // Statement object.
  public void addBatch(String sql) throws SQLException {
    // check that SQl query is correct and then add it to List Array
    if (isClosed) {
      throw new SQLException();
    } else {
      batch.add(sql);
    }
  }

  @Override
  // Empties this Statement object's current list of SQL commands.
  public void clearBatch() throws SQLException {
    // Clear the list
    if (isClosed) {
      throw new SQLException();
    } else {
      batch.clear();
    }

  }

  @Override
  // Releases this Statement object's database and JDBC resources immediately
  // instead of waiting for this to happen when it is automatically closed.
  public void close() throws SQLException {
    isClosed = true;
  }

  @Override
  // Executes the given SQL statement, which may return multiple results.
  public boolean execute(String sql) throws SQLException {
    if (isClosed) {
      throw new SQLException();
    } else {
      QueryRunnableCode queryCode = new QueryRunnableCode(currentEngine,
          QueryRunnableCode.generalQuery, this, sql, queryValidatorAndParser);// the
                                                                              // query
                                                                              // code
      Thread queryThread = new Thread(queryCode, "Query Thread");// it's
                                                                 // executing
                                                                 // thread
      queryThread.start();
      try {
        queryThread.join(((long) time) * 1000);// wait for the query either the
                                               // user-determined time or it's
                                               // return
        if (queryThread.isAlive()) {// if the thread is still alive after time
                                    // has passed, kill it
          queryThread.interrupt();
          throw new SQLTimeoutException();
        }
      } catch (InterruptedException e) {
        // shouldn't happen
      }
      // execution continued normally
      // an SQL exception may be throw on executing this line
      return queryCode.getExecutionResult();
    }
  }

  @Override
  // Submits a batch of commands to the database for execution and if all
  // commands execute successfully, returns an array of update counts.
  public int[] executeBatch() throws SQLException {
    if (isClosed) {
      throw new SQLException();
    } else {
      boolean errorFlag = false;
      int[] updateCount = new int[batch.size()];
      int counter = 0;
      for (String query : batch) {
        try {
          updateCount[counter] = executeUpdate(query);
        } catch (SQLTimeoutException e) {
          throw new SQLTimeoutException();
        } catch (SQLException e2) {
          errorFlag = true;
          updateCount[counter] = EXECUTE_FAILED;
        }
        counter++;
      }
      batch.clear();
      if (errorFlag)
        throw new BatchUpdateException(updateCount);
      else
        return updateCount;
    }

  }

  @Override
  // Executes the given SQL statement, which returns a single ResultSet
  // object.
  public ResultSet executeQuery(String sql) throws SQLException {
    if (isClosed) {
      throw new SQLException();
    } else {
      QueryRunnableCode queryCode = new QueryRunnableCode(currentEngine,
          QueryRunnableCode.selectionQuery, this, sql, queryValidatorAndParser);
      Thread queryThread = new Thread(queryCode, "Query Thread");
      queryThread.start();
      try {
        queryThread.join(((long) time) * 1000);
        if (queryThread.isAlive()) {
          queryThread.interrupt();
          throw new SQLTimeoutException();
        }
      } catch (InterruptedException e) {
        // shouldn't happen
      }
      // execution continued normally
      // an sql exception may be thrown right here
      return queryCode.getResultSetExecutionResult();
    }

  }

  @Override
  // Executes the given SQL statement, which may be an INSERT, UPDATE, or
  // DELETE statement or an SQL statement that returns nothing, such as an SQL
  // DDL statement.
  public int executeUpdate(String sql) throws SQLException {
    if (isClosed) {
      throw new SQLException();
    } else {
      QueryRunnableCode queryCode = new QueryRunnableCode(currentEngine,
          QueryRunnableCode.updateQuery, this, sql, queryValidatorAndParser);
      Thread queryThread = new Thread(queryCode, "Query Thread");
      queryThread.start();
      try {
        queryThread.join(((long) time) * 1000);
        if (queryThread.isAlive()) {
          queryThread.interrupt();
          throw new SQLTimeoutException();
        }
      } catch (InterruptedException e) {
        // shouldn't happen
      }
      // execution continued normally
      // an sql exception may be thrown right here
      return queryCode.getIntegerExecutionResult();
    }
  }

  @Override
  // Retrieves the Connection object that produced this Statement object.
  public Connection getConnection() throws SQLException {
    return this.currentConnection;
  }

  @Override
  // Retrieves the number of seconds the driver will wait for a Statement
  // object to execute.
  public int getQueryTimeout() throws SQLException {
    if (isClosed) {
      throw new SQLException();
    } else {
      return this.time;
    }
  }

  @Override
  // Sets the number of seconds the driver will wait for a Statement object to
  // execute to the given number of seconds.
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

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void cancel() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void clearWarnings() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void closeOnCompletion() throws SQLException {

    throw new java.lang.UnsupportedOperationException();
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

}
