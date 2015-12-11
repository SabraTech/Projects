package eg.edu.alexu.csd.oop.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import eg.edu.alexu.csd.oop.jdbc.connection.handler.ConnectionGetter;
import eg.edu.alexu.csd.oop.jdbc.engine.Engine;

public class MyConnection implements Connection {

  private Engine engine;
  private String path;

  public static class ConnectionPool {
    // singleton class
    private int maximumConnections;// to limit the no of maximum stored
                                   // connections for memory issues
    private static ConnectionPool singleInstance;
    private Map<String, Connection> pool;

    // private Constructor
    private ConnectionPool() {
      pool = new HashMap<String, Connection>();
      maximumConnections = 5;
    }

    // static method to get instance
    public static ConnectionPool getInstance() {
      if (singleInstance == null) {
        singleInstance = new ConnectionPool();
      }
      return singleInstance;
    }

    public void setMaximumStoredConnections(int max) {// sets the maximum
                                                      // number
                                                      // of
                                                      // stored
                                                      // connections
      if (max < 0) {
        throw new IllegalArgumentException();
      }
      maximumConnections = max;
    }

    // call when required connection return connection with required path
    public Connection getConnection(String path) {
      if (pool.containsKey(path)) {
        return pool.remove(path);// remove it from the map so as not to
                                 // be given
                                 // to another client
      }
      // no matching path, so create new connection with specified path
      return new MyConnection(path);
    }

    // called when connection is closed, re-add it to the pool
    public void releaseConnection(MyConnection releasedConnection) {
      if (pool.size() < maximumConnections)
        pool.put(releasedConnection.getPath(), releasedConnection);
    }

  }

  private MyConnection(String path) {// no outer classes can instantiate the
                                     // connection Class
    engine = new Engine(path);
    this.path = path;
  }

  public String getPath() {// to get the path on releasing the connection
    return path;
  }

  @Override
  // Releases this Connection object's database and JDBC resources immediately
  // instead of waiting for them to be automatically released
  public void close() throws SQLException {
    ConnectionGetter.getInstance().releaseConnection(this);// returns the
    // connection instance
    // to it's pool
  }

  @Override
  // Creates a Statement object for sending SQL statements to the database
  public Statement createStatement() throws SQLException {
    Statement statement = new MyStatement(engine, this);
    return statement;

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
  public void abort(Executor arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void clearWarnings() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void commit() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Blob createBlob() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Clob createClob() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public NClob createNClob() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public SQLXML createSQLXML() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Statement createStatement(int arg0, int arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean getAutoCommit() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public String getCatalog() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Properties getClientInfo() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public String getClientInfo(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getHoldability() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public DatabaseMetaData getMetaData() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getNetworkTimeout() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public String getSchema() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getTransactionIsolation() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Map<String, Class<?>> getTypeMap() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean isClosed() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean isReadOnly() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean isValid(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public String nativeSQL(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public CallableStatement prepareCall(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public CallableStatement prepareCall(String arg0, int arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3)
      throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public PreparedStatement prepareStatement(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3)
      throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void releaseSavepoint(Savepoint arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void rollback() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void rollback(Savepoint arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setAutoCommit(boolean arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setCatalog(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setClientInfo(Properties arg0) throws SQLClientInfoException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setClientInfo(String arg0, String arg1) throws SQLClientInfoException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setHoldability(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setReadOnly(boolean arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Savepoint setSavepoint() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Savepoint setSavepoint(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setSchema(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setTransactionIsolation(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

}
