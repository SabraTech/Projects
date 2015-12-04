package eg.edu.alexu.csd.oop.jdbc;

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

		return false;
	}

	@Override
	public Connection connect(String arg0, Properties info) throws SQLException {
	  
	  Object path = info.get("path");
	  throw new RuntimeException(String.valueOf(path));
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

		return null;
	}

	@Override
	public boolean jdbcCompliant() {

		throw new java.lang.UnsupportedOperationException();
	}

}
