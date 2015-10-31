package at.buc.web.service.application_wrapper;

import java.io.IOException;

import org.apache.commons.exec.ExecuteException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationWrapperRestController {

	@RequestMapping("api/exec")
	public Object exec(@RequestParam(value="cmd", required=true) String cmd) {
		try {
			return ApplicationExecutor.exec(cmd);
		} catch (ExecuteException e) {
			e.printStackTrace();
			return e;
		} catch (IOException e) {
			return e;
		}
	}
	
}
