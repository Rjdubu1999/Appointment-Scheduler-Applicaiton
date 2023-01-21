package Data_Access_Object;

import Model.Appointment;
import Model.Report;
import Utilities.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReportDAO extends Appointment {
    public ReportDAO(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, customerID, userID, contactID);
    }

    public static ObservableList<Report> getCustomer() throws SQLException{
        ObservableList<Report> customerObservableList = FXCollections.observableArrayList();
        String query = "SELECT customers.Customer_Name, count(*) as customerCount FROM customers ORDER BY count(*) desc";
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            String customerName = resultSet.getString("customerName");
            int customerCount = resultSet.getInt("customerCount");
            Report report = new Report(customerName, customerCount);
            customerObservableList.add(report);


        }
        return customerObservableList;
    }
}
