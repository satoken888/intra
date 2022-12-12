package jp.co.kawakyo.intra.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.kawakyo.intra.Entity.UserAccount;
import jp.co.kawakyo.intra.Service.ContactService;

@Controller
public class ContactController {

    Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    ContactService contactService;
    
    @PostMapping("/contact/confirm")
    public String confirmContact(@RequestParam("inquiry_text") String inquiry_text,Model model){

        model.addAttribute("inquiry_text", inquiry_text);

        return "contact/confirm";
    }

    @PostMapping("/contact/finish")
    public String finishContact(@AuthenticationPrincipal UserAccount loginUser, @RequestParam("inquiry_text") String inquiry_text,Model model){

        logger.info(inquiry_text);

        //管理者へメール送信処理
        contactService.sendContactMail(loginUser.getUsername(), inquiry_text);

        return "contact/finish";
    }
}
