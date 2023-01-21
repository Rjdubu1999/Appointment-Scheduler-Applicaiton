package Controller;

import Data_Access_Object.AppointmentDAO;
import Data_Access_Object.ContactDAO;
import Data_Access_Object.CustomerDAO;
import Data_Access_Object.USERDAO;
import Model.*;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import static Utilities.Time.convertTOUTC;
public class MainAppointmentController {

    @FXML
    private TextField AptIDField;
    @FXML
    private TextField UserIDField;
    @FXML
    private TextField LocationField;
    @FXML
    private TextField TitleField;
    @FXML
    private TextField TypeField;
    @FXML
    private TextField DescriptionField;
    @FXML
    private TextField CustomerIDField;
    @FXML
    private TextField ContactField;
    @FXML
    private RadioButton AllAptRadio;
    @FXML
    private RadioButton WeeklyRadio;
    @FXML
    private RadioButton MonthlyRadio;
    @FXML
    private ComboBox<String> StartTimeCombo;
    @FXML
    private ComboBox<String> EndTimeCombo;
    @FXML
    private DatePicker StartDatePicker;
    @FXML
    private DatePicker EndDatePicker;
    @FXML
    private ComboBox<String> ContactCombo;
    @FXML
    private TableColumn<?, ?> CustomerIDColumn;
    @FXML
    private TableColumn<?, ?> AptIDColumn;
    @FXML
    private TableColumn<?, ?> TitleColumn;
    @FXML
    private TableColumn<?, ?> TypeColumn;
    @FXML
    private TableColumn<?, ?> LocationColumn;
    @FXML
    private TableColumn<?, ?> DescriptionCol;
    @FXML
    private TableColumn<?, ?> StartCol;
    @FXML
    private TableColumn<?, ?> EndColumn;
    @FXML
    private TableColumn<?, ?> ContactIDColumn;
    @FXML
    private TableColumn<?, ?> UserIDColumn;
    @FXML
    private
    TableView<Appointment> MainTableView;
    private boolean monthlyBool;
    private Appointment selectedAppointment;
    private Customer selectedCustomer;


    public void initialize() throws SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointment();
        AptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        LocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        StartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        EndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        ContactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        UserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        MainTableView.setItems(allAppointments);
        AllAptRadio.setSelected(true);
        WeeklyRadio.setSelected(false);
        MonthlyRadio.setSelected(false);

    }

    @FXML
    void MouseClickedLoadAptData() {
        try {
            DataBaseConnection.openConnection();
            Appointment selectedAppointment = MainTableView.getSelectionModel().getSelectedItem();

            if (selectedAppointment != null) {
                ObservableList<Contact> contactObservableList = ContactDAO.getAllContacts();
                ObservableList<String> contactNameList = FXCollections.observableArrayList();
                String onScreenContactName = "";

                contactObservableList.forEach(contact -> contactNameList.add(contact.getContactName()));
                ContactCombo.setItems(contactNameList);
                for (Contact contact : contactObservableList) {
                    if (selectedAppointment.getContactID() == contact.getContactID()) {
                        onScreenContactName = contact.getContactName();
                    }
                }
                AptIDField.setText(String.valueOf(selectedAppointment.getAppointmentID()));
                TitleField.setText(selectedAppointment.getAppointmentTitle());
                LocationField.setText(selectedAppointment.getAppointmentLocation());
                DescriptionField.setText(selectedAppointment.getAppointmentDescription());
                TypeField.setText(selectedAppointment.getAppointmentType());
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
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void onActionAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent appointmentButton = FXMLLoader.load(Main.class.getResource("AddAppointment.fxml"));
        Scene scene = new Scene(appointmentButton);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    public void onActionBack(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }




    public void onActionDeleteAppointment(ActionEvent actionEvent) throws Exception {
        try {
            Connection connection = DataBaseConnection.openConnection();
            int deleteAptId = MainTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            String deleteAptType = MainTableView.getSelectionModel().getSelectedItem().getAppointmentType();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Delete The Selected Appointment?");
            Optional<ButtonType> confirm = alert.showAndWait();
            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                AppointmentDAO.deleteAppointment(deleteAptId, connection);
                ObservableList<Appointment> appointmentObservableList = AppointmentDAO.getAllAppointment();
                MainTableView.setItems(appointmentObservableList);
                System.out.println("Appointment Successfully Deleted.");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void onActionModifyAppointment(ActionEvent event) {
        try {

            Connection connection = DataBaseConnection.openConnection();

            if (!TitleField.getText().isEmpty() && !DescriptionField.getText().isEmpty() && !LocationField.getText().isEmpty() && !TypeField.getText().isEmpty() && StartDatePicker.getValue() != null && EndDatePicker.getValue() != null && !StartTimeCombo.getValue().isEmpty() && !EndTimeCombo.getValue().isEmpty() && !CustomerIDField.getText().isEmpty()) {
                ObservableList<Customer> getAllCustomers = CustomerDAO.getAllCustomers(connection);
                ObservableList<Integer> storeCustomerIDs = FXCollections.observableArrayList();
                ObservableList<USERDAO> getAllUsers = USERDAO.getAllUser();
                ObservableList<Integer> storeUserIDs = FXCollections.observableArrayList();
                ObservableList<Appointment> getAllAppointments = AppointmentDAO.getAllAppointment();
                getAllCustomers.stream().map(Customer::getCustomerID).forEach(storeCustomerIDs::add);
                getAllUsers.stream().map(User::getUserID).forEach(storeUserIDs::add);
                LocalDate ldStart = StartDatePicker.getValue();
                LocalDate ldEnd = EndDatePicker.getValue();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime ltStart = LocalTime.parse(StartTimeCombo.getValue(), dateTimeFormatter);
                LocalTime ltEnd = LocalTime.parse(EndTimeCombo.getValue(), dateTimeFormatter);
                LocalDateTime dtStart = LocalDateTime.of(ldStart, ltStart);
                LocalDateTime dtEnd = LocalDateTime.of(ldEnd, ltEnd);
                ZonedDateTime zdtStart = ZonedDateTime.of(dtStart, ZoneId.systemDefault());
                ZonedDateTime zdtEnd = ZonedDateTime.of(dtEnd, ZoneId.systemDefault());
                ZonedDateTime estConversionStart = zdtStart.withZoneSameInstant(ZoneId.of("America/New_York"));
                ZonedDateTime estConversionEnd = zdtEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

                if (estConversionStart.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SATURDAY.getValue()) || estConversionStart.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SUNDAY.getValue()) || estConversionEnd.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SATURDAY.getValue()) || estConversionEnd.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SUNDAY.getValue())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "This day is outside of our (Monday-Friday) business operation days. ");
                    Optional<ButtonType> error = alert.showAndWait();
                    System.out.println("The selected day is outside of operating days");
                    return;
                }

                if (estConversionStart.toLocalTime().isBefore(LocalTime.of(8, 0, 0)) || estConversionStart.toLocalTime().isAfter(LocalTime.of(22, 0, 0)) || estConversionEnd.toLocalTime().isBefore(LocalTime.of(8, 0, 0)) || estConversionEnd.toLocalTime().isAfter(LocalTime.of(22, 0, 0))) {
                    System.out.println("time is outside of business hours");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The selected appointment time is not within our business hours (8AM-10PM EST) " + estConversionStart.toLocalTime() + " - " + estConversionEnd.toLocalTime() + " EST");
                    Optional<ButtonType> error = alert.showAndWait();
                    return;
                }

                int newCustomerID = Integer.parseInt(CustomerIDField.getText());
                int appointmentID = Integer.parseInt(AptIDField.getText());


                if (dtStart.isAfter(dtEnd)) {
                    System.out.println("Check Time: Appointment has a start time before the end of appointment, check times.");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment start time is after end time");
                    Optional<ButtonType> ERROR = alert.showAndWait();
                    return;
                }

                if (dtStart.isEqual(dtEnd)) {
                    System.out.println("Check Time: Appointment can not have same start and end time");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment has same start and end time");
                    Optional<ButtonType> error = alert.showAndWait();
                    return;
                }
                for (Appointment appointment : getAllAppointments) {
                    LocalDateTime checkStart = appointment.getStart();
                    LocalDateTime checkEnd = appointment.getEnd();
                    if ((newCustomerID == appointment.getCustomerID()) && (appointmentID != appointment.getAppointmentID()) &&
                            (dtStart.isBefore(checkStart)) && (dtEnd.isAfter(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "This appointment overlaps the time of a difference existing appointments");
                        Optional<ButtonType> error = alert.showAndWait();
                        System.out.println("Check Appointment: Appointment overlaps with existing appointment.");
                        return;
                    }
                    if ((newCustomerID == appointment.getCustomerID()) && (appointmentID != appointment.getAppointmentID()) && (dtStart.isAfter(checkStart)) && (dtEnd.isBefore(checkEnd)))
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Start time overlaps with existing appointment.");
                        Optional<ButtonType> error = alert.showAndWait();
                        System.out.println("Check Appointment: Start time overlaps with existing appointment.");
                        return;
                    }
                    if (newCustomerID == appointment.getCustomerID() && (appointmentID != appointment.getAppointmentID()) &&
                            (dtEnd.isAfter(checkStart)) && (dtEnd.isBefore(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Overlap in Appointments");
                        Optional<ButtonType> error = alert.showAndWait();
                        System.out.println("Check Appointment: End of one appointment overlaps with a different existing appointment. Check Times.");
                        return;
                    }
                }
                String startDateFormat = StartDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String startTimeGet = StartTimeCombo.getValue();
                String endDateFormat = EndDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String endTimeGet = EndTimeCombo.getValue();
                String utcConversionStart = convertTOUTC(startDateFormat + " " + startTimeGet + ":00");
                String utcConversionEnd = convertTOUTC(endDateFormat + " " + endTimeGet + ":00");

                String query = "UPDATE appointments SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
                DataBaseConnection.setPreparedStatement(DataBaseConnection.getConnection(), query);
                PreparedStatement preparedStatement = DataBaseConnection.getPreparedStatement();
                preparedStatement.setInt(1, Integer.parseInt(AptIDField.getText()));
                preparedStatement.setString(2, TitleField.getText());
                preparedStatement.setString(3, DescriptionField.getText());
                preparedStatement.setString(4, LocationField.getText());
                preparedStatement.setString(5, TypeField.getText());
                preparedStatement.setString(6, utcConversionStart);
                preparedStatement.setString(7, utcConversionEnd);
                preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(9, "admin");
                preparedStatement.setInt(10, Integer.parseInt(CustomerIDField.getText()));
                preparedStatement.setInt(11, Integer.parseInt(UserIDField.getText()));
                preparedStatement.setInt(12, Integer.parseInt(ContactDAO.locateContact(ContactCombo.getValue())));
                preparedStatement.setInt(13, Integer.parseInt(AptIDField.getText()));
                preparedStatement.execute();

                ObservableList<Appointment> allAppointmentsList = AppointmentDAO.getAllAppointment();
                MainTableView.setItems(allAppointmentsList);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void OnClickRadioAllApts(MouseEvent mouseEvent) throws SQLException {
        try {
            ObservableList<Appointment> appointmentObservableList = AppointmentDAO.getAllAppointment();
            if (appointmentObservableList != null)
                for (Appointment appointment : appointmentObservableList) {
                    MainTableView.setItems(appointmentObservableList);
                }
            if(AllAptRadio.isSelected()){
                WeeklyRadio.setSelected(false);
                MonthlyRadio.setSelected(false);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void OnClickRadioWeeklyApts(MouseEvent mouseEvent) throws SQLException {
        try {
            ObservableList<Appointment> appointmentObservableList = AppointmentDAO.getAllAppointment();
            ObservableList<Appointment> weeklyAppointment = FXCollections.observableArrayList();
            LocalDateTime startOfWeek = LocalDateTime.now().minusWeeks(1);
            LocalDateTime endOfWeek = LocalDateTime.now().plusWeeks(1);
            if (appointmentObservableList != null)
                appointmentObservableList.forEach(appointment -> {
                    if (appointment.getEnd().isAfter(startOfWeek) && appointment.getEnd().isBefore(endOfWeek)) {
                        weeklyAppointment.add(appointment);
                    }
                    MainTableView.setItems(weeklyAppointment);
                });
            if(WeeklyRadio.isSelected()){
                AllAptRadio.setSelected(false);
                MonthlyRadio.setSelected(false);
            }

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


    public void OnMonthlyRadioAptsClicked(MouseEvent mouseEvent) throws SQLException {
        try{
            ObservableList<Appointment> appointmentObservableList = AppointmentDAO.getAllAppointment();
            ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
            LocalDateTime monthStart = LocalDateTime.now().minusMonths(1);
            LocalDateTime monthEnd = LocalDateTime.now().plusMonths(1);
            if(appointmentObservableList != null){
                appointmentObservableList.forEach(appointment -> {
                    if(appointment.getEnd().isAfter(monthStart) && appointment.getEnd().isBefore(monthEnd)){
                        monthlyAppointments.add(appointment);
                    }
                    MainTableView.setItems(monthlyAppointments);
                });
            }
            if(MonthlyRadio.isSelected()){
                AllAptRadio.setSelected(false);
                WeeklyRadio.setSelected(false);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
