package eg.edu.alexu.csd.oop.jdbc.connection.handler;

import eg.edu.alexu.csd.oop.jdbc.MyConnection;
import eg.edu.alexu.csd.oop.jdbc.MyConnection.ConnectionPool;

import java.sql.Connection;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionGetter.
 */
public class ConnectionGetter {// singleton

  // class getting connections from the pool

  /** The connection pool. */
private ConnectionPool connectionPool;// the pool
  
  /** The single inst. */
  private static ConnectionGetter singleInst;// the single instance

  /**
   * Instantiates a new connection getter.
   */
  // Constructor
  private ConnectionGetter() {
    connectionPool = ConnectionPool.getInstance();
  }

  /**
   * Gets the single instance of ConnectionGetter.
   *
   * @return single instance of ConnectionGetter
   */
  public static ConnectionGetter getInstance() {
    if (singleInst == null) {
      singleInst = new ConnectionGetter();
    }
    return singleInst;
  }

  /**
   * Gets the connection.
   *
   * @param path the path
   * @return the connection
   */
  // called on requiring a connection
  public Connection getConnection(String path) {
    return connectionPool.getConnection(path);
  }

  /**
   * Release connection.
   *
   * @param connection the connection
   */
  // called on closing connection
  public void releaseConnection(MyConnection connection) {
    connectionPool.releaseConnection(connection);
  }
}
