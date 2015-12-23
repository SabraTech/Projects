package eg.edu.alexu.csd.oop.jdbc;

import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.ResultSetParameters;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * The Class MyResultSet.
 */
public class MyResultSet implements ResultSet {

  /** The data table. */
  private Object[][] dataTable;

  /** The columns names. */
  private String[] columnsNames;

  /** The columns datatypes. */
  private String[] columnsDatatypes;

  /** The table name. */
  private String tableName;

  /** The current statement. */
  private Statement currentStatement;

  /** The is closed. */
  private boolean isClosed;

  /** The current row. */
  private int currentRow;

  /**
   * Instantiates a new my result set.
   *
   * @param data
   *          the data
   * @param statement
   *          the statement
   */
  public MyResultSet(ResultSetParameters data, Statement statement) {
    this.dataTable = data.getSelectedData();
    this.columnsNames = data.getColumnsNames();
    this.columnsDatatypes = data.getColumnsDatatypes();
    this.tableName = data.getTableName();
    this.currentStatement = statement;
    tableName = data.getTableName();
    currentRow = 0;
    isClosed = false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#absolute(int)
   */
  @Override
  // Moves the cursor to the given row number in this ResultSet object
  public boolean absolute(int row) throws SQLException {
    if (isClosed) {
      throw new RuntimeException();
    }
    if (row > dataTable.length || row < 0) {
      throw new IllegalArgumentException();
    }
    currentRow = row;
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#afterLast()
   */
  @Override
  // Moves the cursor to the end of this ResultSet object, just after the last
  // row
  public void afterLast() throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    currentRow = dataTable.length + 1;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#beforeFirst()
   */
  @Override
  // Moves the cursor to the front of this ResultSet object, just before the
  // first row
  public void beforeFirst() throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    currentRow = 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#close()
   */
  @Override
  // Releases this ResultSet object's database and JDBC resources immediately
  // instead of waiting for this to happen when it is automatically closed
  public void close() throws SQLException {

    isClosed = true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#findColumn(java.lang.String)
   */
  @Override
  // Maps the given ResultSet column label to its ResultSet column index
  public int findColumn(String columnLabel) throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    for (int counter = 0; counter < columnsNames.length; counter++) {
      if (columnsNames[counter].equalsIgnoreCase(columnLabel)) {
        return counter + 1;
      }
    }
    throw new SQLException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#first()
   */
  @Override
  // Moves the cursor to the first row in this ResultSet object
  public boolean first() throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    if (dataTable.length == 0) {
      throw new IllegalArgumentException();
    }
    currentRow = 1;
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getInt(int)
   */
  @Override
  // Retrieves the value of the designated column in the current row of this
  // ResultSet object as an INT in the Java programming language
  public int getInt(int columnIndex) throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    if (columnIndex < 1 || columnIndex > dataTable[0].length) {
      throw new SQLException();
    }
    if (!columnsDatatypes[columnIndex - 1].equalsIgnoreCase("int")) {
      throw new SQLException();
    }
    return (int) dataTable[currentRow - 1][columnIndex - 1];
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getInt(java.lang.String)
   */
  @Override
  // Retrieves the value of the designated column in the current row of this
  // ResultSet object as an INT in the Java programming language.
  public int getInt(String columnLabel) throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    if (currentRow == 0 || currentRow == dataTable.length + 1) {
      throw new SQLException();
    }
    for (int counter = 0; counter < dataTable[0].length; counter++) {
      if (columnsNames[counter].equalsIgnoreCase(columnLabel)) {
        if (!columnsDatatypes[counter].equalsIgnoreCase("int")) {
          throw new SQLException();
        }
        return (int) dataTable[currentRow - 1][counter];
      }
    }
    throw new SQLException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getMetaData()
   */
  @Override
  // Retrieves the number, types and properties of this ResultSet object's
  // columns
  public ResultSetMetaData getMetaData() throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    return new MyResultSetMetaData(columnsNames, columnsDatatypes, tableName);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getObject(int)
   */
  @Override
  // Gets the value of the designated column in the current row of this
  // ResultSet
  public Object getObject(int columnIndex) throws SQLException {
    if (isClosed) {
      throw new SQLException();
    }
    if (columnIndex < 1 || columnIndex > dataTable[0].length) {
      throw new SQLException();
    }
    return dataTable[currentRow - 1][columnIndex - 1];
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getStatement()
   */
  @Override
  // Retrieves the Statement object that produced this ResultSet object
  public Statement getStatement() throws SQLException {
    return currentStatement;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getString(int)
   */
  @Override
  // Retrieves the value of the designated column in the current row of this
  // ResultSet
  public String getString(int columnIndex) throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    if (columnIndex < 1 || columnIndex > dataTable[0].length) {
      throw new SQLException();
    }
    if (!columnsDatatypes[columnIndex - 1].equalsIgnoreCase("varChar")) {
      throw new SQLException();
    }
    return (String) dataTable[currentRow - 1][columnIndex - 1];
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getString(java.lang.String)
   */
  @Override
  // Retrieves the value of the designated column in the current row of this
  // ResultSet
  public String getString(String columnLabel) throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    if (currentRow == 0 || currentRow == dataTable.length + 1) {
      throw new SQLException();
    }
    for (int counter = 0; counter < dataTable[0].length; counter++) {
      if (columnsNames[counter].equalsIgnoreCase(columnLabel)) {
        if (!columnsDatatypes[counter].equalsIgnoreCase("varChar")) {
          throw new SQLException();
        }
        return (String) dataTable[currentRow - 1][counter];
      }
    }
    throw new SQLException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#isAfterLast()
   */
  @Override
  // Retrieves whether the cursor is after the last row in this ResultSet
  // object
  public boolean isAfterLast() throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    return currentRow == dataTable.length + 1;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#isBeforeFirst()
   */
  @Override
  // Retrieves whether the cursor is before the first row in this ResultSet
  // object
  public boolean isBeforeFirst() throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    return currentRow == 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#isClosed()
   */
  @Override
  // Retrieves whether this ResultSet object has been closed
  public boolean isClosed() throws SQLException {

    return isClosed;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#isFirst()
   */
  @Override
  // Retrieves whether the cursor is on the first row of this ResultSet object
  public boolean isFirst() throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    return currentRow == 1;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#isLast()
   */
  @Override
  // Retrieves whether the cursor is on the last row of this ResultSet object
  public boolean isLast() throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    return currentRow == dataTable.length;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#last()
   */
  @Override
  // Moves the cursor to the last row in this ResultSet object
  public boolean last() throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    if (dataTable.length == 0) {
      return false;
    }
    currentRow = dataTable.length;
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#next()
   */
  @Override
  // Moves the cursor forward one row from its current position
  public boolean next() throws SQLException {

    if (isClosed) {
      throw new SQLException();
    }
    if (currentRow == dataTable.length) {
      currentRow += 1;
      return false;
    }
    if (currentRow == dataTable.length + 1) {
      return false;
    }
    currentRow += 1;
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#previous()
   */
  @Override
  // Moves the cursor to the previous row in this ResultSet object
  public boolean previous() throws SQLException {
    if (isClosed) {
      throw new SQLException();
    }
    if (currentRow == 1) {
      currentRow -= 1;
      return false;
    }
    if (currentRow == 0) {
      return false;
    }
    currentRow -= 1;
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
   */
  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Wrapper#unwrap(java.lang.Class)
   */
  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#cancelRowUpdates()
   */
  @Override
  public void cancelRowUpdates() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#clearWarnings()
   */
  @Override
  public void clearWarnings() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#deleteRow()
   */
  @Override
  public void deleteRow() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getArray(int)
   */
  @Override
  public Array getArray(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getArray(java.lang.String)
   */
  @Override
  public Array getArray(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getAsciiStream(int)
   */
  @Override
  public InputStream getAsciiStream(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getAsciiStream(java.lang.String)
   */
  @Override
  public InputStream getAsciiStream(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getBigDecimal(int)
   */
  @Override
  public BigDecimal getBigDecimal(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getBigDecimal(java.lang.String)
   */
  @Override
  public BigDecimal getBigDecimal(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getBigDecimal(int, int)
   */
  @Override
  public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getBigDecimal(java.lang.String, int)
   */
  @Override
  public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getBinaryStream(int)
   */
  @Override
  public InputStream getBinaryStream(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getBinaryStream(java.lang.String)
   */
  @Override
  public InputStream getBinaryStream(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getBlob(int)
   */
  @Override
  public Blob getBlob(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getBlob(java.lang.String)
   */
  @Override
  public Blob getBlob(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getBoolean(int)
   */
  @Override
  public boolean getBoolean(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getBoolean(java.lang.String)
   */
  @Override
  public boolean getBoolean(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getByte(int)
   */
  @Override
  public byte getByte(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getByte(java.lang.String)
   */
  @Override
  public byte getByte(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getBytes(int)
   */
  @Override
  public byte[] getBytes(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getBytes(java.lang.String)
   */
  @Override
  public byte[] getBytes(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getCharacterStream(int)
   */
  @Override
  public Reader getCharacterStream(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getCharacterStream(java.lang.String)
   */
  @Override
  public Reader getCharacterStream(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getClob(int)
   */
  @Override
  public Clob getClob(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getClob(java.lang.String)
   */
  @Override
  public Clob getClob(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getConcurrency()
   */
  @Override
  public int getConcurrency() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getCursorName()
   */
  @Override
  public String getCursorName() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getDate(int)
   */
  @Override
  public Date getDate(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getDate(java.lang.String)
   */
  @Override
  public Date getDate(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getDate(int, java.util.Calendar)
   */
  @Override
  public Date getDate(int arg0, Calendar arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getDate(java.lang.String, java.util.Calendar)
   */
  @Override
  public Date getDate(String arg0, Calendar arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getDouble(int)
   */
  @Override
  public double getDouble(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getDouble(java.lang.String)
   */
  @Override
  public double getDouble(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getFetchDirection()
   */
  @Override
  public int getFetchDirection() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getFetchSize()
   */
  @Override
  public int getFetchSize() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getFloat(int)
   */
  @Override
  public float getFloat(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getFloat(java.lang.String)
   */
  @Override
  public float getFloat(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getHoldability()
   */
  @Override
  public int getHoldability() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getLong(int)
   */
  @Override
  public long getLong(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getLong(java.lang.String)
   */
  @Override
  public long getLong(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getNCharacterStream(int)
   */
  @Override
  public Reader getNCharacterStream(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getNCharacterStream(java.lang.String)
   */
  @Override
  public Reader getNCharacterStream(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getNClob(int)
   */
  @Override
  public NClob getNClob(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getNClob(java.lang.String)
   */
  @Override
  public NClob getNClob(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getNString(int)
   */
  @Override
  public String getNString(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getNString(java.lang.String)
   */
  @Override
  public String getNString(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getObject(java.lang.String)
   */
  @Override
  public Object getObject(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getObject(int, java.util.Map)
   */
  @Override
  public Object getObject(int arg0, Map<String, Class<?>> arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getObject(java.lang.String, java.util.Map)
   */
  @Override
  public Object getObject(String arg0, Map<String, Class<?>> arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getObject(int, java.lang.Class)
   */
  @Override
  public <T> T getObject(int arg0, Class<T> arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getObject(java.lang.String, java.lang.Class)
   */
  @Override
  public <T> T getObject(String arg0, Class<T> arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getRef(int)
   */
  @Override
  public Ref getRef(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getRef(java.lang.String)
   */
  @Override
  public Ref getRef(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getRow()
   */
  @Override
  public int getRow() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getRowId(int)
   */
  @Override
  public RowId getRowId(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getRowId(java.lang.String)
   */
  @Override
  public RowId getRowId(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getSQLXML(int)
   */
  @Override
  public SQLXML getSQLXML(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getSQLXML(java.lang.String)
   */
  @Override
  public SQLXML getSQLXML(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getShort(int)
   */
  @Override
  public short getShort(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getShort(java.lang.String)
   */
  @Override
  public short getShort(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getTime(int)
   */
  @Override
  public Time getTime(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getTime(java.lang.String)
   */
  @Override
  public Time getTime(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getTime(int, java.util.Calendar)
   */
  @Override
  public Time getTime(int arg0, Calendar arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getTime(java.lang.String, java.util.Calendar)
   */
  @Override
  public Time getTime(String arg0, Calendar arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getTimestamp(int)
   */
  @Override
  public Timestamp getTimestamp(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getTimestamp(java.lang.String)
   */
  @Override
  public Timestamp getTimestamp(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getTimestamp(int, java.util.Calendar)
   */
  @Override
  public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getTimestamp(java.lang.String, java.util.Calendar)
   */
  @Override
  public Timestamp getTimestamp(String arg0, Calendar arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getType()
   */
  @Override
  public int getType() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getURL(int)
   */
  @Override
  public URL getURL(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getURL(java.lang.String)
   */
  @Override
  public URL getURL(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getUnicodeStream(int)
   */
  @Override
  public InputStream getUnicodeStream(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getUnicodeStream(java.lang.String)
   */
  @Override
  public InputStream getUnicodeStream(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#getWarnings()
   */
  @Override
  public SQLWarning getWarnings() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#insertRow()
   */
  @Override
  public void insertRow() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#moveToCurrentRow()
   */
  @Override
  public void moveToCurrentRow() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#moveToInsertRow()
   */
  @Override
  public void moveToInsertRow() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#refreshRow()
   */
  @Override
  public void refreshRow() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#relative(int)
   */
  @Override
  public boolean relative(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#rowDeleted()
   */
  @Override
  public boolean rowDeleted() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#rowInserted()
   */
  @Override
  public boolean rowInserted() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#rowUpdated()
   */
  @Override
  public boolean rowUpdated() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#setFetchDirection(int)
   */
  @Override
  public void setFetchDirection(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#setFetchSize(int)
   */
  @Override
  public void setFetchSize(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateArray(int, java.sql.Array)
   */
  @Override
  public void updateArray(int arg0, Array arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateArray(java.lang.String, java.sql.Array)
   */
  @Override
  public void updateArray(String arg0, Array arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateAsciiStream(int, java.io.InputStream)
   */
  @Override
  public void updateAsciiStream(int arg0, InputStream arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateAsciiStream(java.lang.String,
   * java.io.InputStream)
   */
  @Override
  public void updateAsciiStream(String arg0, InputStream arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateAsciiStream(int, java.io.InputStream, int)
   */
  @Override
  public void updateAsciiStream(int arg0, InputStream arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateAsciiStream(java.lang.String,
   * java.io.InputStream, int)
   */
  @Override
  public void updateAsciiStream(String arg0, InputStream arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateAsciiStream(int, java.io.InputStream, long)
   */
  @Override
  public void updateAsciiStream(int arg0, InputStream arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateAsciiStream(java.lang.String,
   * java.io.InputStream, long)
   */
  @Override
  public void updateAsciiStream(String arg0, InputStream arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBigDecimal(int, java.math.BigDecimal)
   */
  @Override
  public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBigDecimal(java.lang.String,
   * java.math.BigDecimal)
   */
  @Override
  public void updateBigDecimal(String arg0, BigDecimal arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBinaryStream(int, java.io.InputStream)
   */
  @Override
  public void updateBinaryStream(int arg0, InputStream arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBinaryStream(java.lang.String,
   * java.io.InputStream)
   */
  @Override
  public void updateBinaryStream(String arg0, InputStream arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBinaryStream(int, java.io.InputStream, int)
   */
  @Override
  public void updateBinaryStream(int arg0, InputStream arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBinaryStream(java.lang.String,
   * java.io.InputStream, int)
   */
  @Override
  public void updateBinaryStream(String arg0, InputStream arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBinaryStream(int, java.io.InputStream, long)
   */
  @Override
  public void updateBinaryStream(int arg0, InputStream arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBinaryStream(java.lang.String,
   * java.io.InputStream, long)
   */
  @Override
  public void updateBinaryStream(String arg0, InputStream arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBlob(int, java.sql.Blob)
   */
  @Override
  public void updateBlob(int arg0, Blob arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBlob(java.lang.String, java.sql.Blob)
   */
  @Override
  public void updateBlob(String arg0, Blob arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBlob(int, java.io.InputStream)
   */
  @Override
  public void updateBlob(int arg0, InputStream arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBlob(java.lang.String, java.io.InputStream)
   */
  @Override
  public void updateBlob(String arg0, InputStream arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBlob(int, java.io.InputStream, long)
   */
  @Override
  public void updateBlob(int arg0, InputStream arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBlob(java.lang.String, java.io.InputStream,
   * long)
   */
  @Override
  public void updateBlob(String arg0, InputStream arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBoolean(int, boolean)
   */
  @Override
  public void updateBoolean(int arg0, boolean arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBoolean(java.lang.String, boolean)
   */
  @Override
  public void updateBoolean(String arg0, boolean arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateByte(int, byte)
   */
  @Override
  public void updateByte(int arg0, byte arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateByte(java.lang.String, byte)
   */
  @Override
  public void updateByte(String arg0, byte arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBytes(int, byte[])
   */
  @Override
  public void updateBytes(int arg0, byte[] arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateBytes(java.lang.String, byte[])
   */
  @Override
  public void updateBytes(String arg0, byte[] arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateCharacterStream(int, java.io.Reader)
   */
  @Override
  public void updateCharacterStream(int arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateCharacterStream(java.lang.String,
   * java.io.Reader)
   */
  @Override
  public void updateCharacterStream(String arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateCharacterStream(int, java.io.Reader, int)
   */
  @Override
  public void updateCharacterStream(int arg0, Reader arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateCharacterStream(java.lang.String,
   * java.io.Reader, int)
   */
  @Override
  public void updateCharacterStream(String arg0, Reader arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateCharacterStream(int, java.io.Reader, long)
   */
  @Override
  public void updateCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateCharacterStream(java.lang.String,
   * java.io.Reader, long)
   */
  @Override
  public void updateCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateClob(int, java.sql.Clob)
   */
  @Override
  public void updateClob(int arg0, Clob arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateClob(java.lang.String, java.sql.Clob)
   */
  @Override
  public void updateClob(String arg0, Clob arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateClob(int, java.io.Reader)
   */
  @Override
  public void updateClob(int arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateClob(java.lang.String, java.io.Reader)
   */
  @Override
  public void updateClob(String arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateClob(int, java.io.Reader, long)
   */
  @Override
  public void updateClob(int arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateClob(java.lang.String, java.io.Reader, long)
   */
  @Override
  public void updateClob(String arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateDate(int, java.sql.Date)
   */
  @Override
  public void updateDate(int arg0, Date arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateDate(java.lang.String, java.sql.Date)
   */
  @Override
  public void updateDate(String arg0, Date arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateDouble(int, double)
   */
  @Override
  public void updateDouble(int arg0, double arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateDouble(java.lang.String, double)
   */
  @Override
  public void updateDouble(String arg0, double arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateFloat(int, float)
   */
  @Override
  public void updateFloat(int arg0, float arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateFloat(java.lang.String, float)
   */
  @Override
  public void updateFloat(String arg0, float arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateInt(int, int)
   */
  @Override
  public void updateInt(int arg0, int arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateInt(java.lang.String, int)
   */
  @Override
  public void updateInt(String arg0, int arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateLong(int, long)
   */
  @Override
  public void updateLong(int arg0, long arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateLong(java.lang.String, long)
   */
  @Override
  public void updateLong(String arg0, long arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateNCharacterStream(int, java.io.Reader)
   */
  @Override
  public void updateNCharacterStream(int arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateNCharacterStream(java.lang.String,
   * java.io.Reader)
   */
  @Override
  public void updateNCharacterStream(String arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateNCharacterStream(int, java.io.Reader, long)
   */
  @Override
  public void updateNCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateNCharacterStream(java.lang.String,
   * java.io.Reader, long)
   */
  @Override
  public void updateNCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateNClob(int, java.sql.NClob)
   */
  @Override
  public void updateNClob(int arg0, NClob arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateNClob(java.lang.String, java.sql.NClob)
   */
  @Override
  public void updateNClob(String arg0, NClob arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateNClob(int, java.io.Reader)
   */
  @Override
  public void updateNClob(int arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateNClob(java.lang.String, java.io.Reader)
   */
  @Override
  public void updateNClob(String arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateNClob(int, java.io.Reader, long)
   */
  @Override
  public void updateNClob(int arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateNClob(java.lang.String, java.io.Reader, long)
   */
  @Override
  public void updateNClob(String arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateNString(int, java.lang.String)
   */
  @Override
  public void updateNString(int arg0, String arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateNString(java.lang.String, java.lang.String)
   */
  @Override
  public void updateNString(String arg0, String arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateNull(int)
   */
  @Override
  public void updateNull(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateNull(java.lang.String)
   */
  @Override
  public void updateNull(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateObject(int, java.lang.Object)
   */
  @Override
  public void updateObject(int arg0, Object arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateObject(java.lang.String, java.lang.Object)
   */
  @Override
  public void updateObject(String arg0, Object arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateObject(int, java.lang.Object, int)
   */
  @Override
  public void updateObject(int arg0, Object arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateObject(java.lang.String, java.lang.Object,
   * int)
   */
  @Override
  public void updateObject(String arg0, Object arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateRef(int, java.sql.Ref)
   */
  @Override
  public void updateRef(int arg0, Ref arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateRef(java.lang.String, java.sql.Ref)
   */
  @Override
  public void updateRef(String arg0, Ref arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateRow()
   */
  @Override
  public void updateRow() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateRowId(int, java.sql.RowId)
   */
  @Override
  public void updateRowId(int arg0, RowId arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateRowId(java.lang.String, java.sql.RowId)
   */
  @Override
  public void updateRowId(String arg0, RowId arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateSQLXML(int, java.sql.SQLXML)
   */
  @Override
  public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateSQLXML(java.lang.String, java.sql.SQLXML)
   */
  @Override
  public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateShort(int, short)
   */
  @Override
  public void updateShort(int arg0, short arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateShort(java.lang.String, short)
   */
  @Override
  public void updateShort(String arg0, short arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateString(int, java.lang.String)
   */
  @Override
  public void updateString(int arg0, String arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateString(java.lang.String, java.lang.String)
   */
  @Override
  public void updateString(String arg0, String arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateTime(int, java.sql.Time)
   */
  @Override
  public void updateTime(int arg0, Time arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateTime(java.lang.String, java.sql.Time)
   */
  @Override
  public void updateTime(String arg0, Time arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateTimestamp(int, java.sql.Timestamp)
   */
  @Override
  public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#updateTimestamp(java.lang.String,
   * java.sql.Timestamp)
   */
  @Override
  public void updateTimestamp(String arg0, Timestamp arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSet#wasNull()
   */
  @Override
  public boolean wasNull() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

}
