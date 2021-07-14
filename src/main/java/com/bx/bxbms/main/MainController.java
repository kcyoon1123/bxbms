package com.bx.bxbms.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping(value= {"/main/index.do", "/"})
	public String mainPage() {
		return "main/main";
	}
	@RequestMapping(value= {"/test.do"})
	public String testPage() {
		return "main/test";
	}

}
