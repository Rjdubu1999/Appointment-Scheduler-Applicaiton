/**package Model;

import Utilities.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseCustomer {
    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    public static Customer getCustomer(int id) {
        try {
            Statement statement = DataBaseConnection.getConnection().createStatement();
            String query = "SELECT * FROM customers WHERE Customer_ID='" + id + "'";
            ResultSet resultset = statement.executeQuery(query);
            if (resultset.next()) {
                Customer customer = new Customer();
                customer.setCustomerName(resultset.getString("customerName"));
                statement.close();
                return customer;
            }
        } catch (SQLException sqlException) {
            System.out.println("Exception: " + sqlException.getMessage());
        }
        return null;
    }

    public static ObservableList<Customer> getCustomerList() {
        customerList.clear();
        try {
            Statement statement = DataBaseConnection.getConnection().createStatement();
            String query = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Phone, customers.Postal_Code, first_level_divisions.Divisions FROM customers INNER JOIN first_level_division ON customers.Division_ID = first_level_divisions_Division_ID  ";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt("customerId"), resultSet.getString("customerName"), resultSet.getString("address"), resultSet.getString("city"), resultSet.getString("phone"), resultSet.getString("postalCode"));
                customerList.add(customer);
            }
            statement.close();
            return customerList;
        } catch (SQLException sqlException) {
            System.out.println("Exception: " + sqlException.getMessage());
            return null;
        }
    }


    public static boolean saveCustomer(String name, String address, int cityId, String zip, String phone) {
        try {
            Statement statement = DataBaseConnection.getConnection().createStatement();
            String firstQuery = "INSERT INTO address SET address ='" + address + "', phone='" + phone + "', postalCode='" + zip + "', cityId='" + cityId;
            int updateFirst = statement.executeUpdate(firstQuery);
            if (updateFirst == 1) {
                int addressId = customerList.size() + 1;
                String secondQuery = "INSERT INTO customer SET customerName'" + name + "', addressId='" + addressId;
                int updateSecond = statement.executeUpdate(secondQuery);
                if (updateSecond == 1) {
                    return true;
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Exception: " + sqlException.getMessage());
        }
        return false;
    }

    public static boolean updateCustomer(int id, String name, String address, int cityId, String zip, String phone) {
        try {
            Statement statement = DataBaseConnection.getConnection().createStatement();
            String firstQuery = "UPDATE customers SET address='" + address + "', cityId=" + cityId + ", postalCode='" + zip + "', phone='" + phone + "'" + "WHERE addressId='" + id;
            int updateFirst = statement.executeUpdate(firstQuery);
            if (updateFirst == 1) {
                String secondQuery = "UPDATE customer SET customerName='" + name + "', addressId='" + id + "'WHERE customerId=" + id;
                int updateSecond = statement.executeUpdate(secondQuery);
                if (updateSecond == 1) {
                    return true;
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Exception: " + sqlException.getMessage());
        }
        return false;
    }

    public static boolean deleteCustomer(int id){
        try{
            Statement statement = DataBaseConnection.getConnection().createStatement();
            String firstQuery = "DELETE FROM customer WHERE addressId=" + id;
            int updateFirst = statement.executeUpdate(firstQuery);
            if (updateFirst == 1){
                String secondQuery = "DELETE FROM customerId= "+ id;
                int updateSecond = statement.executeUpdate(secondQuery);
                if(updateSecond == 1){
                    return true;
                }
            }
        }catch (SQLException sqlException){
            System.out.println("Exception: " + sqlException.getMessage());
        }
        return false;
    }

}
**/