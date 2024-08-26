package com.example.demo.services.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddContactForm;
import com.example.demo.dto.UpdateContactForm;
import com.example.demo.entities.Contact;
import com.example.demo.entities.User;
import com.example.demo.helpers.ResourceNotFoundExeception;
import com.example.demo.repository.ContactRepository;
import com.example.demo.services.ContactService;
import com.example.demo.services.ImageService;
import com.example.demo.services.UserService;
import com.example.demo.validators.GetEmailAuth;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @Override
    public Contact saveContact(AddContactForm addContactForm, Authentication authentication) {
        // recupère utilisateur connecté à partir de son email
        String username = GetEmailAuth.getEmailForAuth(authentication);
        User user = this.userService.getUserByEmail(username);

        // donnée de l'utilisateur à sauvegardé
        Contact contactSaved = new Contact();

        // traitement image à partir de multipartfile
        if (addContactForm.getContactImage() != null && !addContactForm.getContactImage().isEmpty()) {
            String[] imageData = this.imageService.uploadImage(addContactForm.getContactImage());
            contactSaved.setCloudinaryImagePublicId(imageData[0]);
            contactSaved.setContactImage(imageData[1]);
        } else {
            contactSaved.setCloudinaryImagePublicId("");
            contactSaved.setContactImage(
                    "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
        }

        // String fileurl =
        // "https://static.vecteezy.com/system/resources/previews/024/983/914/non_2x/simple-user-default-icon-free-png.png";
        // sauvegarde le contact
        String contactId = UUID.randomUUID().toString();
        contactSaved.setId(contactId);
        contactSaved.setName(addContactForm.getName());
        contactSaved.setEmail(addContactForm.getEmail());
        contactSaved.setPhoneNumber(addContactForm.getPhoneNumber());
        contactSaved.setAddress(addContactForm.getAddress());
        contactSaved.setDescription(addContactForm.getDescription());
        contactSaved.setWebsiteLink(addContactForm.getWebsiteLink());
        contactSaved.setLinkedInLink(addContactForm.getLinkedInLink());
        contactSaved.setFavorite(addContactForm.isFavorite());
        contactSaved.setUser(user);
        this.contactRepository.save(contactSaved);

        return contactSaved;
    }

    @Override
    public Contact updateContact(UpdateContactForm updateContactForm, String contactId) {
        Contact contactUpdate = this.contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundExeception("Contact non trouvé avec cet ID" + contactId));

        // traitement image à partir de multipartfile
        if (updateContactForm.getContactImage() != null && !updateContactForm.getContactImage().isEmpty()) {
            String[] imageData = this.imageService.uploadImage(updateContactForm.getContactImage());
            contactUpdate.setCloudinaryImagePublicId(imageData[0]);
            contactUpdate.setContactImage(imageData[1]);
        }

        // maj donnée du contact
        contactUpdate.setName(updateContactForm.getName());
        contactUpdate.setEmail(updateContactForm.getEmail());
        contactUpdate.setPhoneNumber(updateContactForm.getPhoneNumber());
        contactUpdate.setAddress(updateContactForm.getAddress());
        contactUpdate.setDescription(updateContactForm.getDescription());
        contactUpdate.setFavorite(updateContactForm.isFavorite());
        contactUpdate.setWebsiteLink(updateContactForm.getWebsiteLink());
        contactUpdate.setLinkedInLink(updateContactForm.getLinkedInLink());
        this.contactRepository.save(contactUpdate);

        return contactUpdate;

    }

    @Override
    public void delete(String contactId) {
        Contact contact = this.contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundExeception("Contact non trouvé avec cet ID " + contactId));

        // suppression du contact
        this.contactRepository.delete(contact);
    }

    @Override
    public Page<Contact> getContactsByUser(User user, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return this.contactRepository.findContactsByUser(user, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,
            User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return this.contactRepository.findByUserAndEmailContaining(user, emailKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {

        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return this.contactRepository.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
            String order, User user) {

        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return this.contactRepository.findByUserAndPhoneNumberContaining(user, phoneNumberKeyword, pageable);
    }

    @Override
    public Contact getById(String contactId) {
        return this.contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundExeception("Contact non trouvé avec cet ID " + contactId));
    }

}
