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

/**
 * @Author Ryan Wilkinson
 */

/**
 * Creating a data access object for the report model class that will get all the data from the
 * appointments table in the MYSQL database
 */
public class ReportDAO extends Appointment  {

    /**
     * creating a super constructor for the DAO class
     * @param appointmentID
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentType
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     */
    public ReportDAO(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, customerID, userID, contactID);
    }

    /**
     * @return selects the information from the first level division and inner joins it with the customers table to get meaningful data
     * to put into the report controller screen which displays the first level divisions in the program and how
     * many times it appears throughout the program
     * @throws SQLException
     */
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
