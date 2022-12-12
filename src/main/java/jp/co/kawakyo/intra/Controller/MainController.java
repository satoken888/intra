package jp.co.kawakyo.intra.Controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kawakyo.intra.Utils.GoogleCalendarUtils;

@Controller
public class MainController {

	@Autowired
	GoogleCalendarUtils googleCalendarUtils;

	@RequestMapping({"/","/index"})
	public String index() {

		// try {
		// 	// GoogleCalendarUtils.addEvent();
		// 	GoogleCalendarUtils.viewEvents10();
		// } catch (IOException | GeneralSecurityException e) {
		// 	// TODO Auto-generated catch block
		// 	e.printStackTrace();
		// }
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login")
	public String loginPost() {
		return "redirect:/login-error";
	}
	
	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError",true);
		return "login";
	}
	
	@RequestMapping("/top")
    public String top() {
        return "index";
    }

	@RequestMapping("/company")
	public String viewCompany(){
		return "company";
	}

	@RequestMapping("/service")
	public String viewService(){
		return "service";
	}

	@RequestMapping("contact")
	public String viewContact(){
		return "contact";
	}

	@RequestMapping("tool")
	public String viewTool(){
		return "tool";
	}

	@RequestMapping("link")
	public String viewLink(){
		return "link";
	}

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/general")
    public String genetal() {
        return "general";
    }
}
