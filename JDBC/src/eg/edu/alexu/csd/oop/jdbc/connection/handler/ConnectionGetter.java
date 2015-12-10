package eg.edu.alexu.csd.oop.jdbc.connection.handler;

import java.sql.Connection;

import eg.edu.alexu.csd.oop.jdbc.MyConnection;
import eg.edu.alexu.csd.oop.jdbc.MyConnection.ConnectionPool;

public class ConnectionGetter {// singleton

  // class getting connections from the pool

  private ConnectionPool connectionPool;// the pool
  private static ConnectionGetter singleInst;// the single instance

  // Constructor
  private ConnectionGetter() {
    connectionPool = ConnectionPool.getInstance();
  }

  public static ConnectionGetter getInstance() {
    if (singleInst == null)
      singleInst = new ConnectionGetter();
    return singleInst;
  }

  // called on requiring a connection
  public Connection getConnection(String path) {
    return connectionPool.getConnection(path);
  }

  // called on closing connection
  public void releaseConnection(MyConnection connection) {
    connectionPool.releaseConnection(connection);
  }
}
