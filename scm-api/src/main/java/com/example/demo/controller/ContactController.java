package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.config.AppConstant;
import com.example.demo.dto.AddContactForm;
import com.example.demo.dto.ContactSearchForm;
import com.example.demo.dto.UpdateContactForm;
import com.example.demo.entities.Contact;
import com.example.demo.entities.User;
import com.example.demo.services.ContactService;
import com.example.demo.services.UserService;
import com.example.demo.validators.GetEmailAuth;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Value("${app.url}")
    private String APP_URL;

    @Autowired
    ContactService contactService;

    @Autowired
    UserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // add contact GET
    @GetMapping("/")
    public String contactAddGet(Model model) {
        AddContactForm addContactForm = new AddContactForm();
        model.addAttribute("addContactForm", addContactForm);

        logger.info("Get /contactAddGet => nouveau contact demandé...");
        return "user/contact_add";
    }

    // add contact POST
    @PostMapping("/")
    public String contactAddPost(@Valid @ModelAttribute AddContactForm addContactForm, BindingResult bindingResult,
            Model model, Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "user/contact_add";
        }

        this.contactService.saveContact(addContactForm, authentication);
        logger.info("Post /contactAddPost => nouveau contact sauvegardé " + addContactForm.getEmail());
        return "redirect:/user/contacts/?contactSuccess";
    }

    // update contact get
    @GetMapping("/update/{contactId}")
    public String updateContactGet(
            @PathVariable("contactId") String contactId,
            Model model) {

        var contact = this.contactService.getById(contactId);
        UpdateContactForm updateContactForm = new UpdateContactForm();
        updateContactForm.setId(contact.getId());
        updateContactForm.setName(contact.getName());
        updateContactForm.setEmail(contact.getEmail());
        updateContactForm.setPhoneNumber(contact.getPhoneNumber());
        updateContactForm.setAddress(contact.getAddress());
        updateContactForm.setDescription(contact.getDescription());
        updateContactForm.setFavorite(contact.isFavorite());
        updateContactForm.setWebsiteLink(contact.getWebsiteLink());
        updateContactForm.setLinkedInLink(contact.getLinkedInLink());
        updateContactForm.setPicture(contact.getContactImage());

        model.addAttribute("updateContactForm", updateContactForm);
        model.addAttribute("contactId", contactId);
        model.addAttribute("contactUrl", contact.getContactImage());

        logger.info("Get /updateContactGet => contact modification demandé " + updateContactForm.getEmail());
        return "user/contact_update";
    }

    // update contact post
    @PostMapping("/update/{contactId}")
    public String updateContactPost(
            @Valid @ModelAttribute UpdateContactForm updateContactForm,
            BindingResult bindingResult,
            Model model,
            @PathVariable("contactId") String contactId) {

        if (bindingResult.hasErrors()) {
            return "user/contact_update";
        }

        this.contactService.updateContact(updateContactForm, contactId);
        logger.info("Post /updateContactPost => contact modification maj " + updateContactForm.getEmail());
        return "redirect:/user/contacts/list";
    }

    // detete contact
    @RequestMapping("/delete/{contactId}")
    public String contactDelete(@PathVariable("contactId") String contactId) {
        this.contactService.delete(contactId);

        logger.info("Get /contactDelete => contact suppression succès " + contactId);
        return "redirect:/user/contacts/list";
    }

    // list of contact
    @GetMapping("/list")
    public String contactList(
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstant.PAGE_SIZE + "") int size,
            Authentication authentication, Model model) {

        // load all the user contacts
        String username = GetEmailAuth.getEmailForAuth(authentication);
        User user = this.userService.getUserByEmail(username);

        Page<Contact> pageContact = this.contactService.getContactsByUser(user, page, size, sortBy, direction);
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstant.PAGE_SIZE);
        model.addAttribute("pageSearch", "false");
        model.addAttribute("contactSearchForm", new ContactSearchForm());
        model.addAttribute("app_url", APP_URL);

        logger.info("Get /contactList => listing contacts");
        return "user/contact_list";
    }

    // search contact
    @GetMapping("/search")
    public String contactSearch(
            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstant.PAGE_SIZE + "") int size,
            Authentication authentication, Model model) {

        // load all the user contacts
        String username = GetEmailAuth.getEmailForAuth(authentication);
        User user = this.userService.getUserByEmail(username);

        Page<Contact> pageContact = null;
        if (contactSearchForm.getFields().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getKeyword(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getFields().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getKeyword(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getFields().equalsIgnoreCase("phoneNumber")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getKeyword(), size, page, sortBy,
                    direction, user);
        }

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstant.PAGE_SIZE);
        model.addAttribute("pageSearch", "true");
        model.addAttribute("contactSearchForm", contactSearchForm);

        logger.info("Get contactSearch => List Search Contacts");
        return "user/contact_list";
    }

}
