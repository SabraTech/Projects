package eg.edu.alexu.csd.oop.jdbc.facade;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import eg.edu.alexu.csd.oop.jdbc.MyDriver;

public class HelperFacade {// not added to the project yet
  private Driver driver;
  private Connection connection;
  private Properties infoProp;

  public static enum DBTypes {
    MyJDBC;
  }

  public HelperFacade(DBTypes dbType, String url, Properties info) {
    switch (dbType) {
    case MyJDBC:
      driver = new MyDriver();
      infoProp = info;
      try {
        connection = driver.connect("jdbc:xmldb://localhost", infoProp);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      break;
    }

  }

  public Connection getDataBase() {
    return connection;
  }
}
