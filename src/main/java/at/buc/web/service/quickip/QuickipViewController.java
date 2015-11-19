package at.buc.web.service.quickip;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import at.buc.framework.web.ui.DefaultViewController;


@Controller
public class QuickipViewController {

	@RequestMapping("/quickip")
	public String quickip(Model model) {
		model.addAttribute("nav", DefaultViewController.getRegistrations());
		model.addAttribute("apps", DefaultViewController.getRegistrations().getChildren().get(0));
		model.addAttribute("endpoints", DefaultViewController.getRegistrations().getChildren().get(1));
		
		return "QuickipView";
	}
	
}
