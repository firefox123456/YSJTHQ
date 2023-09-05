package huangqi.user.service.impl;

import huangqi.user.async.ConstantFiledUtil;
import huangqi.user.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件发送类
 *
 * @author "黄骐"
 * @date 2023/09/05 14:29
 **/

@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String from;

    @Override
    @Async("AsyncThreadPool")
    public void sendLoginMail(String to, String ip) {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            StringBuilder sb = new StringBuilder();
            mimeMessageHelper.setSubject("登录提醒");
            sb.append("您的账号登录成功 "+"ip:"+ip+"!");
            mimeMessageHelper.setText(sb.toString(), true);
            javaMailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            log.info("邮件发送失败"+"sendLoginMail");
        }
    }


}
