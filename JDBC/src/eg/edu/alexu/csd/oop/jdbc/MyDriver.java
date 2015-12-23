package eg.edu.alexu.csd.oop.jdbc;

import eg.edu.alexu.csd.oop.jdbc.connection.handler.ConnectionGetter;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * The Class MyDriver.
 */
public class MyDriver implements Driver {

  /** The connection getter. */
  ConnectionGetter connectionGetter;

  /**
   * Instantiates a new my driver.
   */
  public MyDriver() {
    connectionGetter = ConnectionGetter.getInstance();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Driver#acceptsURL(java.lang.String)
   */
  @Override
  // Retrieves whether the driver thinks that it can open a connection to the
  // given URL.
  public boolean acceptsURL(String url) throws SQLException {
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Driver#getPropertyInfo(java.lang.String,
   * java.util.Properties)
   */
  @Override
  // Gets information about the possible properties for this driver.
  public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
    DriverPropertyInfo[] usedMap = new DriverPropertyInfo[1];
    usedMap[0] = new DriverPropertyInfo("path", " ");
    return usedMap;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Driver#connect(java.lang.String, java.util.Properties)
   */
  @Override
  // Attempts to make a database connection to the given URL.
  public Connection connect(String url, Properties info) throws SQLException {
    File dir = (File) info.get("path");
    String path = dir.getAbsolutePath();
    return connectionGetter.getConnection(path);// connectionGetter is
                                                // responsible for dealing
                                                // wit // the connection
                                                // pool
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Driver#getMajorVersion()
   */
  @Override
  public int getMajorVersion() {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Driver#getMinorVersion()
   */
  @Override
  public int getMinorVersion() {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Driver#getParentLogger()
   */
  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Driver#jdbcCompliant()
   */
  @Override
  public boolean jdbcCompliant() {
    throw new java.lang.UnsupportedOperationException();
  }

}
