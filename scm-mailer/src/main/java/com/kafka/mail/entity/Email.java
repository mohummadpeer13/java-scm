package com.kafka.mail.entity;

import java.util.HashMap;
import java.util.Map;

public class Email {
	private String sendTo;
	private String subject;
	private String template;
	private Map<String, Object> context;

	public Email() {
		this.context = new HashMap<>();
	}

	public <T> void init(T context) {
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}
}
