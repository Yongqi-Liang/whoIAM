package liangyongqi.iam.Util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class Mail {
    @Autowired
    private JavaMailSender javaMailSender;

    public boolean sendMail(String to, String cc, String subject, String htmlContent) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // 第二个参数表示是否支持附件

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);  // 设置HTML内容
            helper.setFrom("no-reply@liangyongqi.top");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
