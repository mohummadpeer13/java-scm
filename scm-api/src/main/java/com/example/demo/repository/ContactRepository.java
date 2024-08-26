package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Contact;
import com.example.demo.entities.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String>{
    Page<Contact> findContactsByUser(User user, Pageable pageable);
    
    Page<Contact> findByUserAndEmailContaining(User user, String emailkeyword, Pageable pageable);
    Page<Contact> findByUserAndNameContaining(User user, String namekeyword, Pageable pageable);
    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phonekeyword, Pageable pageable);

}
