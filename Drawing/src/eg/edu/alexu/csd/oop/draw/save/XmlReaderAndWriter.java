package eg.edu.alexu.csd.oop.draw.save;

import eg.edu.alexu.csd.oop.draw.Factory;
import eg.edu.alexu.csd.oop.draw.Shape;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlReaderAndWriter {
  private Factory factory;

  public XmlReaderAndWriter() {
    factory = Factory.getInstance();
  }

  public Shape[] readXml(String path) {

    Shape[] returnedShapes;
    try {
      File xmlFile = new File(path);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
      Document doc = documentBuilder.parse(xmlFile);
      doc.getDocumentElement().normalize();

      // create class loader to set the class name for each shape

      // get how many shapes saved in the file
      NodeList dummyNodeList = doc.getElementsByTagName("Shape");
      // NodeList mapList = doc.getElementsByTagName("proparties");
      // set the returned array of shapes with number of shapes scaned
      returnedShapes = new Shape[dummyNodeList.getLength()];

      // loop for each shape tag
      for (int tmp = 0; tmp < dummyNodeList.getLength(); tmp++) {

        // catch the shape tag
        Node dummyNode = dummyNodeList.item(tmp);

        if (dummyNode.getNodeType() == Node.ELEMENT_NODE) {
          Element dummyElement = (Element) dummyNode;
          // get the name of the shape
          String shapeName = dummyElement.getAttribute("name");
          // set the name of the shape
          returnedShapes[tmp] = factory.getShapeByExactName(shapeName);

          // loop for map

          Node propartiesTag = dummyNode.getFirstChild();
          if (propartiesTag.getTextContent().equals("null")) {
            // do nothing
          } else {
            Map<String, Double> properties = returnedShapes[tmp].getProperties();
            String[] keys = new String[properties.size()];
            properties.keySet().toArray(keys);
            NodeList childs = propartiesTag.getChildNodes();
            for (int counter = 0; counter < childs.getLength(); counter++) {
              Node childNode = childs.item(counter);
              String tagValue = childNode.getTextContent();
              if (tagValue.equals("null")) {
                properties.put(keys[counter], null);
              } else {
                properties.put(keys[counter], Double.parseDouble(tagValue));
              }

            }
            returnedShapes[tmp].setProperties(properties);
          }

          // set the rest of tags
          String color = dummyElement.getElementsByTagName("Color").item(0).getTextContent();
          if (color.equals("null")) {
            returnedShapes[tmp].setColor(null);
          } else {
            returnedShapes[tmp].setColor(new Color(Integer.parseInt(color)));
          }
          String fillColor = dummyElement.getElementsByTagName("FillColor").item(0)
              .getTextContent();
          if (fillColor.equals("null")) {
            returnedShapes[tmp].setFillColor(null);
          } else {
            returnedShapes[tmp].setFillColor(new Color(Integer.parseInt(fillColor)));
          }
          String positionX = dummyElement.getElementsByTagName("positionX").item(0)
              .getTextContent();
          String positionY = dummyElement.getElementsByTagName("positionY").item(0)
              .getTextContent();
          if (positionX.equals("null") && positionY.equals("null")) {
            returnedShapes[tmp].setPosition(null);
          } else {
            returnedShapes[tmp]
                .setPosition(new Point(Integer.parseInt(positionX), Integer.parseInt(positionY)));
          }

        }
      }

    } catch (Exception e) {
      throw new RuntimeException();
    }
    return returnedShapes;
  }

  public void writeXml(String path, Shape[] shapes) {

    try {

      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

      // root elements
      Document doc = docBuilder.newDocument();
      Element rootElement = doc.createElement("File");
      doc.appendChild(rootElement);

      for (int num = 0; num < shapes.length; num++) {

        // shape elements
        Element shapeName = doc.createElement("Shape");
        rootElement.appendChild(shapeName);

        // set name to shape element
        Attr attr = doc.createAttribute("name");
        attr.setValue(shapes[num].getClass().getName().toString());
        shapeName.setAttributeNode(attr);

        // Properties elements
        Element map = doc.createElement("Properties");
        shapeName.appendChild(map);

        if (shapes[num].getProperties() == null) {
          map.appendChild(doc.createTextNode("null"));
        } else {
          String[] keys = new String[shapes[num].getProperties().size()];
          Double[] values = new Double[shapes[num].getProperties().size()];
          shapes[num].getProperties().keySet().toArray(keys);
          shapes[num].getProperties().values().toArray(values);

          // get keys and values under properties elements
          for (int counter = 0; counter < keys.length; counter++) {

            Element property = doc.createElement(keys[counter]);
            if (values[counter] == null) {
              property.appendChild(doc.createTextNode("null"));
            } else {
              property.appendChild(doc.createTextNode(values[counter].toString()));
            }
            map.appendChild(property);
          }
        }

        // Color element
        Element color = doc.createElement("Color");
        if (shapes[num].getColor() == null) {
          color.appendChild(doc.createTextNode("null"));
        } else {
          color.appendChild(doc.createTextNode(String.valueOf(shapes[num].getColor().getRGB())));
        }
        shapeName.appendChild(color);

        // Fill Color element
        Element fillColor = doc.createElement("FillColor");
        if (shapes[num].getFillColor() == null) {
          fillColor.appendChild(doc.createTextNode("null"));
        } else {
          fillColor
              .appendChild(doc.createTextNode(String.valueOf(shapes[num].getFillColor().getRGB())));
        }
        shapeName.appendChild(fillColor);

        Element positionX = doc.createElement("positionX");
        Element positionY = doc.createElement("positionY");
        if (shapes[num].getPosition() == null) {
          // positionX element
          positionX.appendChild(doc.createTextNode("null"));
          // positionY element
          positionY.appendChild(doc.createTextNode("null"));
        } else {
          int pointX = shapes[num].getPosition().x, pointY = shapes[num].getPosition().y;
          // positionX element
          positionX.appendChild(doc.createTextNode(String.valueOf(pointX)));
          // positionY element
          positionY.appendChild(doc.createTextNode(String.valueOf(pointY)));
        }

        shapeName.appendChild(positionX);
        shapeName.appendChild(positionY);

      }

      // write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(path));

      // output to console for testing
      // StreamResult result = new StreamResult(System.out);

      transformer.transform(source, result);

      // print file saved!

    } catch (ParserConfigurationException pce) {
      throw new RuntimeException();
    } catch (TransformerException tfe) {
      throw new RuntimeException();
    }

  }

}
