package eg.edu.alexu.csd.oop.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MyResultSetMetaData implements ResultSetMetaData {
	private String[] columnsNames;
	private String[] columnsDataTypes;
	private String tableName;

	public MyResultSetMetaData(String[] columnsNames, String[] dataTypes,
			String tableName) {
		this.columnsNames = columnsNames;
		this.columnsDataTypes = dataTypes;
		this.tableName = tableName;
	}

	@Override
	// Returns the number of columns in this ResultSet object.
	public int getColumnCount() throws SQLException {
		return columnsNames.length;
	}

	@Override
	// Gets the designated column's suggested title for use in printouts and
	// displays
	public String getColumnLabel(int column) throws SQLException {

		if (column < 1 || column > columnsNames.length) {
			throw new RuntimeException("invalid index");
		}
		return columnsNames[column - 1];
	}

	@Override
	// Get the designated column's name
	public String getColumnName(int column) throws SQLException {

		if (column < 1 || column > columnsNames.length) {
			throw new RuntimeException("invalid index");
		}
		return columnsNames[column - 1];
	}

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

	@Override
	// Gets the designated column's table name
	public String getTableName(int column) throws SQLException {

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
