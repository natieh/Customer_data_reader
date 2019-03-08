package service;

import model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactDBService {

    public boolean saveContact(Contact contact, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO CONTACTS (customer_id, type, contact) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, contact.getCustomerId());
            ps.setInt(2, contact.getType());
            ps.setString(3, contact.getContact());
            int i = ps.executeUpdate();

            if (i == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
