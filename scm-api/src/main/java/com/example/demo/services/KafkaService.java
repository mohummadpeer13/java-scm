package com.example.demo.services;

import com.example.demo.entities.Email;

public interface KafkaService {
    Void sendSignUpEmail (Email email);
}
