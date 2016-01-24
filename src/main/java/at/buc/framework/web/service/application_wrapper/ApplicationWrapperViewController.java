package at.buc.framework.web.service.application_wrapper;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.buc.framework.web.service.ui.DefaultViewController;

@Controller()
public class ApplicationWrapperViewController {

	//TODO The Command should be executed by api/exec-RestController not the exec-ViewController
	@RequestMapping(value="exec", method=RequestMethod.GET)
	public String executeCmd_get(@RequestParam(value="cmd", required=false)String cmd, Model model) {
		model.addAttribute("nav", DefaultViewController.getRegistrations());
		model.addAttribute("apps", DefaultViewController.getRegistrations().getChildren().get(0));
		model.addAttribute("endpoints", DefaultViewController.getRegistrations().getChildren().get(1));
		
		if (cmd != null) {
			try {
				ApplicationExecutor exec = new ApplicationExecutor(cmd);
				exec.startExecute().waitFor();
				model.addAllAttributes(exec.asMap());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}else {
			model.addAttribute("stdout", "");
			model.addAttribute("stderr", "");
		}
		
		return "runView";
	}
//	@RequestMapping(value="exec", method=RequestMethod.POST)
//	public String executeCmd_post(@RequestParam("cmd") String cmd, Model model) {
//		try {
//			model.addAllAttributes(ApplicationExecutor.exec(cmd));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}			
//		return "runView";
//	}
}
