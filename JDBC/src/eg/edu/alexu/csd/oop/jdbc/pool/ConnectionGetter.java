package eg.edu.alexu.csd.oop.jdbc.pool;

import java.sql.Connection;

import eg.edu.alexu.csd.oop.jdbc.MyConnection;
import eg.edu.alexu.csd.oop.jdbc.MyConnection.ConnectionPool;

public class ConnectionGetter {

  // class for requires a connection

  private ConnectionPool connectionPool;
  private static ConnectionGetter singleInst;

  // Constructor
  private ConnectionGetter() {
    connectionPool = ConnectionPool.getInstance();
  }

  public static ConnectionGetter getInstance() {
    if (singleInst == null)
      singleInst = new ConnectionGetter();
    return singleInst;
  }

  // call when want to get a connection
  public Connection getConnection(String path) {

    return connectionPool.getConnection(path);
  }

  // call when close connection
  public void releaseConnection(MyConnection connection) {
    connectionPool.releaseConnection(connection);
  }
}
