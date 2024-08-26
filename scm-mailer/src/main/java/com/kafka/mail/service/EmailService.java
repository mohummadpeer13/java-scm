package com.kafka.mail.service;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.kafka.mail.entity.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	@Value("${spring.mail.username}")
	private String fromEmail;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

	@Async
	public void sendMail(Email email) throws MessagingException {
		MimeMessage message = this.emailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

		Context context = new Context();
		context.setVariables(email.getContext());
		
		String emailContent = templateEngine.process(email.getTemplate(), context);

		mimeMessageHelper.setTo(email.getSendTo());
		mimeMessageHelper.setSubject(email.getSubject());
		mimeMessageHelper.setFrom(fromEmail);
		mimeMessageHelper.setText(emailContent, true);
		this.emailSender.send(message);

		LOGGER.info(String.format("SCM 2.0 REGISTER -> Email send -> %s", email.getSendTo()));
	}

}
