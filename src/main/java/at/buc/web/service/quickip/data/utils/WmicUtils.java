package at.buc.web.service.quickip.data.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import at.buc.framework.web.service.application_wrapper.ApplicationWrapperRestClient;
import at.buc.framework.web.service.application_wrapper.ApplicationWrapperRestController;

/**
 * 
 * @author Thomas Buchinger
 */
public class WmicUtils {

	/**
	 * Execute wmic Command and return a Map with the property-name=value
	 * 
	 * @Note: This method requires {@link ApplicationWrapperRestClient} class in
	 *        the Classpath and {@link ApplicationWrapperRestController} running
	 * @Note: if multiple Instances are present, only the first one is returned
	 * 
	 * @param cmd
	 *            wmic command to execute
	 * @return Map containing property-name=value
	 */
	public static Map<String, String> getSingleInstanceProperties(String cmd) {
		String stdout = (String) ApplicationWrapperRestClient.simple_call(cmd).get("stdout");
		List<Map<String, String>> re = new ArrayList<Map<String, String>>();
		try {
			return CSVParser.parse(stdout, CSVFormat.DEFAULT.withHeader()).getRecords().get(0).toMap();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Execute wmic Command and return a Map with the property-name=value
	 * 
	 * @Note: This method requires {@link ApplicationWrapperRestClient} class in
	 *        the Classpath and {@link ApplicationWrapperRestController} running
	 * 
	 * @param cmd
	 *            wmic command to execute
	 * @return Map containing property-name=value
	 */
	public static List<Map<String, String>> getMultipleInstanceProperties(String cmd) {
		String stdout = (String) ApplicationWrapperRestClient.simple_call(cmd).get("stdout");
		List<Map<String, String>> re = new ArrayList<Map<String, String>>();
		try {
			for (CSVRecord record : CSVParser.parse(stdout, CSVFormat.DEFAULT.withHeader()).getRecords()) {
				re.add(record.toMap());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return re;
	}
}
