package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class DriverImp implements Driver {

  @Override
  public boolean acceptsURL(String arg0) throws SQLException {
    return true;
  }

  @Override
  public Connection connect(String url, Properties info) throws SQLException {

    // File dir = (File) info.get("path");
    String path = ((String) info.get("path")).toLowerCase();
    Connection connect = new ConnectionImp(path);
    return connect;
  }

  @Override
  public int getMajorVersion() {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getMinorVersion() {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {

    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public DriverPropertyInfo[] getPropertyInfo(String arg0, Properties arg1) throws SQLException {
    DriverPropertyInfo[] usedMap = new DriverPropertyInfo[1];
    usedMap[0] = new DriverPropertyInfo("path", " ");
    return usedMap;
  }

  @Override
  public boolean jdbcCompliant() {

    throw new java.lang.UnsupportedOperationException();
  }

}
