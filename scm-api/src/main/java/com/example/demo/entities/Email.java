package com.example.demo.entities;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Email {
    private String sendTo;
    private String subject;
    private String template;
    private Map<String, Object> context;
}
