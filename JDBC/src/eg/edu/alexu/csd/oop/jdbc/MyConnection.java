package eg.edu.alexu.csd.oop.jdbc;

import eg.edu.alexu.csd.oop.jdbc.connection.handler.ConnectionGetter;
import eg.edu.alexu.csd.oop.jdbc.engine.Engine;

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

/**
 * The Class MyConnection.
 */
public class MyConnection implements Connection {

  /** The engine. */
  private Engine engine;

  /** The path. */
  private String path;

  /**
   * The Class ConnectionPool.
   */
  public static class ConnectionPool {// singleton class

    /** The maximum connections. */
    private int maximumConnections;// to limit the no of maximum stored

    /** The single instance. */
    private static ConnectionPool singleInstance;// connections for memory
                                                 // issues

    /** The pool. */
    private Map<String, Connection> pool;

    /**
     * Instantiates a new connection pool.
     */
    private ConnectionPool() { // private Constructor
      pool = new HashMap<String, Connection>();
      maximumConnections = 5;
    }

    /**
     * Gets the single instance of ConnectionPool.
     *
     * @return single instance of ConnectionPool
     */
    // static method to get instance
    public static ConnectionPool getInstance() {
      if (singleInstance == null) {
        singleInstance = new ConnectionPool();
      }
      return singleInstance;
    }

    /**
     * Sets the maximum stored connections.
     *
     * @param max
     *          the new maximum stored connections
     */
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

    /**
     * Gets the connection.
     *
     * @param path
     *          the path
     * @return the connection
     */
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

    /**
     * Release connection.
     *
     * @param releasedConnection
     *          the released connection
     */
    // called when connection is closed, re-add it to the pool
    public void releaseConnection(MyConnection releasedConnection) {
      if (pool.size() < maximumConnections)
        pool.put(releasedConnection.getPath(), releasedConnection);
    }

  }

  /**
   * Instantiates a new my connection.
   *
   * @param path
   *          the path
   */
  private MyConnection(String path) {// no outer classes can instantiate the
                                     // connection Class
    engine = new Engine(path);
    this.path = path;
  }

  /**
   * Gets the path.
   *
   * @return the path
   */
  public String getPath() {// to get the path on releasing the connection
    return path;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#close()
   */
  @Override
  // Releases this Connection object's database and JDBC resources immediately
  // instead of waiting for them to be automatically released
  public void close() throws SQLException {
    ConnectionGetter.getInstance().releaseConnection(this);// returns the
    // connection instance
    // to it's pool
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#createStatement()
   */
  @Override
  // Creates a Statement object for sending SQL statements to the database
  public Statement createStatement() throws SQLException {
    Statement statement = new MyStatement(engine, this);
    return statement;

  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
   */
  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Wrapper#unwrap(java.lang.Class)
   */
  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#abort(java.util.concurrent.Executor)
   */
  @Override
  public void abort(Executor arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#clearWarnings()
   */
  @Override
  public void clearWarnings() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#commit()
   */
  @Override
  public void commit() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#createArrayOf(java.lang.String,
   * java.lang.Object[])
   */
  @Override
  public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#createBlob()
   */
  @Override
  public Blob createBlob() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#createClob()
   */
  @Override
  public Clob createClob() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#createNClob()
   */
  @Override
  public NClob createNClob() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#createSQLXML()
   */
  @Override
  public SQLXML createSQLXML() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#createStatement(int, int)
   */
  @Override
  public Statement createStatement(int arg0, int arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#createStatement(int, int, int)
   */
  @Override
  public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#createStruct(java.lang.String, java.lang.Object[])
   */
  @Override
  public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#getAutoCommit()
   */
  @Override
  public boolean getAutoCommit() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#getCatalog()
   */
  @Override
  public String getCatalog() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#getClientInfo()
   */
  @Override
  public Properties getClientInfo() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#getClientInfo(java.lang.String)
   */
  @Override
  public String getClientInfo(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#getHoldability()
   */
  @Override
  public int getHoldability() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#getMetaData()
   */
  @Override
  public DatabaseMetaData getMetaData() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#getNetworkTimeout()
   */
  @Override
  public int getNetworkTimeout() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#getSchema()
   */
  @Override
  public String getSchema() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#getTransactionIsolation()
   */
  @Override
  public int getTransactionIsolation() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#getTypeMap()
   */
  @Override
  public Map<String, Class<?>> getTypeMap() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#getWarnings()
   */
  @Override
  public SQLWarning getWarnings() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#isClosed()
   */
  @Override
  public boolean isClosed() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#isReadOnly()
   */
  @Override
  public boolean isReadOnly() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#isValid(int)
   */
  @Override
  public boolean isValid(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#nativeSQL(java.lang.String)
   */
  @Override
  public String nativeSQL(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#prepareCall(java.lang.String)
   */
  @Override
  public CallableStatement prepareCall(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#prepareCall(java.lang.String, int, int)
   */
  @Override
  public CallableStatement prepareCall(String arg0, int arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#prepareCall(java.lang.String, int, int, int)
   */
  @Override
  public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3)
      throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#prepareStatement(java.lang.String)
   */
  @Override
  public PreparedStatement prepareStatement(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#prepareStatement(java.lang.String, int)
   */
  @Override
  public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#prepareStatement(java.lang.String, int[])
   */
  @Override
  public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#prepareStatement(java.lang.String,
   * java.lang.String[])
   */
  @Override
  public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#prepareStatement(java.lang.String, int, int)
   */
  @Override
  public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#prepareStatement(java.lang.String, int, int, int)
   */
  @Override
  public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3)
      throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
   */
  @Override
  public void releaseSavepoint(Savepoint arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#rollback()
   */
  @Override
  public void rollback() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#rollback(java.sql.Savepoint)
   */
  @Override
  public void rollback(Savepoint arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#setAutoCommit(boolean)
   */
  @Override
  public void setAutoCommit(boolean arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#setCatalog(java.lang.String)
   */
  @Override
  public void setCatalog(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#setClientInfo(java.util.Properties)
   */
  @Override
  public void setClientInfo(Properties arg0) throws SQLClientInfoException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#setClientInfo(java.lang.String, java.lang.String)
   */
  @Override
  public void setClientInfo(String arg0, String arg1) throws SQLClientInfoException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#setHoldability(int)
   */
  @Override
  public void setHoldability(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#setNetworkTimeout(java.util.concurrent.Executor,
   * int)
   */
  @Override
  public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#setReadOnly(boolean)
   */
  @Override
  public void setReadOnly(boolean arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#setSavepoint()
   */
  @Override
  public Savepoint setSavepoint() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#setSavepoint(java.lang.String)
   */
  @Override
  public Savepoint setSavepoint(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#setSchema(java.lang.String)
   */
  @Override
  public void setSchema(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#setTransactionIsolation(int)
   */
  @Override
  public void setTransactionIsolation(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Connection#setTypeMap(java.util.Map)
   */
  @Override
  public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

}
