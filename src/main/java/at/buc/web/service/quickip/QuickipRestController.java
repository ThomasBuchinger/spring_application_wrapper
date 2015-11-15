package at.buc.web.service.quickip;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuickipRestController {

	@RequestMapping("/api/quickip")
	public Object get() {
		return "unfinished";
	}
	
}
