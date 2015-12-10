package eg.edu.alexu.csd.oop.jdbc.log4j;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import eg.edu.alexu.csd.oop.jdbc.MyDriver;

public class Log4jLogger {
  
  public static final Logger log = Logger.getLogger(Log4jLogger.class.getName());
  
  public static void main(String[] args) {
    PropertyConfigurator.configure("log4j.properties");
    Driver dir = new MyDriver();
    Properties info = new Properties();
    File dbDir = new File("/debug/db/test/sample");
    info.put("path", dbDir.getAbsoluteFile());
    try {
      Connection connect = dir.connect("jdbc:xmldb://localhost", info);
      Statement stat = connect.createStatement();
      stat.execute("DROP DATABASE new");
      stat.execute("CREATE DATABASE new");
      stat.close();
      stat.equals("CREATE DATABASE new2");
      Statement stat1 = connect.createStatement();
      stat1.execute("CREATE TABLE table1 (id int, names varchar)");
      stat1.execute("INSERT INTO table1 VALUES (1, m)");
      stat1.execute("INSERT INTO table1 VALUES (2, h)");
      stat1.execute("INSERT INTO table1 VALUES (3, a)");
      stat1.execute("DELETE FROM table1 WHERE id<2");
      stat1.execute("SELECT * FROM table1");
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

}
