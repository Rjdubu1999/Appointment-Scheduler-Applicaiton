package Data_Access_Object;

import Model.Contact;
import Utilities.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAO {
    public static ObservableList<Contact> getAllContacts() throws SQLException{
        ObservableList<Contact> contactObservableList = FXCollections.observableArrayList();
        String query = "SELECT * from contacts";
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            int contactID = resultSet.getInt("Contact_ID");
            String contactName = resultSet.getString("Contact_Name");
            String contactEmail = resultSet.getString("Email");
            Contact contact = new Contact(contactID, contactName, contactEmail);
            contactObservableList.add(contact);

        }
        return contactObservableList;
    }

    public static String locateContact(String contactID) throws SQLException{
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement("SELECT * FROM contacts WHERE Contact_Name = ?");
        preparedStatement.setString(1, contactID);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            contactID = resultSet.getString("Contact_ID");
        }
        return contactID;
    }
}
