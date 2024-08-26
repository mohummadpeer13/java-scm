package com.kafka.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.mail.entity.Email;
import com.kafka.mail.service.EmailService;

import jakarta.mail.MessagingException;

@SpringBootApplication
public class MailApplication {

	@Autowired
	EmailService emailService;

	public static void main(String[] args) {
		SpringApplication.run(MailApplication.class, args);
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(MailApplication.class);

	@KafkaListener(topics = "sendMail", groupId = "group_id")
	public void consume(String message) throws MessagingException {

		ObjectMapper objectMapper = new ObjectMapper();
		Email email = new Email();
		try {
			email = objectMapper.readValue(message, Email.class);
			
			LOGGER.info(String.format("Message received -> %s", message));
			this.emailService.sendMail(email);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

}
