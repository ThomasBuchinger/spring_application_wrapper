package at.buc.web.ui;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import at.buc.utils.TreeNode;


@Controller
public class DefaultViewController {
	
	
	public static NavElement getRegistrations() {
		NavElement root=new NavElement("ROOT", "");
		root
			.addNode(new NavElement("Applications", "")
					.addNode(new NavElement("Application Wrapper", "/exec"))
					.addNode(new NavElement("Quick IP", "/quickip")))
			.addNode(new NavElement("Endpoints", "")
					.addNode(new NavElement("Application Wrapper REST-API","api/exec"))
					.addNode(new NavElement("Quickip REST-API","api/quickip")))
			;
		return root;
	}
	
	@RequestMapping("/")
	public String index(Model model) {
		return "runView";
	}
}
