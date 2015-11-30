package eg.edu.alexu.csd.oop.db.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlHandler {

  private String[] fieldsNames;
  private String[] fieldsTypes;
  private Object[][] data;

  public Object[][] readXml(String path, String name) {
    // dataBasePath should have .xml at the end
    String filePath = path + name + ".xml";
    try {
      File fXmlFile = new File(filePath);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      dbFactory.setValidating(true);
      dbFactory.setNamespaceAware(true);
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      dBuilder.setErrorHandler(new ErrorHandler() {

        @Override
        public void error(SAXParseException arg0) throws SAXException {
          // TODO Auto-generated method stub
          System.out.println("ERROR : " + arg0.getMessage());
          throw arg0;
        }

        @Override
        public void fatalError(SAXParseException arg0) throws SAXException {
          // TODO Auto-generated method stub
          System.out.println("FATAL : " + arg0.getCause());
          throw arg0;
        }

        @Override
        public void warning(SAXParseException arg0) throws SAXException {
          // TODO Auto-generated method stub
          System.out.println("WARNING : " + arg0.getMessage());
        }

      });
      Document doc = dBuilder.parse(fXmlFile);
      doc.getDocumentElement().normalize();

      // get the root name which is the table name

      // get how many rows saved in the file
      NodeList nList = doc.getElementsByTagName("Row");
      Node oneRow = nList.item(0);
      NodeList numOfFields = oneRow.getChildNodes();
      fieldsNames = new String[numOfFields.getLength()];
      fieldsTypes = new String[numOfFields.getLength()];
      data = new Object[nList.getLength()][numOfFields.getLength()];

      // loop for each row tag
      for (int temp = 0; temp < nList.getLength(); temp++) {

        // catch the row[i] tag
        Node nNode = nList.item(temp);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

          NodeList columns = nNode.getChildNodes();
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
      // TODO Auto-generated catch block
      throw new RuntimeException();
    }

  }

  public String[] getFieldsNames() {
    return fieldsNames;
  }

  public String[] getFieldsTypes() {
    return fieldsTypes;
  }

  public void writeXml(String[] columnsNames, String[] datatypes, String path, Object[][] table,
      String name) {
    String dtdFile = writeDtd(columnsNames, path, name);
    String filePath = path + name + ".xml";
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

  private String writeDtd(String[] names, String path, String name) {
    String filePath = path + name + ".dtd";
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

  public boolean clearTable(String path, String name) {
    String xmlPath = path + name + ".xml";
    String dtdPath = path + name + ".dtd";
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
