package Controller;

import Data_Access_Object.AppointmentDAO;
import Data_Access_Object.ContactDAO;
import Data_Access_Object.ReportDAO;
import Model.Appointment;
import Model.Contact;
import Model.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainReportsController {


    @FXML private
    TableView MonthlyTotalsTableView;
    @FXML private TableColumn<?,?> MonthlyAptColumn;
    @FXML private TableColumn<?,?> MonthlyTotalColumn;
    @FXML private TableView<?> TypeAptTableView;
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

    public void onActionReturnToMain(ActionEvent actionEvent) {
    }


    public void initialize() throws SQLException{

            CustomerNameTotal.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            CustomerTotalCol.setCellValueFactory(new PropertyValueFactory<>("customerCount"));
            MonthlyAptColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentMonth"));
            MonthlyTotalColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));
            TypeTotalCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            TypeTotalTotalColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));
            AptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            LocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            TitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            TypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            StartColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
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


    public void CustomerDataLoad(Event event) throws SQLException {
        try{
            ObservableList<Report> allCustomers = ReportDAO.getCustomer();
            ObservableList<Report> customersToAdd = FXCollections.observableArrayList();
            allCustomers.forEach(customersToAdd::add);
            CustomerAptTableView.setItems(customersToAdd);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
