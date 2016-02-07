package at.buc.framework.web.service.application_wrapper;

import java.util.Map;

public interface ApplicationWrapperClient {
	public Map<String, Object> simple_call(String cmd);
}
