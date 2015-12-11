package eg.edu.alexu.csd.oop.jdbc.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import eg.edu.alexu.csd.oop.jdbc.MyDriver;

public class Gui {

  private JFrame frame;

  private JButton connect, disconnect, pathChooser, execute, addBatch, clearBatch, executeBatch,
      clearQuery, clearResult;

  private TextArea queryArea, infoArea;

  private JLabel pathLabel1, pathLabel2;

  private Panel connectPanel, queryPanel, buttonsPanel, infoPanel;

  private Connection connection;

  private String path;

  public Gui() {
    initialize();
  }

  public JFrame getFrame() {
    return frame;
  }

  private void initialize() {
    frame = new JFrame();
    frame.setSize(500, 550);
    frame.setLocationRelativeTo(null);
    frame.setTitle("JDBC Application");
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new FlowLayout());
    connectPanel = new Panel();
    queryPanel = new Panel();
    buttonsPanel = new Panel();
    infoPanel = new Panel();
    connectPanel.setLayout(new GridLayout(2, 2, 30, 30));
    queryPanel.setLayout(new BorderLayout());
    buttonsPanel.setLayout(new GridLayout(2, 3, 15, 15));
    pathLabel1 = new JLabel("Path");
    pathLabel2 = new JLabel("");
    pathLabel2.setForeground(Color.green);
    pathChooser = new JButton("Browse Path");
    queryArea = new TextArea("Enter query", 8, 60);
    infoArea = new TextArea("Result", 8, 60);
    connect = new JButton("Connect");
    disconnect = new JButton("Disconnect");
    execute = new JButton("Execute Query");
    addBatch = new JButton("Add to Batch");
    clearBatch = new JButton("Clear Batch");
    executeBatch = new JButton("Execute Batch");
    clearQuery = new JButton("Clear Query");
    clearResult = new JButton("Clear Result");
    connectPanel.add(pathLabel1);
    connectPanel.add(pathLabel2);
    connectPanel.add(pathChooser);
    connectPanel.add(connect);
    connectPanel.add(disconnect);
    queryPanel.add("Center", queryArea);
    buttonsPanel.add(execute);
    buttonsPanel.add(addBatch);
    buttonsPanel.add(clearBatch);
    buttonsPanel.add(executeBatch);
    buttonsPanel.add(clearQuery);
    buttonsPanel.add(clearResult);
    queryPanel.add("South", buttonsPanel);
    infoPanel.add(infoArea);
    frame.add(connectPanel);
    frame.add(queryPanel);
    frame.add(infoPanel);

    pathChooser.addActionListener(new ChooseFile());
    connect.addActionListener(new ConnectDB());
    disconnect.addActionListener(new DisconnectDB());
    execute.addActionListener(new Execute());
    addBatch.addActionListener(new AddBatch());
    clearBatch.addActionListener(new ClearBatch());
    executeBatch.addActionListener(new ExecuteBatch());
    clearQuery.addActionListener(new ClearQuery());
    clearResult.addActionListener(new ClearResult());

  }

  public class ChooseFile implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Choose DB path");
      chooser.setCurrentDirectory(new java.io.File("."));
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      chooser.setAcceptAllFileFilterUsed(false);
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        path = chooser.getCurrentDirectory().toString();
        pathLabel2.setText("Path Selected");
        // System.out.println(path);
      }

    }
  }

  public class ConnectDB implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        // logger should be here;
        Driver driver = new MyDriver();
        Properties info = new Properties();
        File dbDir = new File(path);
        info.put("path", dbDir.getAbsoluteFile());
        connection = driver.connect("jdbc:xmldb://localhost", info);
        infoArea.setText("Connection Success");
      } catch (Exception ex) {
        infoArea.setText(ex.toString());
      }
    }
  }

  public class DisconnectDB implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        connection.close();
        infoArea.append("\nConnection Closed");
      } catch (SQLException e1) {
        infoArea.setText("Error in disconnection");
      }
    }
  }

  public class Execute implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        Statement statement;
        ResultSet resultSet;
        ResultSetMetaData metaData;
        String query;
        boolean more;
        statement = connection.createStatement();
        int count = 0;
        query = queryArea.getText();
        if (query.toLowerCase().contains("select")) {
          resultSet = statement.executeQuery(query);
          metaData = resultSet.getMetaData();
          more = resultSet.next();
          if (!more) {
            infoArea.append("\n No Results");
            return;
          }
          int columnCount = metaData.getColumnCount();
          infoArea.append("\n");
          for (int counter = 1; counter <= columnCount; counter++) {
            infoArea.append(metaData.getColumnName(counter) + "\t");
          }
          while (more) {
            infoArea.append("\n");
            for (int counter = 1; counter <= columnCount; counter++) {
              if (metaData.getColumnType(counter) == java.sql.Types.INTEGER) {
                infoArea.append(String.valueOf(resultSet.getInt(counter)) + "\t");
              } else if (metaData.getColumnType(counter) == java.sql.Types.VARCHAR) {
                infoArea.append(resultSet.getString(counter) + "\t");
              }
            }
            count++;
            more = resultSet.next();
          }
          infoArea.append("\n" + count + " rows Selected");
          resultSet.close();
          statement.close();
        } else if (query.toLowerCase().contains("update")) {
          count = statement.executeUpdate(query);
          infoArea.append("\n");
          if (count == 0) {
            infoArea.append("No Rows Updated");
          } else {
            infoArea.append(count + " rows Updated");
          }
          statement.close();
        } else if (query.toLowerCase().contains("insert")) {
          count = statement.executeUpdate(query);
          infoArea.append("\n");
          if (count == 0) {
            infoArea.append("No Rows Inserted");
          } else {
            infoArea.append(count + " rows Inserted");
          }
          statement.close();
        } else if (query.toLowerCase().contains("delete")) {
          count = statement.executeUpdate(query);
          infoArea.append("\n");
          if (count == 0) {
            infoArea.append("No Rows Deleted");
          } else {
            infoArea.append(count + " rows Deleted");
          }
          statement.close();
        } else if (query.toLowerCase().contains("create")) {
          boolean value;
          value = statement.execute(query);
          if (value) {
            infoArea.append("\nCreated");
          } else {
            infoArea.append("\nDoesnot Created");
          }
          statement.close();
        }
      } catch (SQLException ex) {
        infoArea.setText("Error in the query");
      }
    }
  }

  public class AddBatch implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

    }
  }

  public class ClearBatch implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

    }
  }

  public class ExecuteBatch implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

    }
  }

  public class ClearQuery implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      queryArea.setText("");

    }
  }

  public class ClearResult implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      infoArea.setText("");

    }
  }

}
