package eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters;

public class ResultSetParameters {

  private Object[][] selectedData;
  private String[] columnsNames;
  private String[] columnsDatatypes;

  public ResultSetParameters(Object[][] data, String[] colNames, String[] colDatatypes) {
    this.selectedData = data;
    this.columnsNames = colNames;
    this.columnsDatatypes = colDatatypes;
  }

  public Object[][] getSelectedData() {
    return selectedData;
  }

  public String[] getColumnsNames() {
    return columnsNames;
  }

  public String[] getColumnsDatatypes() {
    return columnsDatatypes;
  }

}
