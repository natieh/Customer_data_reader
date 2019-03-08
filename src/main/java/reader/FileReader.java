package reader;

import com.opencsv.CSVReader;
import database.BDConnectionProvider;
import model.Contact;
import model.Customer;
import service.ContactDBService;
import service.CustomerDBService;
import properties.ApplicationProperties;
import service.ContactService;

import java.io.*;
import java.sql.Connection;

public class FileReader {

    private Connection connection;

    public FileReader() {
        BDConnectionProvider connectionFactory = new BDConnectionProvider();
        connection = connectionFactory.getConnection();
    }

    public void readFiles() {
        ApplicationProperties properties = new ApplicationProperties();

        try {
            File folder = new File(properties.getPath());
            File[] fileNames = folder.listFiles();

            if (fileNames != null) {
                for (File file : fileNames) {
                    if (file.getName().contains("csv") || file.getName().contains("txt")) {
                        processFile(file);
                    }
                }
            } else {
                System.out.println("In directory is no file for reading");
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processFile(File file) throws Exception {
        InputStream stream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(stream);
        CSVReader csvReader = new CSVReader(reader);
        readContent(csvReader);
    }

    private void readContent(CSVReader csvReader) throws Exception {
        String[] data;
        CustomerDBService customerDBService = new CustomerDBService();
        while ((data = csvReader.readNext()) != null) {
            Customer customer = readCustomer(data);
            Integer customerID = customerDBService.insertCustomer(customer, connection);

            ContactService contactService = new ContactService();
            readContact(data, customerID, contactService);
        }
    }

    private void readContact(String[] data, Integer customerID, ContactService contactService) {
        int length = data.length;
        for (int i = 4; i < length; ++i) {
            Contact contact = contactService.getContact(data[i], customerID);
            ContactDBService contactDBService = new ContactDBService();
            contactDBService.saveContact(contact, connection);
        }
    }

    private Customer readCustomer(String[] data) {
        Customer customer = new Customer();
        customer.setName(data[0]);
        customer.setSurname(data[1]);
        if (!data[2].isEmpty()) {
            customer.setAge(Integer.parseInt(data[2]));
        }
        customer.setCity(data[3]);
        return customer;
    }

}
