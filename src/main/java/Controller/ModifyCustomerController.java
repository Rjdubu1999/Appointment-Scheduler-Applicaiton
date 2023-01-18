package Controller;

import Data_Access_Object.CountryDAO;
import Data_Access_Object.CustomerDAO;
import Data_Access_Object.FLD_DAO;
import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.FLD;
import Utilities.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {


    @FXML private TextField NameField;
    @FXML private TextField IDField;
    @FXML private TextField AddressField;
    @FXML private TextField PhoneField;
    @FXML private TextField PostalCodeField;
    @FXML private ComboBox CountryCombo;
    @FXML private ComboBox StateCombo;
    private int customerIndex = 0;
    public Customer selectedCustomer;

    public void customerToModify(int customerIndex, Customer selectedCustomer) throws SQLException{
    this.selectedCustomer = selectedCustomer;

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void OnActionUpdate(ActionEvent actionEvent) {}

    /**    try {
            int customerID = Integer.parseInt(IDField.getText());
            String customerName = NameField.getText();
            String phoneNumber = PhoneField.getText();
            String customerAddress = AddressField.getText();
            String postalCode = PostalCodeField.getText();
            String creationTime = null;
            String createdBy = "";
            int divisionId = 0;
            String countryName = "";

            for (FLD fld : FLD_DAO.getAllFLD()) {
                if (CountryCombo.getSelectionModel().getSelectedItem().equals(fld.getDivisionName())) {
                    divisionId = fld.getDivision_ID();
                }
            }
            for (Country country : CountryDAO.getCountryList()) {
                //if(StateCombo.getSelectionModel().getSelectedItem().equals(country.getCountryName())){
                //  countryName = country.getCountryName();
                //Customer
            }
        }
    }

    }
**/
    public void onActionCancel(ActionEvent actionEvent) {
    }

}
