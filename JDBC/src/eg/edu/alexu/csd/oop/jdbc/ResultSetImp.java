package eg.edu.alexu.csd.oop.jdbc;

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

import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.ResultSetParameters;

public class ResultSetImp implements ResultSet {
  private Object[][] dataTable;
  private String[] columnsNames;
  private String[] columnsDatatypes;
  private String tableName;
  private Statement currentStatement;
  private boolean isClosed;
  private int currentRow;

  public ResultSetImp(ResultSetParameters data, Statement stat) {
    this.dataTable = data.getSelectedData();
    this.columnsNames = data.getColumnsNames();
    this.columnsDatatypes = data.getColumnsDatatypes();
    this.tableName = data.getTableName();
    this.currentStatement = stat;
    tableName = data.getTableName();
    currentRow = 0;
    isClosed = false;
  }

  @Override
  // Moves the cursor to the given row number in this ResultSet object
  public boolean absolute(int row) throws SQLException {
    // TODO Auto-generated method stub
    if (isClosed) {
      throw new RuntimeException();
    }
    if (row > dataTable.length || row < 0) {
      throw new IllegalArgumentException();
    }
    currentRow = row;
    return true;
  }

  @Override
  // Moves the cursor to the end of this ResultSet object, just after the last
  // row
  public void afterLast() throws SQLException {
    // TODO Auto-generated method stub
    if (isClosed) {
      throw new SQLException();
    }
    currentRow = dataTable.length + 1;
  }

  @Override
  // Moves the cursor to the front of this ResultSet object, just before the
  // first row
  public void beforeFirst() throws SQLException {
    // TODO Auto-generated method stub
    if (isClosed) {
      throw new SQLException();
    }
    currentRow = 0;
  }

  @Override
  // Releases this ResultSet object's database and JDBC resources immediately
  // instead of waiting for this to happen when it is automatically closed
  public void close() throws SQLException {
    // TODO Auto-generated method stub
    isClosed = true;
  }

  @Override
  // Maps the given ResultSet column label to its ResultSet column index
  public int findColumn(String columnLabel) throws SQLException {
    // TODO Auto-generated method stub
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

  @Override
  // Moves the cursor to the first row in this ResultSet object
  public boolean first() throws SQLException {
    // TODO Auto-generated method stub
    if (isClosed) {
      throw new SQLException();
    }
    if (dataTable.length == 0) {
      throw new IllegalArgumentException();
    }
    currentRow = 1;
    return true;
  }

  @Override
  // Retrieves the value of the designated column in the current row of this
  // ResultSet object as an INT in the Java programming language
  public int getInt(int columnIndex) throws SQLException {
    // TODO Auto-generated method stub
    if (isClosed) {
      throw new SQLException();
    }
    if (columnIndex < 1 || columnIndex > dataTable.length) {
      throw new SQLException();
    }
    if (!columnsDatatypes[columnIndex - 1].equals("int")) {
      throw new SQLException();
    }
    return (int) dataTable[currentRow - 1][columnIndex - 1];
  }

  @Override
  // Retrieves the value of the designated column in the current row of this
  // ResultSet object as an INT in the Java programming language.
  public int getInt(String columnLabel) throws SQLException {
    // TODO Auto-generated method stub
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

  @Override
  // Retrieves the number, types and properties of this ResultSet object's
  // columns
  public ResultSetMetaData getMetaData() throws SQLException {
    // TODO Auto-generated method stub
    if (isClosed) {
      throw new SQLException();
    }
    return new ResultSetMetaDataImp(columnsNames, columnsDatatypes, tableName);
  }

  @Override
  // Gets the value of the designated column in the current row of this
  // ResultSet
  public Object getObject(int columnIndex) throws SQLException {
    // TODO Auto-generated method stub
    if (isClosed) {
      throw new SQLException();
    }
    if (columnIndex < 1 || columnIndex > dataTable.length) {
      throw new SQLException();
    }
    return dataTable[currentRow - 1][columnIndex - 1];
  }

  @Override
  // Retrieves the Statement object that produced this ResultSet object
  public Statement getStatement() throws SQLException {
    // TODO Auto-generated method stub'
    return currentStatement;
  }

  @Override
  // Retrieves the value of the designated column in the current row of this
  // ResultSet
  public String getString(int columnIndex) throws SQLException {
    // TODO Auto-generated method stub
    if (isClosed) {
      throw new SQLException();
    }
    if (columnIndex < 1 || columnIndex > dataTable.length) {
      throw new SQLException();
    }
    if (!columnsDatatypes[columnIndex - 1].equals("varChar")) {
      throw new SQLException();
    }
    return (String) dataTable[currentRow - 1][columnIndex - 1];
  }

  @Override
  // Retrieves the value of the designated column in the current row of this
  // ResultSet
  public String getString(String columnLabel) throws SQLException {
    // TODO Auto-generated method stub
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

  @Override
  // Retrieves whether the cursor is after the last row in this ResultSet
  // object
  public boolean isAfterLast() throws SQLException {
    // TODO Auto-generated method stub
    if (isClosed) {
      throw new SQLException();
    }
    return currentRow == dataTable.length + 1;
  }

  @Override
  // Retrieves whether the cursor is before the first row in this ResultSet
  // object
  public boolean isBeforeFirst() throws SQLException {
    // TODO Auto-generated method stub
    if (isClosed) {
      throw new SQLException();
    }
    return currentRow == 0;
  }

  @Override
  // Retrieves whether this ResultSet object has been closed
  public boolean isClosed() throws SQLException {
    // TODO Auto-generated method stub
    return isClosed;
  }

  @Override
  // Retrieves whether the cursor is on the first row of this ResultSet object
  public boolean isFirst() throws SQLException {
    // TODO Auto-generated method stub
    if (isClosed) {
      throw new SQLException();
    }
    return currentRow == 1;
  }

  @Override
  // Retrieves whether the cursor is on the last row of this ResultSet object
  public boolean isLast() throws SQLException {
    // TODO Auto-generated method stub
    if (isClosed) {
      throw new SQLException();
    }
    return currentRow == dataTable.length;
  }

  @Override
  // Moves the cursor to the last row in this ResultSet object
  public boolean last() throws SQLException {
    // TODO Auto-generated method stub
    if (isClosed) {
      throw new SQLException();
    }
    if (dataTable.length == 0) {
      return false;
    }
    currentRow = dataTable.length;
    return true;
  }

  @Override
  // Moves the cursor froward one row from its current position
  public boolean next() throws SQLException {
    // TODO Auto-generated method stub
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

  @Override
  // Moves the cursor to the previous row in this ResultSet object
  public boolean previous() throws SQLException {
    // TODO Auto-generated method stub
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

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void cancelRowUpdates() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void clearWarnings() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void deleteRow() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Array getArray(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Array getArray(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public InputStream getAsciiStream(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public InputStream getAsciiStream(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public BigDecimal getBigDecimal(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public BigDecimal getBigDecimal(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public InputStream getBinaryStream(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public InputStream getBinaryStream(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Blob getBlob(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Blob getBlob(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean getBoolean(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean getBoolean(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public byte getByte(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public byte getByte(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public byte[] getBytes(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public byte[] getBytes(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Reader getCharacterStream(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Reader getCharacterStream(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Clob getClob(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Clob getClob(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getConcurrency() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public String getCursorName() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Date getDate(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Date getDate(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Date getDate(int arg0, Calendar arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Date getDate(String arg0, Calendar arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public double getDouble(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public double getDouble(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getFetchDirection() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getFetchSize() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public float getFloat(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public float getFloat(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getHoldability() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public long getLong(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public long getLong(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Reader getNCharacterStream(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Reader getNCharacterStream(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public NClob getNClob(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public NClob getNClob(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public String getNString(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public String getNString(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Object getObject(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Object getObject(int arg0, Map<String, Class<?>> arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Object getObject(String arg0, Map<String, Class<?>> arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public <T> T getObject(int arg0, Class<T> arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public <T> T getObject(String arg0, Class<T> arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Ref getRef(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Ref getRef(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getRow() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public RowId getRowId(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public RowId getRowId(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public SQLXML getSQLXML(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public SQLXML getSQLXML(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public short getShort(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public short getShort(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Time getTime(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Time getTime(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Time getTime(int arg0, Calendar arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Time getTime(String arg0, Calendar arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Timestamp getTimestamp(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Timestamp getTimestamp(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public Timestamp getTimestamp(String arg0, Calendar arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public int getType() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public URL getURL(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public URL getURL(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public InputStream getUnicodeStream(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public InputStream getUnicodeStream(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void insertRow() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void moveToCurrentRow() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void moveToInsertRow() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void refreshRow() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean relative(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean rowDeleted() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean rowInserted() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean rowUpdated() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setFetchDirection(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void setFetchSize(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateArray(int arg0, Array arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateArray(String arg0, Array arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateAsciiStream(int arg0, InputStream arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateAsciiStream(String arg0, InputStream arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateAsciiStream(int arg0, InputStream arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateAsciiStream(String arg0, InputStream arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateAsciiStream(int arg0, InputStream arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateAsciiStream(String arg0, InputStream arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBigDecimal(String arg0, BigDecimal arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBinaryStream(int arg0, InputStream arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBinaryStream(String arg0, InputStream arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBinaryStream(int arg0, InputStream arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBinaryStream(String arg0, InputStream arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBinaryStream(int arg0, InputStream arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBinaryStream(String arg0, InputStream arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBlob(int arg0, Blob arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBlob(String arg0, Blob arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBlob(int arg0, InputStream arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBlob(String arg0, InputStream arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBlob(int arg0, InputStream arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBlob(String arg0, InputStream arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBoolean(int arg0, boolean arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBoolean(String arg0, boolean arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateByte(int arg0, byte arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateByte(String arg0, byte arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBytes(int arg0, byte[] arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateBytes(String arg0, byte[] arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateCharacterStream(int arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateCharacterStream(String arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateCharacterStream(int arg0, Reader arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateCharacterStream(String arg0, Reader arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateClob(int arg0, Clob arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateClob(String arg0, Clob arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateClob(int arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateClob(String arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateClob(int arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateClob(String arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateDate(int arg0, Date arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateDate(String arg0, Date arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateDouble(int arg0, double arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateDouble(String arg0, double arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateFloat(int arg0, float arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateFloat(String arg0, float arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateInt(int arg0, int arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateInt(String arg0, int arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateLong(int arg0, long arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateLong(String arg0, long arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateNCharacterStream(int arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateNCharacterStream(String arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateNCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateNCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateNClob(int arg0, NClob arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateNClob(String arg0, NClob arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateNClob(int arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateNClob(String arg0, Reader arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateNClob(int arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateNClob(String arg0, Reader arg1, long arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateNString(int arg0, String arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateNString(String arg0, String arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateNull(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateNull(String arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateObject(int arg0, Object arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateObject(String arg0, Object arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateObject(int arg0, Object arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateObject(String arg0, Object arg1, int arg2) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateRef(int arg0, Ref arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateRef(String arg0, Ref arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateRow() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateRowId(int arg0, RowId arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateRowId(String arg0, RowId arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateShort(int arg0, short arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateShort(String arg0, short arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateString(int arg0, String arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateString(String arg0, String arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateTime(int arg0, Time arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateTime(String arg0, Time arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public void updateTimestamp(String arg0, Timestamp arg1) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  public boolean wasNull() throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

}
