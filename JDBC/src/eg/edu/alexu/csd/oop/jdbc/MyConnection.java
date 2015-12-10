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
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import eg.edu.alexu.csd.oop.jdbc.engine.Engine;
import eg.edu.alexu.csd.oop.jdbc.log4j.Log4jLogger;

public class MyConnection implements Connection {
	  
	  private Engine engine;
	  private boolean isClosed;
	  
	  public MyConnection(String path){
	    isClosed = false;
	    Log4jLogger.log.debug("Create new Connection");
	    engine = new Engine(path);
	    Log4jLogger.log.debug("new engine instance created");
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
	  public void close() throws SQLException {
	    isClosed = true;
	    Log4jLogger.log.debug("The Connection Closed");
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
	  public Statement createStatement() throws SQLException {
	    if(isClosed){
	      Log4jLogger.log.error("Statment is Closed");
	      throw new SQLException();
	    }else{
	      Statement stat = new MyStatement(engine,this);
	      return stat;
	    }
	    
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
	  public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {

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
	  public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {

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
