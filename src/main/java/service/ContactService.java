package service;

import model.Contact;

public class ContactService {

    public Contact getContact(String contact, Integer customerId){
        Contact fullContact = new Contact();
        fullContact.setCustomerId(customerId);
        fullContact.setContact(contact);
        fullContact.setType(getType(contact));

        return fullContact;
    }

    private Integer getType(String contact){
        String emailRegex ="[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)";
        String phoneRegex = "\\d{9}";
        if (contact.matches(emailRegex)){
            return 1;
        } else if (contact.replace(" ", "").matches(phoneRegex)){
            return 2;
        } else {
            return 0;
        }
    }
}
