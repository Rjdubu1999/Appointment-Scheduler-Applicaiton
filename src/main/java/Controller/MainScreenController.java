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

/**
 * @Author Ryan Wilkinson
 * C195 -- Software II
 */

/**
 * Creating class controller for the main screen which will enable the user to select different screens to
 * use the program
 */
public class MainScreenController implements Initializable {


    /**
     * @param actionEvent This method allows the user to go to the main appointment screen to add, delete, and update
     *                    appointments
     * @throws IOException
     */
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

    /**
     * @param actionEvent This method allows the user to go to the main customer screen to add, delete and update customers
     * @throws IOException
     */
    public void onActionCustomerScreen(ActionEvent actionEvent) throws IOException {
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Main.class.getResource("MainCustomer.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException ioException){
            System.out.println("Error getting Customer screen: " + ioException.getClass());
        }
    }

    /**
     * @param actionEvent This method will allow the user to go to the main report screen which will allow
     *                    the user to view the reports about customers and appointments
     * @throws IOException
     */
    public void onActionReportScreen(ActionEvent actionEvent) throws IOException{
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Main.class.getResource("MainReports.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException ioException){
            System.out.println("Error getting Appointment screen: " + ioException.getClass());
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}