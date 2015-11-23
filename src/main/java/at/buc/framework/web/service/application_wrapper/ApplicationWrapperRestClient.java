package at.buc.framework.web.service.application_wrapper;

import java.util.Base64;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class ApplicationWrapperRestClient {
	
	//TODO This would be suitable for Apache River
	
	public static final Logger logger=LoggerFactory.getLogger(ApplicationWrapperRestClient.class);
	public static final String binPath="src/main/resources/bin/";
	
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> simple_call(String cmd) {
		RestTemplate tmpl=new RestTemplate();
		String urlTmpl="http://127.0.0.1:8080/api/exec?cmd={cmd}";
		
		logger.info(String.format("API-CALL: Endpoint=%s, cmd=%s", urlTmpl, cmd));
		Map<String, Object> ret= tmpl.getForObject(urlTmpl, Map.class, cmd);

		byte[] cmd_bytes = ((String) ret.get("cmd_base64")).getBytes();
		byte[] stdout_bytes = ((String) ret.get("stdout_base64")).getBytes();
		byte[] stderr_bytes = ((String) ret.get("stderr_base64")).getBytes();
		
		ret.put("cmd", new String(Base64.getDecoder().decode(cmd_bytes)));
		ret.put("stdout", new String(Base64.getDecoder().decode(stdout_bytes)));
		ret.put("stderr", new String(Base64.getDecoder().decode(stderr_bytes)));

		ret.remove("cmd_base64");
		ret.remove("stdout_base64");
		ret.remove("stderr_base64");
		
//		logger.warn(String.format("API-CALL-Finished: Endpoint=%s, stdout=%s", urlTmpl, ret.get("stdout")));
		
		return ret;
	}
//	public static Map<String, Object> simple_call(String cmd, String workingDir) {
//		return null;
//	}
}
