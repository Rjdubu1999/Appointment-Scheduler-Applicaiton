package Data_Access_Object;

import Model.Appointment;
import Model.DatabaseUser;
import Utilities.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @Author Ryan Wilkinson
 */

/**
 * This class creates a data access object for the appointment data in the MySQL database
 */
public class AppointmentDAO {


    /**
     * @return This method gets all of the appoints information from the MySQL Database
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllAppointment()throws SQLException{
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        String query = "SELECT * from appointments";
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int appointmentID = resultSet.getInt("Appointment_ID");
            String appointmentTitle = resultSet.getString("Title");
            String appointmentDescription = resultSet.getString("Description");
            String appointmentLocation = resultSet.getString("Location");
            String appointmentType = resultSet.getString("Type");
            LocalDateTime start =resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end =resultSet.getTimestamp("End").toLocalDateTime();
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");
            int customerID = resultSet.getInt("Customer_ID");
            Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription,appointmentLocation, appointmentType, start,end, userID,contactID,customerID);
            appointmentObservableList.add(appointment);
        }
        return appointmentObservableList;
    }

    /**
     * @param customer This method will allow a user to delete an appoint in the MysQL database
     * @param connection
     * @return
     * @throws SQLException
     */
    public static int deleteAppointment(int customer, Connection connection) throws SQLException{
        String query = "DELETE FROM appointments WHERE Appointment_ID =?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, customer);
        int delete = preparedStatement.executeUpdate();
        preparedStatement.close();
        return delete;
    }


    }

