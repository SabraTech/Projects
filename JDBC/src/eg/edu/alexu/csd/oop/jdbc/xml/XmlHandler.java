package eg.edu.alexu.csd.oop.jdbc.xml;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * The Class XmlHandler.
 */
public class XmlHandler {
  /** The fields names. */
  private String[] fieldsNames;

  /** The fields types. */
  private String[] fieldsTypes;

  /** The table name. */
  private String tableName;

  /** The data. */
  private Object[][] data;

  /**
   * Read xml.
   *
   * @param path
   *          the path
   * @param name
   *          the name
   * @return the object[][]
   */
  public Object[][] readXml(String path, String name) {
    // dataBasePath should have .xml at the end
    String filePath = path + File.separatorChar + name + ".xml";
    try {
      File xmlFile = new File(filePath);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      dbFactory.setValidating(true);
      dbFactory.setNamespaceAware(true);
      DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
      documentBuilder.setErrorHandler(new ErrorHandler() {

        @Override
        public void error(SAXParseException arg0) throws SAXException {
          System.out.println("ERROR : " + arg0.getMessage());
          throw arg0;
        }

        @Override
        public void fatalError(SAXParseException arg0) throws SAXException {

          System.out.println("FATAL : " + arg0.getCause());
          throw arg0;
        }

        @Override
        public void warning(SAXParseException arg0) throws SAXException {
          System.out.println("WARNING : " + arg0.getMessage());
        }

      });
      Document doc = documentBuilder.parse(xmlFile);
      doc.getDocumentElement().normalize();

      // get the root name which is the table name
      tableName = doc.getDocumentElement().getNodeName();

      // get how many rows saved in the file
      NodeList nodeList = doc.getElementsByTagName("Row");
      Node oneRow = nodeList.item(0);
      NodeList numOfFields = oneRow.getChildNodes();
      fieldsNames = new String[numOfFields.getLength()];
      fieldsTypes = new String[numOfFields.getLength()];
      data = new Object[nodeList.getLength()][numOfFields.getLength()];

      // loop for each row tag
      for (int temp = 0; temp < nodeList.getLength(); temp++) {

        // catch the row[i] tag
        Node dummyNode = nodeList.item(temp);

        if (dummyNode.getNodeType() == Node.ELEMENT_NODE) {

          NodeList columns = dummyNode.getChildNodes();
          for (int i = 0; i < columns.getLength(); i++) {
            Node childNode = columns.item(i);
            Element eElement = (Element) childNode;
            fieldsNames[i] = eElement.getTagName();
            String value = childNode.getTextContent();
            String datatype = eElement.getAttribute("datatype");
            fieldsTypes[i] = datatype;
            if (value.equals("null")) {
              data[temp][i] = null;
            } else {
              if (datatype.equals("int")) {
                data[temp][i] = Integer.parseInt(value);
              } else if (datatype.equals("varchar")) {
                data[temp][i] = value;
              }
            }

          }

        }

      }
      return data;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  /**
   * Gets the fields names.
   *
   * @return the fields names
   */
  public String[] getFieldsNames() {
    return fieldsNames;
  }

  /**
   * Gets the fields types.
   *
   * @return the fields types
   */
  public String[] getFieldsTypes() {
    return fieldsTypes;
  }

  /**
   * Gets the table name.
   *
   * @return the table name
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * Write xml.
   *
   * @param columnsNames
   *          the columns names
   * @param datatypes
   *          the datatypes
   * @param path
   *          the path
   * @param table
   *          the table
   * @param name
   *          the name
   */
  public void writeXml(String[] columnsNames, String[] datatypes, String path, Object[][] table,
      String name) {
    String dtdFile = writeDtd(columnsNames, path, name);
    String filePath = path + File.separatorChar + name + ".xml";
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

      // root element
      Document doc = docBuilder.newDocument();
      Element tablename = doc.createElement(name);
      doc.appendChild(tablename);

      for (int num = 0; num < table.length; num++) {

        // row element
        Element row = doc.createElement("Row");
        tablename.appendChild(row);

        for (int i = 0; i < columnsNames.length; i++) {
          Element field = doc.createElement(columnsNames[i]);
          if (table[num][i] == null) {
            field.appendChild(doc.createTextNode("null"));
          } else {
            field.appendChild(doc.createTextNode(String.valueOf(table[num][i])));
          }
          row.appendChild(field);
          Attr attr = doc.createAttribute("datatype");
          attr.setValue(datatypes[i]);
          field.setAttributeNode(attr);
        }
      }
      // write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtdFile);
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(filePath));
      transformer.transform(source, result);
    } catch (ParserConfigurationException pce) {
      throw new RuntimeException();
    } catch (TransformerException tfe) {
      throw new RuntimeException();
    }
  }

  /**
   * Write dtd.
   *
   * @param names
   *          the names
   * @param path
   *          the path
   * @param name
   *          the name
   * @return the string
   */
  private String writeDtd(String[] names, String path, String name) {
    String filePath = path + File.separatorChar + name + ".dtd";
    try {
      Formatter format = new Formatter(filePath);
      format.format("<!ELEMENT %s (Row*)>%n", name);
      format.format("<!ELEMENT Row (");
      for (int i = 0; i < names.length - 1; i++) {
        format.format("%s, ", names[i]);
      }
      format.format("%s)>%n", names[names.length - 1]);
      for (int i = 0; i < names.length; i++) {
        format.format("<!ELEMENT %s (#PCDATA)>%n", names[i]);
        format.format("<!ATTLIST %s datatype (int|varchar) #REQUIRED>%n", names[i]);
      }
      format.close();
      return name + ".dtd";
    } catch (FileNotFoundException e) {
      throw new RuntimeException("can't create file");
    }
  }

  /**
   * Clear table.
   *
   * @param path
   *          the path
   * @param name
   *          the name
   * @return true, if successful
   */
  public boolean clearTable(String path, String name) {
    String xmlPath = path + File.separatorChar + name + ".xml";
    String dtdPath = path + File.separatorChar + name + ".dtd";
    File xmlFile = new File(xmlPath);
    File dtdFile = new File(dtdPath);
    if (xmlFile.exists() && dtdFile.exists()) {
      xmlFile.delete();
      dtdFile.delete();
      return true;
    } else {
      return false;
    }

  }

}
