package eg.edu.alexu.csd.oop.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * The Class MyResultSetMetaData.
 */
public class MyResultSetMetaData implements ResultSetMetaData {

  /** The columns names. */
  private String[] columnsNames;

  /** The columns data types. */
  private String[] columnsDataTypes;

  /** The table name. */
  private String tableName;

  /**
   * Instantiates a new my result set meta data.
   *
   * @param columnsNames
   *          the columns names
   * @param dataTypes
   *          the data types
   * @param tableName
   *          the table name
   */
  public MyResultSetMetaData(String[] columnsNames, String[] dataTypes, String tableName) {
    this.columnsNames = columnsNames;
    this.columnsDataTypes = dataTypes;
    this.tableName = tableName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#getColumnCount()
   */
  @Override
  // Returns the number of columns in this ResultSet object.
  public int getColumnCount() throws SQLException {
    return columnsNames.length;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#getColumnLabel(int)
   */
  @Override
  // Gets the designated column's suggested title for use in printouts and
  // displays
  public String getColumnLabel(int column) throws SQLException {

    if (column < 1 || column > columnsNames.length) {
      throw new RuntimeException("invalid index");
    }
    return columnsNames[column - 1];
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#getColumnName(int)
   */
  @Override
  // Get the designated column's name
  public String getColumnName(int column) throws SQLException {

    if (column < 1 || column > columnsNames.length) {
      throw new RuntimeException("invalid index");
    }
    return columnsNames[column - 1];
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#getColumnType(int)
   */
  @Override
  // Retrieves the designated column's SQL type
  public int getColumnType(int column) throws SQLException {

    if (column < 1 || column > columnsNames.length) {
      throw new RuntimeException("invalid index");
    }
    if (columnsDataTypes[column - 1].equalsIgnoreCase("varChar")) {
      return java.sql.Types.VARCHAR;
    } else {
      return java.sql.Types.INTEGER;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#getTableName(int)
   */
  @Override
  // Gets the designated column's table name
  public String getTableName(int column) throws SQLException {

    return tableName;
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
   * @see java.sql.ResultSetMetaData#getCatalogName(int)
   */
  @Override
  public String getCatalogName(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#getColumnClassName(int)
   */
  @Override
  public String getColumnClassName(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#getColumnDisplaySize(int)
   */
  @Override
  public int getColumnDisplaySize(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#getColumnTypeName(int)
   */
  @Override
  public String getColumnTypeName(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#getPrecision(int)
   */
  @Override
  public int getPrecision(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#getScale(int)
   */
  @Override
  public int getScale(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#getSchemaName(int)
   */
  @Override
  public String getSchemaName(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#isAutoIncrement(int)
   */
  @Override
  public boolean isAutoIncrement(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#isCaseSensitive(int)
   */
  @Override
  public boolean isCaseSensitive(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#isCurrency(int)
   */
  @Override
  public boolean isCurrency(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#isDefinitelyWritable(int)
   */
  @Override
  public boolean isDefinitelyWritable(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#isNullable(int)
   */
  @Override
  public int isNullable(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#isReadOnly(int)
   */
  @Override
  public boolean isReadOnly(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#isSearchable(int)
   */
  @Override
  public boolean isSearchable(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#isSigned(int)
   */
  @Override
  public boolean isSigned(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.ResultSetMetaData#isWritable(int)
   */
  @Override
  public boolean isWritable(int arg0) throws SQLException {
    throw new java.lang.UnsupportedOperationException();
  }

}
