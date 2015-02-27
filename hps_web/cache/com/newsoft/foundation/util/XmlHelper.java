package com.newsoft.foundation.util;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * A XML parse helper via dom4j.
 * 
 * @author George Guo
 * 
 */
public class XmlHelper {
	private static Log logger = LogFactory.getLog(XmlHelper.class);

	/**
	 * Load document from relative file path.
	 * 
	 * @param xmlPath
	 * @return
	 */
	public static Document readXML(String xmlPath) {
		Document doc = null;
		SAXReader saxReader = new SAXReader();
		InputStream ins = null;
		try {
			ins = XmlHelper.class.getClassLoader().getResourceAsStream(xmlPath);
			doc = saxReader.read(ins);
		} catch (Exception e) {
			logger.error("Error when read xml from classpath: " + xmlPath, e);
		}
		return doc;
	}

	/**
	 * Load document from string content.
	 * 
	 * @param xmlString
	 * @return
	 */
	public static Document readXMLContentString(String xmlString) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			logger.error("Error when read xml from string content", e);
		}
		return doc;
	}

	/**
	 * create a document start with given root element name.
	 * 
	 * @param rootElementName
	 * @return
	 */
	public static Document createRootElementXML(String rootElementName) {
		Document doc = DocumentHelper.createDocument();
		doc.addElement(rootElementName);
		return doc;
	}

	/**
	 * Get the root element of the document.
	 * 
	 * @param doc
	 * @return
	 */
	public static Element getRootElement(Document doc) {
		if (doc != null) {
			return doc.getRootElement();
		} else {
			return null;
		}
	}

	/**
	 * append new sub element to the root document.
	 * 
	 * @param doc
	 * @param nodeName
	 * @param nodeValue
	 * @return
	 */
	public static Document appendElementToRoot(Document doc, String nodeName, String nodeValue) {
		if (doc != null && nodeName != null) {
			Element root = doc.getRootElement();
			Element childTmp = root.addElement(nodeName);
			if (nodeValue != null) {
				childTmp.setText(nodeValue);
			}
		}
		return doc;
	}

	/**
	 * append new sub element to the specified parent element.
	 * 
	 * @param parentElement
	 * @return
	 */
	public static Element appendElement(Element parentElement, String nodeName, String nodeValue) {
		if (parentElement != null && nodeName != null) {
			Element childTmp = parentElement.addElement(nodeName);
			if (nodeValue != null) {
				childTmp.setText(nodeValue);
			}
		}
		return parentElement;
	}

	/**
	 * 
	 * Copy the element with given name from the source document to another
	 * document under the root.
	 * 
	 * @param toDoc
	 * @param fromDoc
	 * @param nodeName
	 * @return
	 */
	public static Document appendElementFromOtherDoc(Document toDoc, Document fromDoc, String nodeName) {
		if (toDoc != null && fromDoc != null && nodeName != null) {
			String nodeValue = getNodeText(fromDoc, nodeName);
			Element root = toDoc.getRootElement();
			appendElement(root, nodeName, nodeValue);
		}
		return toDoc;
	}

	/**
	 * read node text from the given document and node name.
	 * 
	 * @param doc
	 * @param nodeName
	 * @return
	 */
	public static String getNodeText(Document doc, String nodeName) {
		String xpath = "//" + nodeName;
		String nodeText = "";
		try {
			Node node = doc.selectSingleNode(xpath);
			if (node != null)
				nodeText = node.getText();
		} catch (Exception e) {
			logger.error("error when get node text from document", e);
		}
		return nodeText;
	}

	/**
	 * read node text from a give node name and XML string content.
	 * 
	 * @param docXmlStr
	 * @param nodeName
	 * @return
	 */
	public static String getNodeText(String docXmlStr, String nodeName) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(docXmlStr);
		} catch (DocumentException e) {
			logger.error("error when get node text from xml string", e);
		}
		String nodeText = "";
		if (doc != null) {
			nodeText = getNodeText(doc, nodeName);
		}
		return nodeText;
	}

	/**
	 * 校验xml文档中是否含有指定的节点
	 * 
	 * @param doc
	 * @param nodeName
	 * @return
	 */
	public static boolean checkIfExistNode(Document doc, String nodeName) {
		List<?> list = doc.selectNodes("//" + nodeName);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

}
