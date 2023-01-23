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

public class ReportDAO extends Appointment  {

    public ReportDAO(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, customerID, userID, contactID);
    }

    public static ObservableList<Report> getDivision() throws SQLException{
        ObservableList<Report> divisionList = FXCollections.observableArrayList();
        String query = "SELECT first_level_divisions.Division, COUNT(*) AS divisionTotal FROM customers INNER JOIN first_level_divisions ON customers.Division_ID WHERE first_level_divisions.Division_ID = customers.Division_ID GROUP BY first_level_divisions.Division_ID ORDER BY COUNT(*) DESC ";
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            String division  = resultSet.getString("Division");
            int totalDivision = resultSet.getInt("divisionTotal");
            Report report = new Report(totalDivision, division);
            divisionList.add(report);


        }
        return divisionList;
    }
}
