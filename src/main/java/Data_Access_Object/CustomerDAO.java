package Data_Access_Object;

import Model.Customer;
import Utilities.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class CustomerDAO {

    public static ObservableList<Customer> getAllCustomers(Connection connection)throws SQLException{
        String query = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division from customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        while(resultSet.next()){
            int customerID = resultSet.getInt("Customer_ID");
            String customerName = resultSet.getString("Customer_Name");
            String address = resultSet.getString("Address");
            String postalCode = resultSet.getString("Postal_Code");
            String phone = resultSet.getString("Phone");
            int divisionID = resultSet.getInt("Division_ID");
            String divisionName = resultSet.getString("Division");
            Customer customer = new Customer(customerID, customerName,address,postalCode,phone,divisionID,divisionName);
            customerObservableList.add(customer);
        }
        return customerObservableList;
    }


    }

