package at.buc.spring_webapplication_wrapper.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationWrapper {

	@RequestMapping(value="exec")
	public Map<String, String> execute_application (@RequestParam("cmd") String cmd) {
		try {
			return ApplicationExecutor.exec(cmd);
		} catch (IOException e) {
			TreeMap<String, String> ret=new TreeMap<String, String>();
			ret.put("stdout", "");
			ret.put("stderr", e.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			ret.put("trace", sw.toString());
			return ret;
		}
	}
}
