package service;

import model.Customer;

import java.sql.*;

public class CustomerDBService {

    public int insertCustomer(Customer customer, Connection connection) {
        try {
            PreparedStatement ps = getPreparedStatement(customer, connection);
            int i = ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            Integer id = 0;
            if (keys.next()) {
                id = keys.getInt(1);
            }

            if (i == 1) {
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private PreparedStatement getPreparedStatement(Customer customer, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO customers (name, surname, age, city) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getSurname());
        if (customer.getAge() != null) {
            ps.setInt(3, customer.getAge());
        } else {
            ps.setNull(3, 0);
        }
        ps.setString(4, customer.getCity());
        return ps;
    }
}
