package com.example.demo.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Email;
import com.example.demo.services.KafkaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, Email> kafkaTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Void sendSignUpEmail(Email email) {
        kafkaTemplate.send("sendMail", email);
        logger.info("Post /register => " + email.getSendTo() + " => envoi mail inscription à kafka...");
        return null;
    }

}
