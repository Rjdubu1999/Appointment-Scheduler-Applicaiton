package Controller;

import Model.Appointment;
import Model.Customer;
import com.example.wilkinson_c195.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainAppointmentController implements Initializable {

    @FXML private
    TableView<Customer> CustomerTableView;
    @FXML private TableColumn<Customer, Integer> CustomerIDColumn;
    @FXML private TableColumn<Customer, String> CustomerColumn;
    @FXML private TableView<Appointment> MonthlyApptTableView;
    @FXML private TableColumn<Appointment, String> MonthlyLocationCol;
    @FXML private TableColumn<Appointment, String> MonthlyDescriptionCol;
    @FXML private TableColumn<Appointment, String> MonthlyContactCol;
    @FXML private TableColumn<Appointment, String> MonthlyStartCol;
    @FXML private TableColumn<Appointment, String> MonthlyEndCol;
    @FXML private Tab WeeklyTab;
    @FXML private TableView<Appointment> WeeklyTableView;
    @FXML private TableColumn<Appointment, String> WeeklyDescriptionCol;
    @FXML private TableColumn<Appointment, String> WeeklyContactCol;
    @FXML private TableColumn<Appointment, String> WeeklyStartCol;
    @FXML private AnchorPane AppointmentAnchorMain;
    private boolean monthlyBool;
    private Appointment selectedAppointment;
    private Customer selectedCustomer;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onActionAddAppointment(ActionEvent actionEvent) throws IOException {
        if(CustomerTableView.getSelectionModel().getSelectedItem() != null){
            selectedCustomer = CustomerTableView.getSelectionModel().getSelectedItem();

        }else {
            return;
        }
        Dialog<ButtonType> dialog = new Dialog();
        dialog.initOwner(AppointmentAnchorMain.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("AddAppointment.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException ioException){
            System.out.println("Error Adding Appointment: " + ioException.getMessage());
        }
        ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(save);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        AddAppointmentController addAppointmentController = fxmlLoader.getController();
        //add appointment controller finish so i can add it
        addAppointmentController
    }

    public void onActionModifyAppointment(ActionEvent actionEvent) {
    }

    public void onActionDeleteAppointment(ActionEvent actionEvent) {
    }

    public void onActionBack(ActionEvent actionEvent) {
    }
}
