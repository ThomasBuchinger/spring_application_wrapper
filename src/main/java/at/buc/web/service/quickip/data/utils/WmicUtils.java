package at.buc.web.service.quickip.data.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import at.buc.framework.web.service.application_wrapper.ApplicationWrapperRestClient;
import at.buc.framework.web.service.application_wrapper.ApplicationWrapperRestController;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

/**
 * 
 * @author Thomas Buchinger
 */
public class WmicUtils {
	private static String query_properties = "/COMMAND/RESULTS/CIM/INSTANCE/*";

	/**
	 * Execute wmic Command and return a Map with the property-name=value NOTE:
	 * This method requires {@link ApplicationWrapperRestClient} class in the
	 * Classpath and {@link ApplicationWrapperRestController} running NOTE: This
	 * method assumes a single instance to be present in the returned XML.
	 * 
	 * @param cmd
	 *            wmic command to execute
	 * @return Map containing property-name=value
	 */
	public static Map<String, String> getPropertiesFromCmd(String cmd) {
		String stdout = (String) ApplicationWrapperRestClient.simple_call(cmd).get("stdout");
		XMLReader xerces = null;
		Builder parser = null;
		Nodes properties = null;
		Map<String, String> toReturn = new HashMap<String, String>();

		try {
			xerces = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
			xerces.setFeature("http://xml.org/sax/features/validation", false);
			xerces.setFeature("http://xml.org/sax/features/external-general-entities", false);
			xerces.setFeature("http://apache.org/xml/features/validation/schema", false);
			xerces.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			xerces.setFeature("http://apache.org/xml/features/validation/schema-full-checking", false);
			// xerces.setFeature("http://apache.org/xml/features/continue-after-fatal-error",
			// true);
			xerces.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);

		} catch (SAXNotRecognizedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e2) {
			// TODO Auto-generated catch block
		}
		try {
			parser = new Builder(xerces);
			Document doc = parser.build(stdout, null);
			properties = doc.query(query_properties);

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

		for (int i = 0; i < properties.size(); i++) {
			Node node = properties.get(i);
			if (node instanceof Element) {
				Element e = (Element) node;
//				System.err.print(e.getAttributeValue("NAME"));
				if (e.getChildCount() == 2) {
//					System.err.println(" <<>> " + e.getChild(0).getValue());
					toReturn.put(e.getAttributeValue("NAME"), e.getChild(0).getValue());
				} else {
//					System.err.println(" <<>> null");
					toReturn.put(e.getAttributeValue("NAME"), "");
				}

				// System.err.println("found Element:
				// "+e.getAttributeValue("CLASSNAME"));
			} else {
				System.err.println("Failed to convert Node to Element " + node.toXML());
			}

		}
		return toReturn;
	}

}
