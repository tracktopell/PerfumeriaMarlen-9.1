/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.dom4j.IllegalAddException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author alfredo
 */
public class QueryXMLLoader {

	private Document xmlDoc;
	
	private Hashtable<String,String> loadedQueryes;
	
	private static QueryXMLLoader instance;

	public static QueryXMLLoader getInstance()  throws IllegalArgumentException{
		if(instance == null){
			instance = new QueryXMLLoader("/native_queryes.xml");
		}
		return instance;
	}
	
	private QueryXMLLoader(String resourceName) throws IllegalArgumentException {
		InputStream is = null;
		try {
			is = QueryXMLLoader.class.getResourceAsStream(resourceName);

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			xmlDoc = dBuilder.parse(is);
			loadedQueryes =  new Hashtable<String,String> ();
		} catch (IOException ioe) {
			throw new IllegalAddException("fail to read resource:" + resourceName);
		} catch (ParserConfigurationException pce) {
			throw new IllegalAddException("ParserConfigurationException:" + pce.getLocalizedMessage());
		} catch (SAXException se) {
			throw new IllegalAddException("SAXException:" + se.getLocalizedMessage());
		}
	}

	public String loadQuery(String id) {
		
		if(!loadedQueryes.containsKey(id)){
		
			String queryText = null;
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work

			xmlDoc.getDocumentElement().normalize();

			//System.out.println("Root element :" + xmlDoc.getDocumentElement().getNodeName());

			NodeList nList = xmlDoc.getElementsByTagName("nativequery");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);


				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					final String attrId = eElement.getAttribute("id");

					if(attrId.equals(id)) {
						queryText = eElement.getElementsByTagName("sql").item(0).getTextContent();
						//System.out.println("SQL : " + queryText);
						loadedQueryes.put(id, queryText);
					}
				}
			}
		} 
		return loadedQueryes.get(id);
	}
	
	public static void main(String[] args) {
		QueryXMLLoader ql=null;
		try {
			ql = QueryXMLLoader.getInstance();
			
			System.out.println("Query:"+ql.loadQuery("inventarios"));
			
		}catch(Exception e){
			e.printStackTrace(System.err);
		}
		
	}
	
}
