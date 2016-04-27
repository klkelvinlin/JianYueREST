package com.jy.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtils {

	public static Document parseXmlFile(String filePath){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(filePath);
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
	public static Document parseXmlString(String xmlString){
		try {
			 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		     DocumentBuilder builder = factory.newDocumentBuilder();
		     return builder.parse(new InputSource(new StringReader(xmlString)));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Node getNodeByXPath(Document doc, String xpath){
		try{
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			XPathExpression xPathExpression = xPath.compile(xpath);
			Node n = (Node)xPathExpression.evaluate(doc, XPathConstants.NODE);
			return n;
		}catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Node getNodeByXPath(Node rootNode, String xpath){
		try{
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			XPathExpression xPathExpression = xPath.compile(xpath);
			Node n = (Node)xPathExpression.evaluate(rootNode, XPathConstants.NODE);
			return n;
		}catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static NodeList getNodeListByXPath(Document doc, String xpath){
		try{
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			XPathExpression xPathExpression = xPath.compile(xpath);
			Object result = xPathExpression.evaluate(doc, XPathConstants.NODESET);
			NodeList nl = (NodeList) result;
			
			return nl;
		}catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getNodeContentsByXPath(Document doc, String xpath){
		Node n = getNodeByXPath(doc, xpath);
		if(n==null){return null;}
		return n.getTextContent();
	}
	public static String getNodeContentsByXPath(Node rootNode, String xpath){
		Node n = getNodeByXPath(rootNode, xpath);
		if(n==null){return null;}
		return n.getTextContent();
	}
	
	public static boolean setNodeContentsByXPath(Document doc, String xpath, String contents){
		Node n = getNodeByXPath(doc, xpath);
		if(n==null){return false;}
		n.setTextContent(contents);
		return true;
	}
	public static String getStringFromDocument(Document doc)
	{
	    try
	    {
	       DOMSource domSource = new DOMSource(doc);
	       StringWriter writer = new StringWriter();
	       StreamResult result = new StreamResult(writer);
	       TransformerFactory tf = TransformerFactory.newInstance();
	       Transformer transformer = tf.newTransformer();
	       transformer.transform(domSource, result);
	       return writer.toString();
	    }
	    catch(TransformerException ex)
	    {
	       ex.printStackTrace();
	       return null;
	    }
	} 
	public static Element createNewNode(Document doc, String nodeName){
		return doc.createElement(nodeName);
	}
	
	public static void embedDocInDoc(Document targetDoc, Document sourceDoc, Node parentNode){
		Element sourceRoot = sourceDoc.getDocumentElement();
		Node adopted = targetDoc.adoptNode(sourceRoot.cloneNode(true));
		parentNode.appendChild(adopted);
	}
	
	public static String getNodeValue(Element element,String tagName){
		NodeList firstNames = element .getElementsByTagName(tagName).item(0).getChildNodes();
		return ((Node) firstNames.item(0)).getNodeValue();
	}
	
	public static void main(String[] args) {
		//System.out.println( UUID.randomUUID().toString());
		//System.out.println( UUID.randomUUID().toString());

	}
}
