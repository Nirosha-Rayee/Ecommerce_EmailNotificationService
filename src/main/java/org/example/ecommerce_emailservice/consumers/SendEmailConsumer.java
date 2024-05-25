package org.example.ecommerce_emailservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.ecommerce_emailservice.dtos.SendEmailMessageDto;
import org.example.ecommerce_emailservice.utilities.EmailUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class SendEmailConsumer {
    private ObjectMapper objectMapper;
    private EmailUtil emailUtil;

    public SendEmailConsumer(ObjectMapper objectMapper,
                             EmailUtil emailUtil) {
        this.objectMapper = objectMapper;
        this.emailUtil = emailUtil;
    }

    @KafkaListener(topics = "sendEmail", groupId = "emailService")
    public void handleSendEmail(String message) throws JsonProcessingException {
        SendEmailMessageDto emailMessage = objectMapper.readValue(message, SendEmailMessageDto.class);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("namanbatch@gmail.com", ""); //Naman@123  .
                return new PasswordAuthentication("nirosharayee@gmail.com", "ekifvfyeysnsnbjy"); //niroshaSDE@1 // app password : ekif vfye ysns nbjy
                //here we have to give our email and password is App password in gamil, security settings-> 2 step verification -> app password



            }
        };
        Session session = Session.getInstance(props, auth);

        emailUtil.sendEmail(
                session,
                emailMessage.getTo(),
                emailMessage.getSubject(),
                emailMessage.getBody()
        );
    }
}

//STEPS TO GENERATE APP PASSWORD:

//If you see no app-specific password when you visit this page: https://myaccount.google.com/apppasswords,
// you would need to create one by selecting the options from the drop-down boxes and then clicking the Generate button.

//The steps are explained here: https://support.google.com/accounts/answer/185833?hl=en. Please post an update.