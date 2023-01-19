package Controller;

import Data_Access_Object.AppointmentDAO;
import Data_Access_Object.ContactDAO;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.DataBaseAppointment;
import Utilities.DataBaseConnection;
import com.example.wilkinson_c195.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class MainAppointmentController  {

    @FXML private TextField AptIDField;
    @FXML private TextField UserIDField ;
    @FXML private TextField LocationField;
    @FXML private TextField TitleField;
    @FXML private TextField TypeField;
    @FXML private TextField DescriptionField;
    @FXML private TextField CustomerIDField;
    @FXML private TextField ContactField;
    @FXML private RadioButton AllAptRadio;
    @FXML private RadioButton WeeklyRadio;
    @FXML private RadioButton MonthlyRadio;
    @FXML private ComboBox<String> StartTimeCombo;
    @FXML private ComboBox<String> EndTimeCombo;
    @FXML private DatePicker StartDatePicker;
    @FXML private DatePicker EndDatePicker;
    @FXML private ComboBox<String> ContactCombo;
    @FXML private TableColumn<?,?> CustomerIDColumn;
    @FXML private TableColumn<?,?> AptIDColumn;
    @FXML private TableColumn<?,?> TitleColumn;
    @FXML private TableColumn<?,?> TypeColumn;
    @FXML private TableColumn<?,?> LocationColumn;
    @FXML private TableColumn<?,?> DescriptionCol;
    @FXML private TableColumn<?,?> StartCol;
    @FXML private TableColumn<?,?> EndColumn;
    @FXML private TableColumn<?,?> ContactIDColumn;
    @FXML private TableColumn<?,?> UserIDColumn;
    @FXML private
    TableView<Appointment> MainTableView;
    private boolean monthlyBool;
    private Appointment selectedAppointment;
    private Customer selectedCustomer;




    public void initialize() throws SQLException{
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointment();
        AptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        LocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        ContactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        StartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        EndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        UserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        MainTableView.setItems(allAppointments);

    }
    @FXML
    void loadAppointmentData(){
        try{
            DataBaseConnection.openConnection();
            Appointment selectedAppointment = MainTableView.getSelectionModel().getSelectedItem();

            if(selectedAppointment != null){
                ObservableList<Contact> contactObservableList = ContactDAO.getAllContacts();
                ObservableList<String> contactNameList = FXCollections.observableArrayList();
                String onScreenContactName = "";

                contactObservableList.forEach(contact -> contactNameList.add(contact.getContactName()));
                ContactCombo.setItems(contactNameList);
                for(Contact contact: contactObservableList){
                    if(selectedAppointment.getContactID() == contact.getContactID()){
                        onScreenContactName = contact.getContactName();
                    }
                }
                AptIDField.setText(String.valueOf(selectedAppointment.getAppointmentID()));
                TitleField.setText(selectedAppointment.getAppointmentTitle());
                LocationField.setText(selectedAppointment.getLocation());
                DescriptionField.setText(selectedAppointment.getDescription());
                TypeField.setText(selectedAppointment.getType());
                CustomerIDField.setText(String.valueOf(selectedAppointment.getCustomerID()));
                StartDatePicker.setValue(selectedAppointment.getStart().toLocalDate());
                EndDatePicker.setValue(selectedAppointment.getEnd().toLocalDate());
                StartTimeCombo.setValue(String.valueOf(selectedAppointment.getStart().toLocalTime()));
                EndTimeCombo.setValue(String.valueOf(selectedAppointment.getEnd().toLocalTime()));
                UserIDField.setText(String.valueOf(selectedAppointment.getUserID()));
                ContactCombo.setValue(onScreenContactName);

                ObservableList<String> timesOfAppointments = FXCollections.observableArrayList();

                LocalTime firstApt = LocalTime.MIN.plusHours(8);
                LocalTime lastApt = LocalTime.MAX.minusHours(1).minusMinutes(45);

                if(!firstApt.equals(0) || !lastApt.equals(0)){
                    while(firstApt.isBefore(lastApt)){
                        timesOfAppointments.add(String.valueOf(firstApt));
                        firstApt = firstApt.plusMinutes(15);
                    }
                }
                StartTimeCombo.setItems(timesOfAppointments);
                EndTimeCombo.setItems(timesOfAppointments);


                }
            }catch (Exception exception){
            exception.printStackTrace();
        }

        }

    public void onActionAddAppointment(ActionEvent actionEvent)throws IOException {
        Parent appointmentButton = FXMLLoader.load(Main.class.getResource("AddAppointment.fxml"));
        Scene scene = new Scene(appointmentButton);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
/**
    public void onActionModifyAppointment() {
        if(MonthlyTab.isSelected()) {
            if (MonthlyApptTableView.getSelectionModel().getSelectedItem() != null) {
                selectedAppointment = MonthlyApptTableView.getSelectionModel().getSelectedItem();
            } else {
                return;
            }
        }else{
            if(WeeklyTableView.getSelectionModel().getSelectedItem() != null){
                selectedAppointment = WeeklyTableView.getSelectionModel().getSelectedItem();
            }else {
                return;
            }

        }
        Dialog<ButtonType> dialog = new Dialog();
        dialog.initOwner(AppointmentAnchorMain.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ModifyAppointment.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());

        }catch (IOException ioException){
            System.out.println("Error Modifying Appointment : " + ioException.getMessage());
        }
        ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(save);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        ModifyAppointmentController controller = fxmlLoader.getController();
        controller.populateModifyFields(selectedCustomer.getCustomerName(),selectedAppointment);
        dialog.showAndWait().ifPresent((firstResponse -> {
            if(firstResponse == save){
                if(controller.handleModifyAppointment(selectedAppointment.getAppointmentId())){
                    MonthlyApptTableView.setItems(DataBaseAppointment.getMonthlyAppointments(selectedCustomer.getCustomerID()));
                    WeeklyTableView.setItems(DataBaseAppointment.getWeeklyAppointments(selectedCustomer.getCustomerID()));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Modifying Appointment");
                    alert.setContentText(controller.displayErrorMessages());
                    alert.showAndWait().ifPresent(secondResponse -> {
                        if(secondResponse == ButtonType.OK){
                            onActionModifyAppointment();
                        }
                    });
                }
            }
        }));
    }

   /** public void onActionDeleteAppointment() {
        if(MonthlyTab.isSelected()){
            monthlyBool = true;
            if(MonthlyApptTableView.getSelectionModel().getSelectedItem() != null){
                selectedAppointment = MonthlyApptTableView.getSelectionModel().getSelectedItem();
            }else {
                return;
            }
        }else {
            monthlyBool = false;
            if(WeeklyTableView.getSelectionModel().getSelectedItem() != null){
            selectedAppointment = WeeklyTableView.getSelectionModel().getSelectedItem();
        }else {
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Appointment");
        alert.setContentText("Would you like to delete this appointment?");
        alert.showAndWait().ifPresent((response -> {
            if(response == ButtonType.OK){
                DataBaseAppointment.deleteAppointment(selectedAppointment.getAppointmentId());
                if(monthlyBool){
                    MonthlyApptTableView.setItems(DataBaseAppointment.getMonthlyAppointments(selectedCustomer.getCustomerID()));

                }else {
                    WeeklyTableView.setItems(DataBaseAppointment.getWeeklyAppointments(selectedCustomer.getCustomerID()));
                }
            }
        }
                ));
    }

    @FXML
    public void onActionBack(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    } **/
}

    public void onActionModifyAppointment(ActionEvent actionEvent) {
    }

    public void onActionDeleteAppointment(ActionEvent actionEvent) {
    }

    public void onActionBack(ActionEvent actionEvent) {
    }
}
