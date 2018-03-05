package uce.edu.bautista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by Alexis on 02/03/2018.
 */
@Service
public class SendMailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String to){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Notificacion");
        mailMessage.setText("Te enviamos una notificacion de qiue se ingreso un nuevo instrumento");

        javaMailSender.send(mailMessage);
    }
}
