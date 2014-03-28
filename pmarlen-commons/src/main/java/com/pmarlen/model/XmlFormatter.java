package com.pmarlen.model;

//import org.apache.xml.serialize.OutputFormat;
//import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author alfredo
 * Pretty-prints xml, supplied as a string.
 * <p/>
 * eg.
 * <code>
 * String formattedXml = XmlFormatter.getInstance().format("<tag><nested>hello</nested></tag>");
 * </code>
 */
public class XmlFormatter {
	private static XmlFormatter instance;

	public static XmlFormatter getInstance() {
		if(instance == null){
			instance = new XmlFormatter();
		}
		return instance;
	}
	
    private XmlFormatter() {
    }
	/*
    public String format(String unformattedXml) {
        try {
            final Document document = parseXmlFile(unformattedXml);

            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	*/

    private Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String unformattedXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><QueryMessage " +
                        " xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\"" +
                        " xmlns:query=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query\">" +
                        "<Query>" +
                        "<query:CategorySchemeWhere>" +
                        "<query:AgencyID>ECB\n\n\n\n</query:AgencyID>" +
                        "</query:CategorySchemeWhere>" +
                        "</Query>" +
                        "</QueryMessage>";

        //System.out.println(XmlFormatter.getInstance().format(unformattedXml));
    }

}