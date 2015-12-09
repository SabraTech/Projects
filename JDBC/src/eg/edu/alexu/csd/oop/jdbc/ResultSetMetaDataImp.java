package eg.edu.alexu.csd.oop.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetMetaDataImp implements ResultSetMetaData {
	  private String[] columnsNames;
	  private String[] columnsDataTypes;
	  private String tableName;

	  public ResultSetMetaDataImp(String[] columnsNames, String[] dataTypes, String tableName) {
	    // TODO Auto-generated constructor stub
	    this.columnsNames = columnsNames;
	    this.columnsDataTypes = dataTypes;
	    this.tableName = tableName;
	  }

	  public int getColumnCount() throws SQLException {
	    // TODO Auto-generated method stub
	    return columnsNames.length;
	  }

	  @Override
	  // Gets the designated column's suggested title for use in printouts and
	  // displays
	  public String getColumnLabel(int column) throws SQLException {
	    // TODO Auto-generated method stub
	    if (column < 1 || column > columnsNames.length) {
	      throw new RuntimeException("invalid index");
	    }
	    return columnsNames[column - 1];
	  }

	  @Override
	  // Get the designated column's name
	  public String getColumnName(int column) throws SQLException {
	    // TODO Auto-generated method stub
	    if (column < 1 || column > columnsNames.length) {
	      throw new RuntimeException("invalid index");
	    }
	    return columnsNames[column - 1];
	  }

	  @Override
	  // Retrieves the designated column's SQL type
	  public int getColumnType(int column) throws SQLException {
	    // TODO Auto-generated method stub
	    if (column < 1 || column > columnsNames.length) {
	      throw new RuntimeException("invalid index");
	    }
	    if (columnsDataTypes[column].equalsIgnoreCase("varChar")) {
	      return java.sql.Types.VARCHAR;
	    } else {
	      return java.sql.Types.INTEGER;
	    }
	  }

	  @Override
	  // Gets the designated column's table name
	  public String getTableName(int column) throws SQLException {
	    // TODO Auto-generated method stub
	    return tableName;
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
	  public String getCatalogName(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public String getColumnClassName(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public int getColumnDisplaySize(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public String getColumnTypeName(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public int getPrecision(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public int getScale(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public String getSchemaName(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public boolean isAutoIncrement(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public boolean isCaseSensitive(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public boolean isCurrency(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public boolean isDefinitelyWritable(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public int isNullable(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public boolean isReadOnly(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public boolean isSearchable(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public boolean isSigned(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	  @Override
	  public boolean isWritable(int arg0) throws SQLException {
	    throw new java.lang.UnsupportedOperationException();
	  }

	}
