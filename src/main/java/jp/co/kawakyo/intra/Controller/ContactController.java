package jp.co.kawakyo.intra.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {
    
    @PostMapping("/contact/confirm")
    public String confirmContact(@RequestParam("inquiry_text") String inquiry_text,Model model){

        model.addAttribute("inquiry_text", inquiry_text);

        return "contact/confirm";
    }

    @PostMapping("/contact/finish")
    public String finishContact(@RequestParam("inquiry_text") String inquiry_text,Model model){

        //TODO: メール送信処理

        return "contact/finish";
    }
}
