package at.buc.framework.web.service.application_wrapper;

import java.io.IOException;

import org.apache.commons.exec.ExecuteException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationWrapperRestController {
	//TODO: Implement BASE64 encoding for the Data
	//TODO: Implement Java Client for easier use of Encoding feature in Java
	
	
	@RequestMapping("api/exec")
	public Object exec(@RequestParam(value="cmd", required=true) String cmd) {
		try {
			ApplicationExecutor exec = new ApplicationExecutor(cmd);
			exec.startExecute().waitFor();
			return exec;
		} catch (ExecuteException e) {
			e.printStackTrace();
			return e;
		} catch (IOException e) {
			return e;
		} catch (InterruptedException e) {
			return e;
		}
	}
	
}
