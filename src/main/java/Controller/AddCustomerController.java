package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController  implements Initializable {

    @FXML  private TextField customerName;
    @FXML  private TextField customerAddress;
    @FXML  private ComboBox city;
    @FXML  private TextField country;
    @FXML  private TextField zip;
    @FXML  private TextField phone;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
