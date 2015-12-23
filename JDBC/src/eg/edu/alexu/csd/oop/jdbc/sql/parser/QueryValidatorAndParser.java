package eg.edu.alexu.csd.oop.jdbc.sql.parser;
//done

import eg.edu.alexu.csd.oop.jdbc.handler.Facade;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.DeleteParameters;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.InsertionParameters;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.SelectionParameters;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.TableCreationParameters;
import eg.edu.alexu.csd.oop.jdbc.sql.parser.parameters.UpdateParameters;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class QueryValidatorAndParser.
 */
public class QueryValidatorAndParser {

  /** The Constant STRUCTURE_QUERY. */
  public static final int STRUCTURE_QUERY = 1;

  /** The Constant UPDATE_QUERY. */
  public static final int UPDATE_QUERY = 2;

  /** The Constant SELECTION_QUERY. */
  public static final int SELECTION_QUERY = 3;

  /** The compare column to value pattern. */
  private final Pattern compareColumnToValue = Pattern.compile(
      "[\"']?(\\w+)[\"']?\\s*([[<][=][>]])\\s*[\"']?(\\w+)[\"']?");

  /** The word or more between parenthesis pattern. */
  private final Pattern wordOrMoreBetweenParenthesis = Pattern.compile(
      "[(]{1}([\\s*[\"']?\\w+[\"']?\\s*,?]*\\s*)[)]{1}");

  /** The create data base pattern. */
  private final Pattern createDataBase = Pattern.compile("^CREATE[\\s]*DATABASE[\\s]"
      + "*([\\w]+)[\\s]*;?[\\s]*$", Pattern.CASE_INSENSITIVE);// done

  /** The create table pattern. */
  private final Pattern createTable = Pattern.compile(
      "^CREATE[\\s]*TABLE[\\s]*([\\w]+)\\s*[(]{1}([\\s]*"
          + "[[\\s]*\\w+[\\s]*\\w+[\\s]*,?[\\s]*]*)[)]{1}[\\s]*;?[\\s]*$",
      Pattern.CASE_INSENSITIVE);

  /** The delete columns with condition pattern. */
  private final Pattern deleteColumnsWithCondition = Pattern.compile(
      "^DELETE[\\s]*FROM[\\s]*(\\w+)[\\s]*WHERE[\\s]*[[\"']?\\w+[\"']?\\s*[<=>]{1}\\s*[\"']?\\w+"
          + "[\"']?]+;?\\s*$", Pattern.CASE_INSENSITIVE);

  /** The delete all pattern. */
  private final Pattern deleteAll = Pattern.compile("^Delete\\s*[*]?\\s*from\\s*(\\w+)\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The drop data base pattern. */
  private final Pattern dropDataBase = Pattern.compile("^DROP\\s*DATABASE\\s*(\\w+)\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The drop table pattern. */
  private final Pattern dropTable = Pattern.compile("^DROP\\s*TABLE\\s*(\\w+)\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The insert into table columns with values pattern. */
  private final Pattern insertIntoTableColumnsWithValues = Pattern.compile(
      "^Insert\\s*Into\\s*([\\w]+)\\s*[(]{1}(\\s*[\\w+\\s*,?]*\\s*)[)]{1}\\s*Values\\s*[(]{1}(\\s*[[\"']?\\w+[\"']?\\s*,?]*\\s*)[)]{1}\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The insert into table values pattern. */
  private final Pattern insertIntoTableValues = Pattern.compile(
      "^Insert\\s*into\\s*(\\w+)\\s*VALUES\\s*[(]{1}(\\s*[[\"']?\\w+[\"']?\\s*,?]*\\s*)[)]{1}\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The select all from table pattern. */
  private final Pattern selectAllFromTable = Pattern.compile(
      "^SELECT\\s*[*]\\s*FROM\\s*([\\w]+)\\s*;?\\s*$", Pattern.CASE_INSENSITIVE);

  /** The select columns from table pattern. */
  private final Pattern selectColumnsFromTable = Pattern.compile(
      "^SELECT\\s*([[\\w]+\\s*,?]+)\\s*FROM\\s*([\\w]+)\\s*;?\\s*$", Pattern.CASE_INSENSITIVE);

  /** The select column with condition pattern. */
  private final Pattern selectColumnWithCondition = Pattern.compile(
      "SELECT\\s*([\\w]+)\\s*FROM\\s*([\\w]+)\\s*WHERE\\s*[\"']?(\\w+)[\"']?\\s*([<=>]{1})\\s*[\"']?(\\w+)[\"']?\\s*\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The select all with condition pattern. */
  private final Pattern selectAllWithCondition = Pattern.compile(
      "^select\\s*[*]\\s*from\\s*(\\w+)\\s*WHERE\\s*[\"']?(\\w+)[\"']?\\s*([<=>]){1}\\s*[\"']?(\\w+)[\"']?\\s*\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The update pattern. */
  private final Pattern update = Pattern.compile(
      "^UPDATE\\s*(\\w+)\\s*set\\s*([[\\w]+\\s*={1}\\s*[\"']?[\\w]+[\"']?\\s*,?]+)\\s*where\\s*[\"']?(\\w+)[\"']?\\s*([<=>]{1})\\s*[\"']?(\\w+)[\"']?\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The update no condition. */
  private final Pattern updateNoCondition = Pattern.compile(
      "^UPDATE\\s*([\\w]+)\\s*set\\s*([[\\w]+\\s*[=]{1}\\s*[\"']?[\\w]+[\"']?\\s*,?]+)\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /**
   * Instantiates a new command carrier.
   */
  public QueryValidatorAndParser() {
  }

  /**
   * Query is create data base.
   *
   * @param query
   *          the query
   * @return true, if query matches create database pattern
   */
  public boolean queryIsCreateDataBase(String query) {
    return createDataBase.matcher(query).find();
  }

  /**
   * Query is create table.
   *
   * @param query
   *          the query
   * @return true, if query matches create table pattern
   */
  public boolean queryIsCreateTable(String query) {
    return createTable.matcher(query).find();
  }

  /**
   * Query is delete all.
   *
   * @param query
   *          the query
   * @return true, if query matches delete all pattern
   */
  public boolean queryIsDeleteAll(String query) {
    return deleteAll.matcher(query).find();
  }

  /**
   * Query is delete with condition.
   *
   * @param query
   *          the query
   * @return true, if query matches delete all with condition pattern
   */
  public boolean queryIsConditionalDelete(String query) {
    return deleteColumnsWithCondition.matcher(query).find();

  }

  /**
   * Query is deletion.
   *
   * @param query
   *          the query
   * @return true, if query matches any deletion pattern
   */
  public boolean queryIsDeletion(String query) {
    return queryIsDeleteAll(query) || queryIsConditionalDelete(query);
  }

  /**
   * Query is drop data base.
   *
   * @param query
   *          the query
   * @return true, if query matches drop database pattern
   */
  public boolean queryIsDropDataBase(String query) {
    return dropDataBase.matcher(query).find();
  }

  /**
   * Query is drop table.
   *
   * @param query
   *          the query
   * @return true, if query matches drop table pattern
   */
  public boolean queryIsDropTable(String query) {
    return dropTable.matcher(query).find();
  }

  /**
   * Query is insert into table column with values.
   *
   * @param query
   *          the query
   * @return true, if query matches insert into table columns with values
   *         pattern
   */
  public boolean queryIsInsertIntoTableColumnWithValues(String query) {
    return insertIntoTableColumnsWithValues.matcher(query).find();
  }

  /**
   * Query is insert into table values.
   *
   * @param query
   *          the query
   * @return true, if query matches insert into table values pattern
   */
  public boolean queryIsInsertIntoTableValues(String query) {
    return insertIntoTableValues.matcher(query).find();
  }

  /**
   * Query is insertion.
   *
   * @param query
   *          the query
   * @return true, if query matches any insertion pattern
   */
  public boolean queryIsInsertion(String query) {
    return queryIsInsertIntoTableColumnWithValues(query) || queryIsInsertIntoTableValues(query);
  }

  /**
   * Query is select all.
   *
   * @param query
   *          the query
   * @return true, if query matches select all pattern
   */
  public boolean queryIsSelectAll(String query) {
    return selectAllFromTable.matcher(query).find();
  }

  /**
   * Query is select with condition.
   *
   * @param query
   *          the query
   * @return true, if query matches select all with condition pattern
   */
  public boolean queryIsSelectWithCondition(String query) {
    return selectColumnWithCondition.matcher(query).find();
  }

  /**
   * Query is select with names.
   *
   * @param query
   *          the query
   * @return true, if query matches select columns with names pattern
   */
  public boolean queryIsSelectWithNames(String query) {
    return selectColumnsFromTable.matcher(query).find();
  }

  /**
   * Checks if is select all with condition.
   *
   * @param query
   *          the query
   * @return true, if query matches select all with condition pattern
   */
  public boolean queryIsSelectAllWithCondition(String query) {
    return selectAllWithCondition.matcher(query).find();
  }

  /**
   * Query is selection.
   *
   * @param query
   *          the query
   * @return true, if query matches any selection pattern
   */
  public boolean queryIsSelection(String query) {
    return queryIsSelectAll(query) || queryIsSelectAllWithCondition(query)
        || queryIsSelectWithCondition(query) || queryIsSelectWithNames(query);
  }

  /**
   * Query is update.
   *
   * @param query
   *          the query
   * @return true, if query matches update pattern
   */
  public boolean queryIsConditionalUpdate(String query) {
    return update.matcher(query).find();

  }

  /**
   * Query is update without condition.
   *
   * @param query
   *          the query
   * @return true, if query matches update without condition pattern
   */
  public boolean queryIsUpdateWithoutCondition(String query) {
    return updateNoCondition.matcher(query).find();
  }

  /**
   * Query is update.
   *
   * @param query
   *          the query
   * @return true, if query matches any update pattern
   */
  public boolean queryIsUpdate(String query) {
    return queryIsConditionalUpdate(query) || queryIsUpdateWithoutCondition(query);
  }

  /**
   * Gets the query type.
   *
   * @param sql
   *          the sql
   * @return an integer representing the query type
   * @throws SQLException
   *           the SQL exception
   */
  public int getQueryType(String sql) throws SQLException {

    if (queryIsCreateDataBase(sql) || queryIsDropDataBase(sql) || queryIsCreateTable(sql)
        || queryIsDropTable(sql)) {
      return STRUCTURE_QUERY;
    } else if (queryIsInsertion(sql) || queryIsDeletion(sql) || queryIsUpdate(sql)) {
      return UPDATE_QUERY;
    } else if (queryIsSelection(sql)) {
      return SELECTION_QUERY;
    }
    throw new SQLException("Invalid query" + sql);
  }

  /**
   * Gets the DB name.
   *
   * @param query
   *          the query
   * @return the DB name
   */
  public String getDatabaseName(String query) {
    Matcher matcher = createDataBase.matcher(query);
    matcher.find();
    String databaseName = matcher.group(1);
    return databaseName;
  }

  /**
   * Gets the table name.
   *
   * @param query
   *          the query
   * @return facade object containing the parameters
   * @throws SQLException
   *           the SQL exception
   */
  public Facade getAddedTableParameters(String query) throws SQLException {
    if (!queryIsCreateTable(query)) {
      return null;
    }
    Matcher dummyMatcher = createTable.matcher(query);
    dummyMatcher.find();
    String tableName = dummyMatcher.group(1);// ;tokenizer.nextToken().replaceAll(";",
                                             // "").replace(" ", "");
    String columnAndDataTypes = dummyMatcher.group(2);
    dummyMatcher = Pattern.compile("(\\w+)").matcher(columnAndDataTypes);
    ArrayList<MyEntry<String, Class<?>>> columnNamesAndClasses = new ArrayList<MyEntry<String, Class<?>>>();
    while (dummyMatcher.find()) {
      String columnName = dummyMatcher.group(1);
      dummyMatcher.find();
      String columnDataType = dummyMatcher.group(1);
      if (columnDataType.equalsIgnoreCase("int")) {
        MyEntry<String, Class<?>> dummyEntry = new MyEntry<String, Class<?>>(columnName,
            Integer.class);
        columnNamesAndClasses.add(dummyEntry);
      } else if (columnDataType.equalsIgnoreCase("varchar")) {
        MyEntry<String, Class<?>> dummyEntry = new MyEntry<String, Class<?>>(columnName,
            String.class);
        columnNamesAndClasses.add(dummyEntry);
      }
    }
    TableCreationParameters table = new TableCreationParameters(tableName, columnNamesAndClasses);
    return new Facade(table);
  }

  /**
   * Gets the table name to delete all.
   *
   * @param query
   *          the query
   * @return the table name to delete all
   */
  public String getTableNameToDeleteAll(String query) {
    if (!queryIsDeleteAll(query)) {
      return null;
    }
    Matcher matcher = deleteAll.matcher(query);
    matcher.find();
    String tableName = matcher.group(1);
    return tableName;
  }

  /**
   * Gets the delete with condition parameters.
   *
   * @param query
   *          the query
   * @return facade object containing the parameters
   * @throws SQLException
   *           the SQL exception
   */
  public Facade getConditionalDeleteParameters(String query) throws SQLException {
    if (!queryIsConditionalDelete(query)) {
      return null;
    }
    try {
      Matcher matcher = deleteColumnsWithCondition.matcher(query);
      matcher.find();
      String tableName = matcher.group(1);
      matcher = compareColumnToValue.matcher(query);
      matcher.find();
      String columnName = matcher.group(1);
      String compareChar = matcher.group(2);
      String value = matcher.group(3);
      DeleteParameters delete = new DeleteParameters(tableName, columnName, compareChar, value);
      return new Facade(delete);
    } catch (Exception e) {
      throw new RuntimeException(e + " query = " + query);
    }

  }

  /**
   * Gets the data base to drop name.
   *
   * @param query
   *          the query
   * @return the data base to drop name
   */
  public String getDataBaseNameToDrop(String query) {
    if (!queryIsDropDataBase(query)) {
      return null;
    }
    Matcher matcher = dropDataBase.matcher(query);
    matcher.find();
    String dbName = matcher.group(1);
    return dbName;
  }

  /**
   * Gets the table to drop name.
   *
   * @param query
   *          the query
   * @return the table to drop name
   */
  public String getTableToDropName(String query) {
    query = query.replace("\"", "").replace("'", "").replace(";", "");
    if (!queryIsDropTable(query)) {
      return null;
    }
    Matcher matcher = dropTable.matcher(query);
    matcher.find();

    // dummyTokenizer = new StringTokenizer(query);
    // dummyTokenizer.nextToken();
    // dummyTokenizer.nextToken();
    String tableName = matcher.group(1);
    return tableName;
  }

  /**
   * Insert into table column values parameters.
   *
   * @param query
   *          the query
   * @return facade object containing the parameters
   * @throws SQLException
   *           the SQL exception
   */
  public Facade getInsertIntoTableColumnsWithValuesParameters(String query) throws SQLException {
    if (!queryIsInsertIntoTableColumnWithValues(query)) {
      return null;
    }
    Matcher matcher = insertIntoTableColumnsWithValues.matcher(query);
    matcher.find();
    String tableName = matcher.group(1);
    Matcher matcher2 = wordOrMoreBetweenParenthesis.matcher(query);
    ArrayList<String> columns = new ArrayList<String>();
    ArrayList<Object> values = new ArrayList<Object>();
    if (matcher2.find()) {
      String columnsString = matcher2.group();
      Matcher matcher3 = Pattern.compile("(\\w+)").matcher(columnsString);
      while (matcher3.find()) {
        columns.add(matcher3.group(1));
      }
    } else {
      throw new SQLException();
    }
    if (matcher2.find()) {
      String valuesString = matcher2.group();
      Matcher matcher3 = Pattern.compile("(\\w+)").matcher(valuesString);
      while (matcher3.find()) {
        values.add(matcher3.group(1));
      }
    } else {
      throw new SQLException(query);
    }
    if (columns.size() != values.size()) {
      throw new SQLException();
    } else {
      InsertionParameters insert = new InsertionParameters(tableName, columns, values);
      return new Facade(insert);
    }
  }

  /**
   * Gets the insert into table values parameters.
   *
   * @param query
   *          the query
   * @return facade object containing the parameters
   * @throws SQLException
   *           the SQL exception
   */
  public Facade getInsertIntoTableValuesParameters(String query) throws SQLException {
    if (!queryIsInsertIntoTableValues(query)) {
      return null;
    }
    Matcher matcher = insertIntoTableValues.matcher(query);
    matcher.find();
    String tableName = matcher.group(1);
    ArrayList<Object> values = new ArrayList<Object>();
    String valuesString = matcher.group(2);
    matcher = Pattern.compile("(\\w+)").matcher(valuesString);
    while (matcher.find()) {
      values.add(matcher.group(1));
    }
    InsertionParameters insert = new InsertionParameters(tableName, values);
    return new Facade(insert);
  }

  /**
   * Gets the select all from table parameters.
   *
   * @param query
   *          the query
   * @return the table name
   */
  public String getSelectAllFromTableParameters(String query) {
    if (!queryIsSelectAll(query)) {
      return null;
    }
    Matcher matcher = selectAllFromTable.matcher(query);
    matcher.find();
    String tableName = matcher.group(1);
    return tableName;
  }

  /**
   * Gets the select column with condition parameters.
   *
   * @param query
   *          the query
   * @return facade object containing the parameters
   * @throws SQLException
   *           the SQL exception
   */
  public Facade getSelectColumnWithConditionParameters(String query) throws SQLException {
    if (!queryIsSelectWithCondition(query)) {
      return null;
    }
    Matcher matcher = selectColumnWithCondition.matcher(query);
    matcher.find();
    String columnName = matcher.group(1);
    String tableName = matcher.group(2);
    String columnToCompareWith, comparingChar, value;
    columnToCompareWith = matcher.group(3);
    comparingChar = matcher.group(4);
    value = matcher.group(5);
    SelectionParameters select = new SelectionParameters(columnName, tableName, columnToCompareWith,
        comparingChar, value);
    return new Facade(select);

  }

  /**
   * Gets the select with name parameters.
   *
   * @param query
   *          the query
   * @return facade object containing the parameters
   */
  public Facade getSelectWithNameParameters(String query) {
    if (!queryIsSelectWithNames(query)) {
      return null;
    }
    ArrayList<String> columnsToSelect = new ArrayList<String>();
    Matcher matcher = selectColumnsFromTable.matcher(query);
    matcher.find();
    Matcher matcher2 = Pattern.compile("(\\w+)").matcher(matcher.group(1));
    while (matcher2.find()) {
      columnsToSelect.add(matcher2.group(1));
    }
    String tableName = matcher.group(2);
    SelectionParameters select = new SelectionParameters(columnsToSelect, tableName);
    return new Facade(select);
  }

  /**
   * Gets the select all with condition parameters.
   *
   * @param query
   *          the query
   * @return facade object containing the parameters
   */
  public Facade getSelectAllWithConditionParameters(String query) {
    if (!queryIsSelectAllWithCondition(query)) {
      return null;
    }
    Matcher matcher = selectAllWithCondition.matcher(query);
    matcher.find();
    String tableName = matcher.group(1);
    String columnName = matcher.group(2);
    String compareChar = matcher.group(3);
    String value = matcher.group(4);

    SelectionParameters select = new SelectionParameters(tableName, columnName, compareChar, value);
    return (new Facade(select));
  }

  /**
   * Gets the update parameters.
   *
   * @param query
   *          the query
   * @return facade object containing the parameters
   * @throws SQLException
   *           the SQL exception
   */
  public Facade getUpdateParameters(String query) throws SQLException {
    Matcher matcher = update.matcher(query);
    matcher.find();
    String tableName = matcher.group(1);
    ArrayList<MyEntry<String, String>> required = new ArrayList<MyEntry<String, String>>();
    Matcher dummy = Pattern.compile("(\\w+)\\s*[=]\\s*(\\w+)").matcher(matcher.group(2));
    while (dummy.find()) {
      String column = dummy.group(1);
      String value = dummy.group(2);
      required.add(new MyEntry<String, String>(column, value));
    }
    String columnName = matcher.group(3);
    String compareChar = matcher.group(4);
    String value = matcher.group(5);
    UpdateParameters update = new UpdateParameters(required, tableName, columnName, compareChar,
        value);
    return new Facade(update);
  }

  /**
   * Gets the update without condition parameters.
   *
   * @param query
   *          the query
   * @return facade object containing the parameters
   */
  public Facade getUpdateWithoutConditionParameters(String query) {
    if (!queryIsUpdateWithoutCondition(query)) {
      return null;
    }
    Matcher matcher = updateNoCondition.matcher(query);
    matcher.find();
    String tableName = matcher.group(1);
    ArrayList<MyEntry<String, String>> required = new ArrayList<MyEntry<String, String>>();
    Matcher dummy = Pattern.compile("(\\w+)\\s*[=]\\s*(\\w+)").matcher(matcher.group(2));
    while (dummy.find()) {
      String column = dummy.group(1);
      String value = dummy.group(2);
      required.add(new MyEntry<String, String>(column, value));
    }
    UpdateParameters update = new UpdateParameters(required, tableName);
    return new Facade(update);
  }

}
