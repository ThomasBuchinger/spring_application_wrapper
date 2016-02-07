package at.buc.framework.web.service.application_wrapper;

import java.util.Base64;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConfigurationProperties(prefix="application_wapper.client")
public class ApplicationWrapperClientRefImpl implements ApplicationWrapperClient, EnvironmentAware{
	
	public static final Logger logger=LoggerFactory.getLogger(ApplicationWrapperClientRefImpl.class);

	//TODO This would be suitable to exchange via Java Spaces
	static Environment env;
	
	public static String urlTemplate;
	public static String binPath;
	
	public ApplicationWrapperClientRefImpl() { }
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> simple_call(String cmd) {
		RestTemplate tmpl=new RestTemplate();
//		String urlTmpl="http://127.0.0.1:8080/api/exec?cmd={cmd}";
//		String urlTemplate= env.getProperty("my.urlTemplate");
		
		logger.info(String.format("API-CALL: Endpoint=%s, cmd=%s", urlTemplate, cmd));
		Map<String, Object> ret= tmpl.getForObject(urlTemplate, Map.class, cmd);

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

//============= Getters & Setters ==========================================================
	@Override
	public void setEnvironment(Environment environment) {
		this.env=environment;
		
	}


	public static String getUrlTemplate() {
		return urlTemplate;
	}


	public static void setUrlTemplate(String urlTemplate) {
		ApplicationWrapperClientRefImpl.urlTemplate = urlTemplate;
	}


	public static String getBinPath() {
		return binPath;
	}


	public static void setBinPath(String binPath) {
		ApplicationWrapperClientRefImpl.binPath = binPath;
	}
	
}
