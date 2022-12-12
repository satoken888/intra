package jp.co.kawakyo.intra.Utils;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Service
public class SendMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String from,String[] to,String subject, String htmlTemplateName,Context context) {

        javaMailSender.send(new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                        StandardCharsets.UTF_8.name());
                helper.setFrom(from);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(getMailBody(htmlTemplateName, context), true);
            }
        });

    }

    private String getMailBody(String templateName, Context context) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(mailTemplateResolver());
        return templateEngine.process(templateName, context);

    }

    private ClassLoaderTemplateResolver mailTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("mailtemplate/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(true);
        return templateResolver;
    }

}
