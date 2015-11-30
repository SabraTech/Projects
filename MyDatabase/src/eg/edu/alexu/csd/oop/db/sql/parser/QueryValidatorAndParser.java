package eg.edu.alexu.csd.oop.db.sql.parser;

import eg.edu.alexu.csd.oop.db.sql.parser.parameters.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class QueryValidatorAndParser.
 */
public class QueryValidatorAndParser {

  /** The dummy tokenizer. */
  private StringTokenizer dummyTokenizer;
  /** The second dummy tokenizer. */
  private StringTokenizer dummyTokenizer2;

  /** The compare column to value pattern. */
  private final Pattern compareColumnToValue = Pattern.compile("\\w+\\s*[[<][=][>]]\\s*\\w+");

  /** The word or more between parenthesis pattern. */
  private final Pattern wordOrMoreBetweenParenthesis = Pattern.compile(
      "[(]{1}[\\s*\\w+\\s*,?]*\\s*[)]{1}");

  /** The pairs between parenthesis pattern. */
  private final Pattern pairsBetweenParenthesis = Pattern.compile("[(]{1}[\\w+ \\w+,?]*[)]{1}");

  /** The create data base pattern. */
  private final Pattern createDataBase = Pattern.compile("^CREATE[\\s]*DATABASE[\\s]"
      + "*[\\w]+[\\s]*;?[\\s]*$", Pattern.CASE_INSENSITIVE);// done

  /** The create table pattern. */
  private final Pattern createTable = Pattern.compile(
      "^CREATE[\\s]*TABLE[\\s]*[\\w]+\\s*[(]{1}[\\s]*"
          + "[[\\s]*\\w+[\\s]*\\w+[\\s]*,?[\\s]*]*[)]{1}[\\s]*;?[\\s]*$", Pattern.CASE_INSENSITIVE);

  /** The delete columns with condition pattern. */
  private final Pattern deleteColumnsWithCondition = Pattern.compile(
      "^DELETE[\\s]*FROM[\\s]*\\w+[\\s]*WHERE[\\s]*[[\"']?\\w+[\"']?\\s*[<=>]{1}\\s*[\"']?\\w+"
          + "[\"']?]+;?\\s*$", Pattern.CASE_INSENSITIVE);

  /** The delete all pattern. */
  private final Pattern deleteAll = Pattern.compile("^Delete\\s*[*]?\\s*from\\s*\\w+\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The drop data base pattern. */
  private final Pattern dropDataBase = Pattern.compile("^DROP\\s*DATABASE\\s*\\w+\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The drop table pattern. */
  private final Pattern dropTable = Pattern.compile("^DROP\\s*TABLE\\s*\\w+\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The insert into table columns with values pattern. */
  private final Pattern insertIntoTableColumnsWithValues = Pattern.compile(
      "^Insert\\s*Into\\s*[\\w]+\\s*[(]{1}\\s*[\\w+\\s*,?]*\\s*[)]{1}\\s*Values\\s*[(]{1}\\s*[[\"']?\\w+[\"']?\\s*,?]*\\s*[)]{1}\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The insert into table values pattern. */
  private final Pattern insertIntoTableValues = Pattern.compile(
      "^Insert\\s*into\\s*[\\w]+\\s*VALUES\\s*[(]{1}\\s*[[\"']?\\w+[\"']?\\s*,?]*\\s*[)]{1}\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The select all from table pattern. */
  private final Pattern selectAllFromTable = Pattern.compile(
      "^SELECT\\s*[*]\\s*FROM\\s*[\\w]+\\s*;?\\s*$", Pattern.CASE_INSENSITIVE);

  /** The select columns from table pattern. */
  private final Pattern selectColumnsFromTable = Pattern.compile(
      "^SELECT\\s*[[\\w]+\\s*,?]+\\s*FROM\\s*[\\w]+\\s*;?\\s*$", Pattern.CASE_INSENSITIVE);

  /** The select column with condition pattern. */
  private final Pattern selectColumnWithCondition = Pattern.compile(
      "SELECT\\s*[\\w]+\\s*FROM\\s*[\\w]+\\s*WHERE\\s*[[\"']?\\w+[\"']?\\s*[<=>]{1}\\s*[\"']?\\w+[\"']?\\s*]+\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The select all with condition pattern. */
  private final Pattern selectAllWithCondition = Pattern.compile(
      "^select\\s*[*]\\s*from\\s*\\w+\\s*WHERE\\s*[[\"']?\\w+[\"']?\\s*[<=>]{1}\\s*[\"']?\\w+[\"']?\\s*]+\\s*;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The update pattern. */
  private final Pattern update = Pattern.compile(
      "^UPDATE\\s*[\\w]+\\s*set\\s*[[\\w]+\\s*={1}\\s*[\"']?[\\w]+[\"']?\\s*,?]+\\s*where\\s*[[\"']?\\w+[\"']?\\s*[<=>]{1}\\s*[\"']?\\w+[\"']?\\s*]+;?\\s*$",
      Pattern.CASE_INSENSITIVE);

  /** The update no condition. */
  private final Pattern updateNoCondition = Pattern.compile(
      "^UPDATE\\s*[\\w]+\\s*set\\s*[[\\w]+\\s*[=]{1,1}\\s*[\"']?[\\w]+[\"']?\\s*,?]+\\s*;?\\s*$",
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
  public boolean isSelectAllWithCondition(String query) {
    return selectAllWithCondition.matcher(query).find();
  }

  /**
   * Query is update.
   *
   * @param query
   *          the query
   * @return true, if query matches update pattern
   */
  public boolean queryIsUpdate(String query) {
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
   * Gets the DB name.
   *
   * @param query
   *          the query
   * @return the DB name
   */
  public String getDatabaseName(String query) {
    if (!queryIsCreateDataBase(query)) {
      return null;
    }
    query = query.replace("\"", "").replace("'", "").replace(";", "");
    if (!queryIsCreateDataBase(query)) {
      return null;
    }
    dummyTokenizer = new StringTokenizer(query);
    dummyTokenizer.nextToken();
    dummyTokenizer.nextToken();
    String databaseName = dummyTokenizer.nextToken().replaceAll(";", "").replace(" ", "");
    return databaseName;
  }

  /**
   * Gets the table name.
   *
   * @param query
   *          the query
   * @return an object array, the first entry is the table name, the second
   *         entry is an arrayList of myEntries containing the column name and
   *         it's class
   * @throws SQLException
   *           the SQL exception
   */
  public TableCreationParameters getAddedTableParameters(String query) throws SQLException {
    if (!queryIsCreateTable(query)) {
      return null;
    }
    query = query.replace("\"", "").replace("'", "").replace(";", "").replace("(", " ( ");
    StringTokenizer tokenizer = new StringTokenizer(query);
    tokenizer.nextToken();
    tokenizer.nextToken();
    String tableName = tokenizer.nextToken().replaceAll(";", "").replace(" ", "");
    Matcher dummy = pairsBetweenParenthesis.matcher(query);
    if (dummy.find()) {
      ArrayList<MyEntry<String, Class<?>>> columnNamesAndClasses = new ArrayList<MyEntry<String, Class<?>>>();
      String dummySt = dummy.group();
      tokenizer = new StringTokenizer(dummySt, ",");
      MyEntry<String, Class<?>> dummyEntry;
      while (tokenizer.hasMoreTokens()) {
        dummyTokenizer2 = new StringTokenizer(tokenizer.nextToken().replace("(", "").replace(")",
            ""));
        String dummyName = dummyTokenizer2.nextToken();
        String dummyClass = dummyTokenizer2.nextToken();
        if (dummyClass.equalsIgnoreCase("int")) {
          dummyEntry = new MyEntry<String, Class<?>>(dummyName, Integer.class);
          columnNamesAndClasses.add(dummyEntry);
        } else if (dummyClass.equalsIgnoreCase("varchar")) {
          dummyEntry = new MyEntry<String, Class<?>>(dummyName, String.class);
          columnNamesAndClasses.add(dummyEntry);
        } else {
          throw new SQLException();
        }
      }
      return new TableCreationParameters(tableName, columnNamesAndClasses);
    } else {
      throw new SQLException();
    }
  }

  /**
   * Gets the table name to delete all.
   *
   * @param query
   *          the query
   * @return the table name to delete all
   */
  public String getTableNameToDeleteAll(String query) {
    query = query.replace("\"", "").replace("'", "").replace(";", "").replace("*", "");
    if (!queryIsDeleteAll(query)) {
      return null;
    }
    dummyTokenizer = new StringTokenizer(query);
    dummyTokenizer.nextToken();
    dummyTokenizer.nextToken();
    String tableName = dummyTokenizer.nextToken();
    return tableName;
  }

  /**
   * Gets the delete with condition parameters.
   *
   * @param query
   *          the query
   * @return an object array, the first entry is the table name, the second
   *         entry is the compared column name, the third entry is the comparing
   *         character < , = or >, the fourth is the value to compare to. all
   *         are strings
   * @throws SQLException
   *           the SQL exception
   */
  public ConditionalDeleteParameters getConditionalDeleteParameters(String query)
      throws SQLException {
    if (!queryIsConditionalDelete(query)) {
      return null;
    }
    query = query.replace("\"", "").replace("'", "").replace(";", "");
    Matcher dummy = compareColumnToValue.matcher(query);
    dummyTokenizer = new StringTokenizer(query);
    dummyTokenizer.nextToken();
    dummyTokenizer.nextToken();
    String tableName = dummyTokenizer.nextToken();
    if (dummy.find()) {
      String dummySt = dummy.group();
      dummyTokenizer = new StringTokenizer(dummySt, "[<=>]");
      String columnName = dummyTokenizer.nextToken();
      String value = dummyTokenizer.nextToken();
      Matcher dummyMatcher = Pattern.compile("[<=>]").matcher(dummySt);
      if (dummyMatcher.find()) {
        ;
      }
      String compareChar = dummyMatcher.group();
      return new ConditionalDeleteParameters(tableName, columnName, compareChar, value);

    } else {
      throw new SQLException();
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
    query = query.replace("\"", "").replace("'", "").replace(";", "");
    dummyTokenizer = new StringTokenizer(query);
    dummyTokenizer.nextToken();
    dummyTokenizer.nextToken();
    String dbName = dummyTokenizer.nextToken();
    return dbName.replace(" ", "");
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
    dummyTokenizer = new StringTokenizer(query);
    dummyTokenizer.nextToken();
    dummyTokenizer.nextToken();
    String tableName = dummyTokenizer.nextToken();
    return tableName.replace(" ", "");
  }

  /**
   * Insert into table column values parameters.
   *
   * @param query
   *          the query
   * @return an object[] , the first entry is the table name, the second is an
   *         arrayList containing the columns names, the third is an arrayList
   *         containing the values
   * @throws SQLException
   *           the SQL exception
   */
  public InsertionParameters getInsertIntoTableColumnsWithValuesParameters(String query)
      throws SQLException {
    if (!queryIsInsertIntoTableColumnWithValues(query)) {
      return null;
    }
    query = query.replace("\"", "").replace("'", "").replace(";", "").replace("(", " ( ");
    dummyTokenizer = new StringTokenizer(query);
    dummyTokenizer.nextToken();
    dummyTokenizer.nextToken();
    String tableName = dummyTokenizer.nextToken().replace(" ", "");
    Matcher dummy = wordOrMoreBetweenParenthesis.matcher(query);
    ArrayList<String> columns = new ArrayList<String>();
    ArrayList<Object> values = new ArrayList<Object>();
    if (dummy.find()) {
      String columnsString = dummy.group().replace("(", "").replace(")", "").replace(" ", "");
      dummyTokenizer = new StringTokenizer(columnsString, ",");
      while (dummyTokenizer.hasMoreTokens()) {
        columns.add(dummyTokenizer.nextToken());
      }
    } else {
      throw new SQLException();
    }
    if (dummy.find()) {
      String valuesString = dummy.group().replace("(", "").replace(")", "").replace(" ", "");
      dummyTokenizer = new StringTokenizer(valuesString, ",");
      while (dummyTokenizer.hasMoreTokens()) {
        values.add(dummyTokenizer.nextToken());
      }
    } else {
      throw new SQLException();
    }
    if (columns.size() != values.size()) {
      throw new SQLException();
    } else {
      return new InsertionParameters(tableName, columns, values);
    }
  }

  /**
   * Gets the insert into table values parameters.
   *
   * @param query
   *          the query
   * @return an object [], the first entry is the table name, the second entry
   *         is the values
   * @throws SQLException
   *           the SQL exception
   */
  public InsertionParameters getInsertIntoTableValuesParameters(String query) throws SQLException {
    query = query.replace("\"", "").replace("'", "").replace(";", "");
    if (!queryIsInsertIntoTableValues(query)) {
      return null;
    }
    query = query.replace("\"", "");
    dummyTokenizer = new StringTokenizer(query);
    dummyTokenizer.nextToken();
    dummyTokenizer.nextToken();
    String tableName = dummyTokenizer.nextToken();
    Matcher dummy = wordOrMoreBetweenParenthesis.matcher(query);
    ArrayList<Object> values = new ArrayList<Object>();
    if (dummy.find()) {
      String valuesString = dummy.group().replace("(", "").replace(")", "");
      dummyTokenizer = new StringTokenizer(valuesString.replace(" ", ""), ",");
      while (dummyTokenizer.hasMoreTokens()) {
        values.add(dummyTokenizer.nextToken());
      }
    } else {
      throw new SQLException();
    }
    return new InsertionParameters(tableName, values);
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
    query = query.replace("\"", "").replace("'", "").replace(";", "").replace("(", " ( ");
    dummyTokenizer = new StringTokenizer(query);
    dummyTokenizer.nextToken();
    dummyTokenizer.nextToken();
    dummyTokenizer.nextToken();
    String tableName = dummyTokenizer.nextToken();
    return tableName.replace(" ", "");
  }

  /**
   * Gets the select column with condition parameters.
   *
   * @param query
   *          the query
   * @return an object[], the column name, the table name, the column to be
   *         compared's name, the comparing character, the value, all are
   *         strings
   * 
   * @throws SQLException
   *           the SQL exception
   */
  public SelectionParameters getSelectColumnWithConditionParameters(String query)
      throws SQLException {
    query = query.replace("\"", "").replace("'", "").replace(";", "");
    if (!queryIsSelectWithCondition(query)) {
      return null;
    }
    dummyTokenizer = new StringTokenizer(query);
    dummyTokenizer.nextToken();
    String columnName = dummyTokenizer.nextToken();
    dummyTokenizer.nextToken();
    String tableName = dummyTokenizer.nextToken();
    Matcher dummy = compareColumnToValue.matcher(query);
    String columnToCompareWith, comparingChar, value;
    if (dummy.find()) {
      dummyTokenizer = new StringTokenizer(dummy.group());
      columnToCompareWith = dummyTokenizer.nextToken();
      comparingChar = dummyTokenizer.nextToken();
      value = dummyTokenizer.nextToken();
    } else {
      throw new SQLException();
    }
    return new SelectionParameters(columnName, tableName, columnToCompareWith, comparingChar,
        value);
  }

  /**
   * Gets the select with name parameters.
   *
   * @param query
   *          the query
   * @return an object[], the first entry is an arrayList of strings the columns
   *         to be selected Names, the second is the table name
   */
  public SelectionParameters getSelectWithNameParameters(String query) {
    query = query.replace("\"", "").replace("'", "").replace(";", "");
    if (!queryIsSelectWithNames(query)) {
      return null;
    }
    ArrayList<String> columnsToSelect = new ArrayList<String>();
    dummyTokenizer = new StringTokenizer(query);
    dummyTokenizer.nextToken();
    String dummy;
    while (!(dummy = dummyTokenizer.nextToken()).equalsIgnoreCase("from")) {
      columnsToSelect.add(dummy.replace(",", ""));
    }
    String tableName = dummyTokenizer.nextToken();
    return new SelectionParameters(columnsToSelect, tableName);
  }

  /**
   * Gets the select all with condition parameters.
   *
   * @param query
   *          the query
   * @return an object [], the table name, the column to be compared name, the
   *         comparing character, the value to compare to, all are strings
   */
  public SelectionParameters getSelectAllWithConditionParameters(String query) {
    if (!isSelectAllWithCondition(query)) {
      return null;
    }
    query = query.replace("\"", "").replace("'", "").replace(";", "");
    dummyTokenizer = new StringTokenizer(query);
    dummyTokenizer.nextToken();
    dummyTokenizer.nextToken();
    dummyTokenizer.nextToken();
    String tableName = dummyTokenizer.nextToken();
    Matcher dummyMatcher = Pattern.compile("\\w+\\s*[<=>]\\s*\\w+").matcher(query);
    dummyMatcher.find();
    dummyTokenizer = new StringTokenizer(dummyMatcher.group(), "[<=>]");
    String columnName = dummyTokenizer.nextToken();
    String value = dummyTokenizer.nextToken();
    dummyMatcher = Pattern.compile("[<=>]").matcher(query);
    dummyMatcher.find();
    String compareChar = dummyMatcher.group();
    return new SelectionParameters(tableName, columnName, compareChar, value);
  }

  /**
   * Gets the update parameters.
   *
   * @param query
   *          the query
   * @return an object[], an arrayList of myEntry contatining the required
   *         columns to update and their values, the table name, the column to
   *         be compared's name, the comparing character, the value to compare
   *         to
   * @throws SQLException
   *           the SQL exception
   */
  public UpdateParameters getUpdateParameters(String query) throws SQLException {
    query = query.replace("\"", "").replace("'", "").replace(";", "");
    dummyTokenizer = new StringTokenizer(query);
    dummyTokenizer.nextToken();
    String tableName = dummyTokenizer.nextToken();
    ArrayList<MyEntry<String, String>> required = new ArrayList<MyEntry<String, String>>();
    Matcher dummyMatcher2 = Pattern.compile("^[\\w\\W]+where", Pattern.CASE_INSENSITIVE).matcher(
        query);
    String subs = "";
    if (dummyMatcher2.find()) {
      subs = dummyMatcher2.group();
    }
    Matcher dummy = Pattern.compile("\\w+\\s*[=]\\s*\\w+").matcher(subs);
    while (dummy.find()) {
      dummyTokenizer = new StringTokenizer(dummy.group().replace(" ", ""), "=");
      required.add(new MyEntry<String, String>(dummyTokenizer.nextToken(), dummyTokenizer
          .nextToken()));
    }
    dummy = Pattern.compile("where\\s*\\w+\\s*[[<][=][>]]\\s*\\w+", Pattern.CASE_INSENSITIVE)
        .matcher(query);
    if (dummy.find()) {
      String dummySt, dummySt2 = "";
      dummySt = dummy.group();
      dummyTokenizer = new StringTokenizer(dummySt);
      dummyTokenizer.nextToken();
      while (dummyTokenizer.hasMoreTokens()) {
        dummySt2 += dummyTokenizer.nextToken();
      }
      Matcher dummyMatcher = Pattern.compile("\\w+\\s*[<=>]\\s*\\w+").matcher(dummySt);
      dummyMatcher.find();
      dummyTokenizer = new StringTokenizer(dummyMatcher.group(), "[<=>]");
      String columnName = dummyTokenizer.nextToken();
      String value = dummyTokenizer.nextToken();
      dummyMatcher = Pattern.compile("[<=>]").matcher(dummySt2);
      dummyMatcher.find();
      String compareChar = dummyMatcher.group();
      return new UpdateParameters(required, tableName, columnName, compareChar, value);
    } else {
      throw new SQLException();
    }
  }

  /**
   * Gets the update without condition parameters.
   *
   * @param query
   *          the query
   * @return an object[], the first entry is an arrayList containing the columns
   *         to be updated names and the new values, the second is the table
   *         name
   */
  public UpdateParameters getUpdateWithoutConditionParameters(String query) {
    if (!queryIsUpdateWithoutCondition(query)) {
      return null;
    }
    query = query.replace("\"", "").replace("'", "").replace(";", "");
    dummyTokenizer = new StringTokenizer(query);
    dummyTokenizer.nextToken();
    String tableName = dummyTokenizer.nextToken();
    System.out.println("at update without condition");
    ArrayList<MyEntry<String, String>> required = new ArrayList<MyEntry<String, String>>();
    Matcher dummy = Pattern.compile("\\w+\\s*[=]\\s*\\w+").matcher(query);
    while (dummy.find()) {
      dummyTokenizer = new StringTokenizer(dummy.group().replace(" ", ""), "=");
      required.add(new MyEntry<String, String>(dummyTokenizer.nextToken(), dummyTokenizer
          .nextToken()));
    }
    return new UpdateParameters(required, tableName);
  }

}
