package jp.co.kawakyo.intra.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping({"/","/index"})
	public String index() {
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

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/general")
    public String genetal() {
        return "general";
    }
}
