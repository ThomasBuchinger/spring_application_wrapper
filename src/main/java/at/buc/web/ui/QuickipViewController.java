package at.buc.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import at.buc.web.service.quickip.Interface;
import at.buc.web.service.quickip.InterfaceEntry;

@Controller
public class QuickipViewController {

	@RequestMapping("/quickip")
	public String quickip(Model model) {
		model.addAttribute("nav", DefaultViewController.getRegistrations());
		model.addAttribute("apps", DefaultViewController.getRegistrations().getChildren().get(0));
		model.addAttribute("endpoints", DefaultViewController.getRegistrations().getChildren().get(1));
		
		String [] dns= {"8.8.8.8", "8.8.4.4"};
		model.addAttribute("iface", new InterfaceEntry("Wireless LAN", "255.255.255.255.0", 32, true, false, "10.0.0.254", dns ));
		
		return "QuickipView";
	}
	
}
