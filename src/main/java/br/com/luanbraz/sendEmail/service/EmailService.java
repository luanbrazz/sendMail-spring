package br.com.luanbraz.sendEmail.service;

import br.com.luanbraz.sendEmail.model.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
public class EmailService {

    private  static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente; // sender

    public String sendMail(EmailDetails details) {

        LOGGER.info("Enviando emaiil simples");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(remetente);
        mailMessage.setTo(details.getDestinatario());
        mailMessage.setText(details.getCorpoMensagem());
        mailMessage.setSubject(details.getAssunto());

        javaMailSender.send(mailMessage);

        return "E-mail enviado com sucesso!";
    }

    public String sendMailWithAttachment(EmailDetails details) {

        LOGGER.info("Enviando emaiil com anexo");


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(remetente);
            mimeMessageHelper.setTo(details.getDestinatario());
            mimeMessageHelper.setText(details.getCorpoMensagem());
            mimeMessageHelper.setSubject(details.getAssunto());

            FileSystemResource file = new FileSystemResource(new File(details.getAnexo()));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);

            javaMailSender.send(mimeMessage);

            LOGGER.info("E-mail com anexo enviado com sucesso!");
            return "E-mail com anexo enviado com sucesso!";

        } catch (MessagingException e) {
            LOGGER.error("ERRO ao enviar o email: ", e);
            throw new RuntimeException(e);
        }

    }
}
