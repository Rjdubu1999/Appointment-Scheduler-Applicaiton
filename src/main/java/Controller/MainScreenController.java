package Controller;

import com.example.wilkinson_c195.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {











    public void onActionAppointmentsScreen(ActionEvent actionEvent) throws IOException {
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Main.class.getResource("MainAppointment.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException ioException){
            System.out.println("Error getting Appointment screen: " + ioException.getClass());
        }
    }

    public void onActionCustomerScreen(ActionEvent actionEvent) throws IOException {
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("MainCustomer.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException ioException){
            System.out.println("Error getting Customer screen: " + ioException.getClass());
        }
    }

    public void onActionReportScreen(ActionEvent actionEvent) throws IOException{
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("MainReports.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException ioException){
            System.out.println("Error getting Appointment screen: " + ioException.getClass());
        }
    }

    public void onActionLogScreen(ActionEvent actionEvent) throws IOException {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}