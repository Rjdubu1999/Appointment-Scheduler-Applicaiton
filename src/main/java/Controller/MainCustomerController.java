package Controller;

import Data_Access_Object.AppointmentDAO;
import Data_Access_Object.CountryDAO;
import Data_Access_Object.CustomerDAO;
import Data_Access_Object.FLD_DAO;
import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.FLD;
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
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @Author Ryan Wilkinson
 * C195 -- Software II
 */

/**
 * Creating a controller class to enable a user to add, modify and delete
 * customers and the data associated with them in the MySQL database
 */
public class MainCustomerController implements Initializable {


    @FXML
    private
    AnchorPane customerAnchorPane;
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<?, ?> customerIdCol;
    @FXML
    private TableColumn<?, ?> customerNamecol;
    @FXML private TableColumn<?, ?> AddressColumn;
    @FXML private TableColumn<?, ?> PostalCodeCol;
    @FXML private TableColumn<?, ?> PhoneColumn;
    @FXML private TableColumn<?, ?> FirstLevelColumn;
    @FXML private TextField IDField;
    @FXML private TextField NameField;
    @FXML private TextField PhoneField;
    @FXML private TextField AddressField;
    @FXML private TextField PostalCodeField;
    @FXML private ComboBox<String> CountryCombo;
    @FXML private ComboBox<String> StateCombo;


    /**
     * @param url Initializes the controller class and sets the main customer table to
     *            hold the values the have been typed into the fields below the table. This information
     *            is then input into the MySql database
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    try{
        Connection connection = DataBaseConnection.openConnection();
        ObservableList<CountryDAO> countryList = CountryDAO.getCountryList();
        ObservableList<String> countries = FXCollections.observableArrayList();
        ObservableList<FLD_DAO> allFLD = FLD_DAO.getAllFLD();
        ObservableList<String> FLDNames = FXCollections.observableArrayList();
        ObservableList<Customer> allCustomer = CustomerDAO.getAllCustomers(connection);
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNamecol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        PostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        PhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        FirstLevelColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

        countryList.stream().map(Country::getCountryName).forEach(countries::add);
        CountryCombo.setItems(countries);

        allFLD.forEach(FLD -> {
            FLDNames.add(FLD.getDivisionName());
        });
        StateCombo.setItems(FLDNames);
        customerTableView.setItems(allCustomer);
        IDField.setEditable(false);

    } catch (Exception exception) {
        exception.printStackTrace();
    }
    }

    /**
     * This method allows a user to add a customer and their associated information into the MySqL database and the
     * main customer table. It assigns the customer a random id up to the number 200
     */
    public void onActionAdd() {
        try {
            Connection connection = DataBaseConnection.openConnection();
            if (!NameField.getText().isEmpty() || !AddressField.getText().isEmpty() ||
                    !PostalCodeField.getText().isEmpty() || !PhoneField.getText().isEmpty() || !CountryCombo.getValue().isEmpty() || !StateCombo.getValue().isEmpty()) {
                Integer newCustomerID = (int) (Math.random() * 25);
                IDField.setText(newCustomerID.toString());


                int FLDName = 0;
                for (FLD_DAO fld : FLD_DAO.getAllFLD()) {
                    if(StateCombo.getSelectionModel().getSelectedItem().equals(fld.getDivisionName())){
                        FLDName = fld.getDivision_ID();
                    }
                    if(NameField.getText().isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Name field is blank please fill in");
                        alert.showAndWait();
                        return;
                    }
                    if(AddressField.getText().isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.ERROR, " Address is blank please fill in");
                        alert.showAndWait();
                        return;
                    }
                    if(PostalCodeField.getText().isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.ERROR, " Postal code is blank please fill in");
                        alert.showAndWait();
                        return;
                    }
                    if(PhoneField.getText().isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Phone number is blank please fill in");
                        alert.showAndWait();
                        return;
                    }
                 //   if(CountryCombo.getValue().isEmpty()){
                   //     Alert alert = new Alert(Alert.AlertType.ERROR, "Country selection is empty");
                     //   alert.showAndWait();
                       // return;
                  //  }
                   // if(StateCombo.getValue().isEmpty()){
                   //     Alert alert = new Alert(Alert.AlertType.ERROR, " is blank please fill in");
                     //   alert.showAndWait();
                       // return;}


/**
 * Insert query to input the information from the fields into the database
 */
                }
                String query = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?,?)";
                DataBaseConnection.setPreparedStatement(DataBaseConnection.getConnection(),query);
                PreparedStatement preparedStatement = DataBaseConnection.getPreparedStatement();
                preparedStatement.setInt(1, newCustomerID);
                preparedStatement.setString(2, NameField.getText());
                preparedStatement.setString(3,AddressField.getText());
                preparedStatement.setString(4,PostalCodeField.getText());
                preparedStatement.setString(5,PhoneField.getText());
                preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(7, "admin");
                preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(9, "admin");
                preparedStatement.setInt(10, FLDName);
                preparedStatement.execute();

                IDField.clear();
                NameField.clear();
                AddressField.clear();
                PostalCodeField.clear();
                PhoneField.clear();

                ObservableList<Customer> updateCustomers = CustomerDAO.getAllCustomers(connection);
                customerTableView.setItems(updateCustomers);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

    /**
     * @param actionEvent This method allows a user to highlight a customer from the table then click modify, which will
     *                    load all of the users information into the fields to be modified and updated which will then
     *                    be saved into the table and database when the user clicks save
     * @throws IOException
     * @throws SQLException
     */
    public void onActionModify(ActionEvent actionEvent) throws IOException, SQLException {

        try{
            DataBaseConnection.openConnection();
            Customer customer = (Customer) customerTableView.getSelectionModel().getSelectedItem();
            String division = "" , country = "";
            if(customer != null){
                ObservableList<CountryDAO> getAllCountries = CountryDAO.getCountryList();
                ObservableList<FLD_DAO> getFLDNames = FLD_DAO.getAllFLD();
                ObservableList<String> allFLD = FXCollections.observableArrayList();
                StateCombo.setItems(allFLD);
                IDField.setText(String.valueOf(customer.getCustomerID()));
                NameField.setText(customer.getCustomerName());
                AddressField.setText(customer.getCustomerAddress());
                PostalCodeField.setText(customer.getCustomerPostalCode());
                PhoneField.setText(customer.getCustomerPhone());

                for(FLD fld: getFLDNames){
                    allFLD.add(fld.getDivisionName());
                    int updateCountryID = fld.getCountry_ID();
                    if(fld.getDivision_ID() == customer.getDivisionID()) division = fld.getDivisionName();
                    for(Country countryVar: getAllCountries){
                        if(countryVar.getCountryID() == updateCountryID){
                            country = countryVar.getCountryName();
                        }
                    }
                }
            }
            StateCombo.setValue(division);
            CountryCombo.setValue(country);
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

    /**
     * @param actionEvent This method allows the user to delete a customer from the main table and also
     *                    delete their associated data in the databse
     * @throws Exception
     */
    public void onActionDelete(ActionEvent actionEvent) throws Exception {
        Connection connection = DataBaseConnection.openConnection();

        ObservableList<Appointment> AllAppointmentsList = AppointmentDAO.getAllAppointment();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would You Like To Delete Customer And Associated Appointments?");
        Optional<ButtonType> confirm = alert.showAndWait();
        if(confirm.isPresent() && confirm.get() == ButtonType.OK){
            int deleteID = customerTableView.getSelectionModel().getSelectedItem().getCustomerID();
            AppointmentDAO.deleteAppointment(deleteID, connection);
            String queryDelete = "DELETE FROM customers WHERE Customer_ID = ?";
            DataBaseConnection.setPreparedStatement(DataBaseConnection.getConnection(), queryDelete);

            PreparedStatement deletePrepared = DataBaseConnection.getPreparedStatement();
            int currentCustomer = customerTableView.getSelectionModel().getSelectedItem().getCustomerID();

            for(Appointment appointment: AllAppointmentsList ){
                int AppointmentCustomer = appointment.getCustomerID();
                if(currentCustomer == AppointmentCustomer){
                    String deleteAppointment = "Delete FROM appointments WHERE Appointment_Id = ?";
                    DataBaseConnection.setPreparedStatement(DataBaseConnection.getConnection(),deleteAppointment);
                }
            }
            deletePrepared.setInt(1, currentCustomer);
            deletePrepared.execute();
            ObservableList<Customer> updateCustomerList = CustomerDAO.getAllCustomers(connection);
            customerTableView.setItems(updateCustomerList);
        }

    }

    public void onActionBack(ActionEvent actionEvent) throws IOException{
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();

        //This way for some reason kept keeping dulicates of the same screen.
        //Will use .hide() instead, as it does not open multiple windows
       // Parent parent = FXMLLoader.load(Main.class.getResource("MainScreen.fxml"));
        //Scene scene = new Scene(parent);
       // Stage returnToMain = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
       // returnToMain.setScene(scene);
       // returnToMain.show();
    }

    /**
     * @param actionEvent This method allows a user to save the updated information that a user has input
     *                    for a customer after it has been modified. It will update the table and the database
     *                    information assocaited with it. After save has been clicked it will clear the
     *                    fields
     */
    public void onActionSave(ActionEvent actionEvent) {
        try {
            Connection connection = DataBaseConnection.openConnection();
            if(!NameField.getText().isEmpty() || !AddressField.getText().isEmpty() ||
                    !PostalCodeField.getText().isEmpty() || !PhoneField.getText().isEmpty() || !CountryCombo.getValue().isEmpty() || !StateCombo.getValue().isEmpty()){
                int fldName = 0;
                for (FLD_DAO fld_dao : FLD_DAO.getAllFLD()){
                    if(StateCombo.getSelectionModel().getSelectedItem().equals(fld_dao.getDivisionName())){
                        fldName = fld_dao.getDivision_ID();
                    }
                }
                String query = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
                DataBaseConnection.setPreparedStatement(DataBaseConnection.getConnection(),query);
                PreparedStatement preparedStatement = DataBaseConnection.getPreparedStatement();
                preparedStatement.setInt(1, Integer.parseInt(IDField.getText()));
                preparedStatement.setString(2, NameField.getText());
                preparedStatement.setString(3, AddressField.getText());
                preparedStatement.setString(4, PostalCodeField.getText());
                preparedStatement.setString(5, PhoneField.getText());
                preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(7, "admin");
                preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(9, "admin");
                preparedStatement.setInt(10, fldName);
                preparedStatement.setInt(11, Integer.parseInt(IDField.getText()));
                preparedStatement.execute();

                IDField.clear();
                NameField.clear();
                AddressField.clear();
                PostalCodeField.clear();
                PhoneField.clear();

                ObservableList<Customer> updateCustomerList = CustomerDAO.getAllCustomers(connection);
                customerTableView.setItems(updateCustomerList);
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }

    /**
     * @param actionEvent Fills the combo box with all of the countries to be selected
     * @throws SQLException
     */
    public void FillCountryCombo(ActionEvent actionEvent) throws  SQLException{
        try{
            DataBaseConnection.openConnection();

            String selectedCountry = CountryCombo.getSelectionModel().getSelectedItem();
            ObservableList<FLD_DAO> getAllFLD = FLD_DAO.getAllFLD();
            ObservableList<String> usDivisions = FXCollections.observableArrayList();
            ObservableList<String> ukDivisions = FXCollections.observableArrayList();
            ObservableList<String> canDivisions = FXCollections.observableArrayList();

            getAllFLD.forEach(FLD -> {
                if(FLD.getCountry_ID() == 1){
                    usDivisions.add(FLD.getDivisionName());
                }else if( FLD.getCountry_ID() == 2){
                    ukDivisions.add(FLD.getDivisionName());
                }else if(FLD.getCountry_ID() == 3){
                    canDivisions.add(FLD.getDivisionName());
                }
            });
            if(selectedCountry.equals("U.S")){
                StateCombo.setItems(usDivisions);
            }else if(selectedCountry.equals("UK")){
                StateCombo.setItems(ukDivisions);
            }else if(selectedCountry.equals("Canada")){
                StateCombo.setItems(canDivisions);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }



}
