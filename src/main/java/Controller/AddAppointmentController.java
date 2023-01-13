package Controller;

import Model.Customer;
import Model.DataBaseAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML private ComboBox ContactCombo;
    @FXML private
    Label currentTime;
    @FXML private Label NYLabel;
    @FXML private Label PhoenixLabel;
    @FXML private Label LondonLabel;
    @FXML private TextField CustomerNameField;
    @FXML private TextField ContactField;
    @FXML private TextField LocationField;
    @FXML private DatePicker DateField;
    @FXML private ComboBox TimeComboBox;
    @FXML private ComboBox ApTypeCombo;


    private Customer customer;
    private final ObservableList<String> appointmentTimes = FXCollections.observableArrayList("9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00");
    private final ObservableList<String> appointmentTypes = FXCollections.observableArrayList(
            "Insurance Quote", "Prescription List Check", "Plan Change", "Medicare Questions", "Life Insurance");

    private final ObservableList<String> contacts = FXCollections.observableArrayList("Jim", "Jody", "Chris");
    private ObservableList<String> errorTypes = FXCollections.observableArrayList();
    public boolean contactValidator(int appointmentContact){
        if(appointmentContact == -1){
            errorTypes.add("Please select a contact");
            return false;
        }
        return true;
    }
    public boolean typeValidator(int appointmentType){
        if(appointmentType == -1){
            errorTypes.add("Please select an appointment");
            return false;
        }
        else{
            return true;
        }
    }
    public boolean timeValidator(int appointmentTime){
        if(appointmentTime == -1){
            errorTypes.add("Please select and appointment time");
            return false;
        }else return true;
    }
    public boolean dateValidator(LocalDate appointmentDate){
        if(appointmentDate == null){
            errorTypes.add("Please select an appointment date");
            return false;
        }else return true;
    }
    public String displayErrorMessages(){
        String string = "";
        if(errorTypes.size() > 0){
            for(String error : errorTypes){
                string = string.concat(error);
            }
            return string;
        }else {
            string = "Database Error";
            return string;
        }
    }
    public boolean handleAddAppointment(int id){
        errorTypes.clear();
        int appointmentContact = ContactCombo.getSelectionModel().getSelectedIndex();
        int appointmentType = ApTypeCombo.getSelectionModel().getSelectedIndex();
        int appointmentTime = TimeComboBox.getSelectionModel().getSelectedIndex();
        LocalDate localDate = DateField.getValue();
        if(!contactValidator(appointmentContact) || !typeValidator(appointmentType) || !timeValidator(appointmentTime) || dateValidator(localDate)){
            return false;
        }
        if(DataBaseAppointment.appointmentsOverlap(-1, LocationField.getText(), localDate.toString(), appointmentTimes.get(appointmentTime))){
            errorTypes.add("Appointments Overlap");
            return false;
        }
        if(DataBaseAppointment.saveAppointment(id, appointmentTypes.get(appointmentType), contacts.get(appointmentContact), LocationField.getText(), localDate.toString(), appointmentTimes.get(appointmentTime))){
            return true;
        }else {
            errorTypes.add("Database Error");
            return false;
        }
    }



    public void populateCustomerNameColumn(String name){
        CustomerNameField.setText(name);
    }

    @FXML
    public void handleLocation(){
        String string = ContactCombo.getSelectionModel().getSelectedItem().toString();
        if(string.equals("Jim")){
            LocationField.setText("New York");
        }else  if(string.equals("Jodi")) {
            LocationField.setText("Phoenix");
        } else if(string.equals("Chris")){
            LocationField.setText("London");
        }
    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContactCombo.setItems(contacts);
        TimeComboBox.setItems(appointmentTimes);
        ApTypeCombo.setItems(appointmentTypes);
        DateField.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate DateField, boolean none) {
                super.updateItem(DateField, none);
                setDisable(none || DateField.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        DateField.getDayOfWeek() == DayOfWeek.SUNDAY ||
                        DateField.isBefore(LocalDate.now()));
                if (DateField.getDayOfWeek() == DayOfWeek.SATURDAY
                        || DateField.getDayOfWeek() == DayOfWeek.SUNDAY ||
                        DateField.isBefore(LocalDate.now())) {
                    setStyle("-fx-background-color: #fa0400");
                }
            }

        });
    }
}
