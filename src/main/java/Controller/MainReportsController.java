package Controller;

import Data_Access_Object.AppointmentDAO;
import Data_Access_Object.ContactDAO;
import Data_Access_Object.ReportDAO;
import Model.*;
import Utilities.DataBaseConnection;
import com.example.wilkinson_c195.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Collections;
import java.time.Month;

/**
 * Creating a Report Class Controller that will hold the information which summarises the data in the tables
 * and database throughout the program and will store it into various tables that are held in multiple tabs
 */

public class MainReportsController {


    @FXML private Tab FillScheduleTab;
    @FXML private
    TableView<ReportByDates> MonthlyTotalsTableView;
    @FXML private TableColumn<?,?> MonthlyAptColumn;
    @FXML private TableColumn<?,?> MonthlyTotalColumn;
    @FXML private TableView<ReportByType> TypeAptTableView;
    @FXML private TableColumn<?,?> TypeTotalTotalColumn;
    @FXML private TableColumn<?,?> TypeTotalCol;
    @FXML private TableView<Appointment> ScheduleTableView;
    @FXML private TableColumn<?,?> AptIDColumn;
    @FXML private TableColumn<?,?> TitleColumn;
    @FXML private TableColumn<?,?> LocationColumn;
    @FXML private TableColumn<?,?> DescriptionColumn;
    @FXML private TableColumn<?,?> TypeColumn;
    @FXML private TableColumn<?,?> StartColumn;
    @FXML private TableColumn<?,?> EndColumn;
    @FXML private ComboBox<String> ScheduleContactsCombo;
    @FXML private TableView<Report> CustomerAptTableView;
    @FXML private TableColumn<?,?> CustomerNameTotal;
    @FXML private TableColumn<?,?> CustomerTotalCol;
    @FXML private TableColumn<?,?> CustomerID;
    @FXML private TableColumn<?,?> ContactID;
    @FXML private TableColumn<?,?> UserID;

    /**
     * @param actionEvent Returns the user to the mainscreen
     * @throws IOException
     */
    public void onActionReturnToMain(ActionEvent actionEvent) throws IOException {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }


    /**
     * @throws SQLException initializes the class and loads the data into the tables for each tab
     */
    public void initialize() throws SQLException{

            CustomerNameTotal.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
            CustomerTotalCol.setCellValueFactory(new PropertyValueFactory<>("divisionTotal"));
            MonthlyAptColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentByMonth"));
            MonthlyTotalColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));
            TypeTotalCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            TypeTotalTotalColumn.setCellValueFactory(new PropertyValueFactory<>("totalAppointments"));
            AptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            LocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            TitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            TypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            StartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            EndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
            CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            ContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            UserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

            ObservableList<Contact> allContacts = ContactDAO.getAllContacts();
            ObservableList<String> contactName = FXCollections.observableArrayList();
            allContacts.forEach(contact -> contactName.add(contact.getContactName()));
            ScheduleContactsCombo.setItems(contactName);


    }

    /**
     * This method takes the data for each individual contact and is used in the combo box to fill the table
     * with the individual contacts appointments
     */
    @FXML
    void onActionFillCombo(){
        try{
            int contactID = 0;
            ObservableList<Appointment> getAppointment = AppointmentDAO.getAllAppointment();
            ObservableList<Appointment> appointmentData = FXCollections.observableArrayList();
            ObservableList<Contact> allContactList = ContactDAO.getAllContacts();
            String contactCombo = ScheduleContactsCombo.getSelectionModel().getSelectedItem();
            Appointment contactsAppointments;
            for(Contact contact: allContactList){
                if(contactCombo.equals(contact.getContactName())){
                    contactID = contact.getContactID();
                }
            }
            for(Appointment appointment: getAppointment){
                if(appointment.getContactID() == contactID){
                    contactsAppointments = appointment;
                    appointmentData.add(contactsAppointments);
                }
            }

            ScheduleTableView.setItems(appointmentData);
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }


    /**
     * @throws SQLException Loads the report data regarding the divisions and the numbers of them into each table
     */
    public void CustomerDataLoad() throws SQLException {
    try{
        ObservableList<Report> divisions = ReportDAO.getDivision();
        ObservableList<Report> divisionsToAdd = FXCollections.observableArrayList();

        divisions.forEach(divisionsToAdd::add);
        CustomerAptTableView.setItems(divisionsToAdd);
    }catch (Exception exception){
        exception.printStackTrace();
    }

}

    /**
     * @param event This method loads the report information for the month table and the appointment type table
     *              and the numbers each category has
     * @throws SQLException
     */
    public void AppointmentReportTab(Event event) throws SQLException {
        try {
            ObservableList<Appointment> getAllAppointments = AppointmentDAO.getAllAppointment();
            ObservableList<Month> aptMonthList = FXCollections.observableArrayList();
            ObservableList<Month> monthofApts = FXCollections.observableArrayList();
            ObservableList<String> appointmentType = FXCollections.observableArrayList();
            ObservableList<String> Appointment = FXCollections.observableArrayList();
            ObservableList<ReportByType> Type = FXCollections.observableArrayList();
            ObservableList<ReportByDates> Months = FXCollections.observableArrayList();

            getAllAppointments.forEach(appointments -> {
                appointmentType.add(appointments.getAppointmentType());
            });

            getAllAppointments.stream().map(appointment -> appointment.getStart().getMonth()).forEach(aptMonthList::add);

            aptMonthList.stream().filter(month -> !monthofApts.contains(month)).forEach(monthofApts::add);

            for (Appointment appointments: getAllAppointments) {
                String appointmentsAppointmentType = appointments.getAppointmentType();
                if (!Appointment.contains(appointmentsAppointmentType)) {
                    Appointment.add(appointmentsAppointmentType);
                }
            }

            for (Month month: monthofApts) {
                int totalMonth = Collections.frequency(aptMonthList, month);
                String monthName = month.name();
                ReportByDates appointmentMonth = new ReportByDates(monthName, totalMonth);
                Months.add(appointmentMonth);
            }
            MonthlyTotalsTableView.setItems(Months);

            for (String type: Appointment) {
                String typeToSet = type;
                int typeTotal = Collections.frequency(appointmentType, type);
                ReportByType appointmentTypes = new ReportByType(typeToSet, typeTotal);
                Type.add(appointmentTypes);
            }
            TypeAptTableView.setItems(Type);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


