package at.buc.web.service.quickip.data.dao;

import java.io.IOException;
import java.util.Map;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import at.buc.framework.web.service.application_wrapper.ApplicationWrapperRestClient;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class AbastractWmiUtis {

	public static Map<String, String>getPropertiesFromCmd(String cmd){
		String stdout=(String) ApplicationWrapperRestClient.simple_call(cmd).get("stdout");
		
		XMLReader xerces = null;
		try {
			xerces = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
			xerces.setFeature("http://xml.org/sax/features/validation", false);
			xerces.setFeature("http://xml.org/sax/features/external-general-entities", false);
			xerces.setFeature("http://apache.org/xml/features/validation/schema", false);
			xerces.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			xerces.setFeature("http://apache.org/xml/features/validation/schema", false);
			xerces.setFeature("http://apache.org/xml/features/validation/schema", false);
			xerces.setFeature("http://apache.org/xml/features/validation/schema", false);
			xerces.setFeature("http://apache.org/xml/features/validation/schema", false);
			xerces.setFeature("http://apache.org/xml/features/validation/schema", false);
			xerces.setFeature("http://apache.org/xml/features/validation/schema", false);
			xerces.setFeature("http://apache.org/xml/features/validation/schema", false);
			xerces.setFeature("http://apache.org/xml/features/validation/schema", false);
			xerces.setFeature("http://apache.org/xml/features/validation/schema", false);
			} catch (SAXNotRecognizedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e2) {
			// TODO Auto-generated catch block
	    }	                         

		Builder parser=new Builder(xerces);
		Elements properties=null;
		Nodes nodes=null;
		try {
			Document doc = parser.build(stdout);
			nodes =doc.query("COMMAND//RESULTS//CIM//INSTANCE//PROPERTY");
			
		} catch (ValidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < nodes.size(); i++) {
			Node node=nodes.get(i);
			if (node instanceof Element) {
				Element e=(Element) node;
				System.err.println("found Element: "+e.getAttributeValue("NAME"));
			} else {
				System.err.println("Failed to convert Node to Element "+node.toXML());
			}
			
		}
		return null;
	}
	
}
