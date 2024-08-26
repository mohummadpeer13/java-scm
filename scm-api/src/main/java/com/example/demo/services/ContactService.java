package com.example.demo.services;


import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import com.example.demo.dto.AddContactForm;
import com.example.demo.dto.UpdateContactForm;
import com.example.demo.entities.Contact;
import com.example.demo.entities.User;

public interface ContactService {
    Contact saveContact(AddContactForm addContactForm, Authentication Authentication);
    Contact updateContact(UpdateContactForm updateContactForm, String contactId);

    // get contact by user
    Page<Contact> getContactsByUser(User user, int page,int size, String sortField, String sortDirection);

    // search contact by email, name, phone by user
    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);
    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,User user);

    Contact getById(String contactId);

    void delete(String contactId);

   
}
  