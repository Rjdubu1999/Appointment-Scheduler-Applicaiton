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
    private ObservableList<String> cityList = FXCollections.observableArrayList("New York", "Phoenix", "London");
    private ObservableList<String> errorList = FXCollections.observableArrayList();

    public boolean nameValidator(String customerName){
        if(customerName.isEmpty()){
            errorList.add("Name field blank, please Please fill in name field.");
            return false;
        }else {
            return true;
        }
    }
    public boolean addressValidator(String customerAddress){
        if(customerAddress.isEmpty()){
            errorList.add("Address field blank, please fill it in.");
            return false;
        }
        return true;
    }
    public boolean cityValidator(int city ){
        if(city == 0){
            errorList.add("City field blank, please fill it in.");
            return false;
        }
        return true;
    }
    public boolean zipValidator(String zip ) {
        if (zip.isEmpty()) {
            errorList.add("Address field blank, please fill it in.");
            return false;
        }
        if (!zip.matches("[A-Z0-9]")) {
            errorList.add("Zip code can only have capitals letters and numbers");
            return false;
        } else {
            return true;
        }
    }

    public boolean phoneValidator(String phone ) {
        if (phone.isEmpty()) {
            errorList.add("Phone Number Field Blank, Please Fill It In.");
            return false;
        }
        if (!phone.matches("[0-9]+")) {
            errorList.add("Phone can only have numbers.");
            return false;
        } else {
            return true;
        }
    }
    public String displayError(){
        String string = "";
        if(errorList.size() > 0){
            for(String error : errorList){
                string = string.concat(error);
            }
        }else{
            string = "Database Error";
        }
        return string;
    }
    public boolean handleAddCustomer() throws SQLException{
        errorList.clear();
        String name = customerName.getText();
        String address =customerAddress.getText();
        int customerCity = city.getSelectionModel().getSelectedIndex() + 1;
        String postalCode = zip.getText();
        String customerPhone = phone.getText();
        if(!nameValidator(name) || addressValidator(address) || cityValidator(customerCity)|| zipValidator(postalCode) || phoneValidator(customerPhone)){
            return false;
        }else{
            return MainCustomerController.saveCustomer(name,address,customerCity,postalCode,customerPhone);
        }
    }


    @FXML
    public void setCountry(){
        String currentCity = city.getSelectionModel().getSelectedItem().toString();
        if( currentCity.equals("London")){
            country.setText("United Kingdom");
        }else if(currentCity.equals("Phoenix") || currentCity.equals("New York")){
            country.setText("United States");
        }
    }
















    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        city.setItems(cityList);

    }
}
