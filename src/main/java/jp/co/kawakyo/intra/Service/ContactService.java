package jp.co.kawakyo.intra.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import jp.co.kawakyo.intra.Utils.SendMailService;

@Service
public class ContactService {

    @Autowired
    SendMailService sendMailService;
    
    public void sendContactMail(String userName,String inquiry_text){
        //送信先、件名、文章内容の設定
        String from = "intra@ramenkan.com";
        String[] to = {"ke.sato@ramenkan.com"};
        String subject = "【河京イントラ】お問い合わせがありました。";

        //HTMLテンプレート記入内容の設定
        Context context = new Context();
        context.setVariable("inquiry_text", inquiry_text);
        context.setVariable("name", userName);

        //送信処理
        sendMailService.sendMail(from, to, subject, "contactMail", context);
    }

}
