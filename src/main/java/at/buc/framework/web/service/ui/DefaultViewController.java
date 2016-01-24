package at.buc.framework.web.service.ui;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DefaultViewController {
	
	
	public static NavElement getRegistrations() {
		NavElement root=new NavElement("ROOT", "");
		root
			.addNode(new NavElement("Applications", "")
					.addNode(new NavElement("Application Wrapper", "/exec"))
					.addNode(new NavElement("Quick IP", "/quickip")))
			.addNode(new NavElement("Endpoints", "")
					.addNode(new NavElement("Application Wrapper REST-API","api/exec?cmd=cmd /version"))
					.addNode(new NavElement("Quickip REST-API","api/quickip"))
					.addNode(new NavElement("QuickIP Iface Table", "/api/quickip/grid/iface"))
					.addNode(new NavElement("QuickIP Route Table", "/api/quickip/grid/route")))
			;
		return root;
	}
	
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("nav", DefaultViewController.getRegistrations());
		model.addAttribute("apps", DefaultViewController.getRegistrations().getChildren().get(0));
		model.addAttribute("endpoints", DefaultViewController.getRegistrations().getChildren().get(1));
		
		return "quickipView";
	}
}
