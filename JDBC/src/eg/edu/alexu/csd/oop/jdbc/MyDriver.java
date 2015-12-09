package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class MyDriver implements Driver {

	@Override
	// Retrieves whether the driver thinks that it can open a connection to the
	// given URL.
	public boolean acceptsURL(String url) throws SQLException {
		return true;
	}

	@Override
	// Gets information about the possible properties for this driver.
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
			throws SQLException {
		DriverPropertyInfo[] usedMap = new DriverPropertyInfo[1];
		usedMap[0] = new DriverPropertyInfo("path", " ");
		return usedMap;
	}

	@Override
	// Attempts to make a database connection to the given URL.
	public Connection connect(String url, Properties info) throws SQLException {
		File dir = (File) info.get("path");
		String path = dir.getAbsolutePath();
		Connection connect = new MyConnection(path);
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
	public boolean jdbcCompliant() {
		throw new java.lang.UnsupportedOperationException();
	}

}
