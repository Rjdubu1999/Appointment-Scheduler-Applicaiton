package Controller;

import Model.Customer;
import Model.DataBaseCustomer;
import Utilities.DataBaseConnection;
import com.example.wilkinson_c195.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainCustomerController implements Initializable {


    @FXML
    private
    AnchorPane customerAnchorPane;
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, Integer> customerIdCol;
    @FXML
    private TableColumn<Customer, String> customerNamecol;


    private Customer selectedCustomer;

    public static boolean saveCustomer(String name, String address, int cityId, String zip, String phone) {
        int addressId = 0;
        int customerId = 0;
        try {
            Statement statement = DataBaseConnection.getConnection().createStatement();
            ResultSet resultSetOne = statement.executeQuery("SELECT MAX(addressId) FROM address");
            if (resultSetOne.next()) {
                addressId = resultSetOne.getInt(1);
                addressId++;
            }
            ResultSet resultSetTwo = statement.executeQuery("SELECT MAX(customerId) FROM customer");
            if (resultSetTwo.next()) {
                customerId = resultSetTwo.getInt(1);
                customerId++;}
                String firstQuery = "INSERT INTO address SET addressId=" + addressId + ", address='" + address + "', address2='none', phone='" + phone + "', postalCode='" + zip + "' cityId=" + cityId + ", createDate=NOW(), createdBy='' lastUpdate=NOW(), lastUpdateBy=''";
                int updateFirst = statement.executeUpdate(firstQuery);
                if (updateFirst == 1) {
                    String secondQuery = "INSERT INTO customer SET customId=" + customerId + ", customerName='" + name + "', addressId=" + addressId + ", active=1, createDate=NOW(), createdBy='', lastUpdateBy=''";
                    int secondUpdate = statement.executeUpdate(secondQuery);
                    if (secondUpdate == 1) {
                        return true;
                    }
                }

        }catch (SQLException sqlException){
            System.out.println("Customer Info Not Found : " + sqlException.getMessage());
        }
        return false;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onActionAdd()  {
        Dialog<ButtonType> dialog = new Dialog();
        dialog.initOwner(customerAnchorPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("AddCustomer.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());

        }catch (IOException ioException){
            System.out.println("Error Adding Customer: " + ioException.getMessage());
        }
        ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(save);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        AddCustomerController controller = fxmlLoader.getController();
        dialog.showAndWait().ifPresent((response -> {
            if(response == save){
                try{
                    if(controller.handleAddCustomer()){
                        customerTableView.setItems(DataBaseCustomer.getCustomerList());
                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error Adding Customer");
                        alert.setContentText(controller.displayError());
                        alert.showAndWait().ifPresent( seoncdResponse -> {
                            if(response == ButtonType.OK){
                                onActionAdd();
                            }
                        });
                    }
                }catch (SQLException sqlException){
                    System.out.println("Exception : " + sqlException.getMessage());
                }
            }
        }));
    }

    public void onActionModify(ActionEvent actionEvent) {
    }

    public void onActionDelete(ActionEvent actionEvent) {
    }

    public void onActionBack(ActionEvent actionEvent) {
    }
}
