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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

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
    @FXML private TextField IDfield;
    @FXML private TextField NameField;
    @FXML private TextField PhoneField;
    @FXML private TextField AddressField;
    @FXML private TextField PostCodeField;
    @FXML private ComboBox<String> CountryCombo;
    @FXML private ComboBox<String> StateCombo;



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
    try{
        Connection connection = DataBaseConnection.openConnection();
        ObservableList<CountryDAO> countryList = CountryDAO.getCountryList();
        ObservableList<String> countries = FXCollections.observableArrayList();
        ObservableList<FLD_DAO> allFLD = FLD_DAO.getAllFLD();
        ObservableList<String> FLDNames = FXCollections.observableArrayList();
        ObservableList<Customer> allCustomer = CustomerDAO.getAllCustomers(connection);
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNamecol.setCellValueFactory((new PropertyValueFactory<>("CustomerName")));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        PostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        PhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        FirstLevelColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

        countryList.stream().map(Country::getCountryName).forEach(countries::add);
        CountryCombo.setItems(countries);

        allFLD.forEach(FLD -> FLDNames.add(FLD.getDivisionName()));
        StateCombo.setItems(FLDNames);
        customerTableView.setItems(allCustomer);

    } catch (Exception exception) {
        exception.printStackTrace();
    }
    }

    public void onActionAdd() {
        try {
            Connection connection = DataBaseConnection.openConnection();
            if (!customerNamecol.getText().isEmpty() || !AddressColumn.getText().isEmpty() ||
                    !PostalCodeCol.getText().isEmpty() || !PhoneColumn.getText().isEmpty() || !CountryCombo.getValue().isEmpty() || !StateCombo.getValue().isEmpty()) {
                Integer newCustomerID = (int) (Math.random() * 50);
                int FLDName = 0;
                for (FLD_DAO fld : FLD_DAO.getAllFLD()) {
                    if(StateCombo.getSelectionModel().getSelectedItem().equals(fld.getDivisionName())){
                        FLDName = fld.getDivision_ID();
                    }

                }
                String query = "INSERT INTO customers (Customer_Id, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?,?)";
                DataBaseConnection.setPreparedStatement(DataBaseConnection.getConnection(),query);
                PreparedStatement preparedStatement = DataBaseConnection.getPreparedStatement();
                preparedStatement.setInt(1, newCustomerID);
                preparedStatement.setString(2, customerNamecol.getText());
                preparedStatement.setString(3,AddressColumn.getText());
                preparedStatement.setString(4,PostalCodeCol.getText());
                preparedStatement.setString(5,PhoneColumn.getText());
                preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(7, "admin");
                preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(9, "admin");
                preparedStatement.setInt(10, FLDName);
                preparedStatement.execute();

                IDfield.clear();
                NameField.clear();
                AddressField.clear();
                PostCodeField.clear();
                PhoneField.clear();

                ObservableList<Customer> updateCustomers = CustomerDAO.getAllCustomers(connection);
                customerTableView.setItems(updateCustomers);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

    public void onActionModify(ActionEvent actionEvent) throws  SQLException{
        try{
            DataBaseConnection.openConnection();
            Customer customer = (Customer) customerTableView.getSelectionModel().getSelectedItem();
            String division = "" , country = "";
            if(customer != null){
                ObservableList<CountryDAO> getAllCountries = CountryDAO.getCountryList();
                ObservableList<FLD_DAO> getFLDNames = FLD_DAO.getAllFLD();
                ObservableList<String> allFLD = FXCollections.observableArrayList();
                StateCombo.setItems(allFLD);
                customerIdCol.setText(String.valueOf(customer.getCustomerID()));
                customerNamecol.setText(customer.getCustomerName());
                AddressColumn.setText(customer.getCustomerAddress());
                PostalCodeCol.setText(customer.getCustomerPostalCode());
                PhoneColumn.setText(customer.getCustomerPhone());

                for(FLD fld: getFLDNames){
                    allFLD.add(fld.getDivisionName());
                    int updateCountryID = fld.getCountry_ID();
                    if(fld.getCountry_ID() == customer.getDivisionID()) division = fld.getDivisionName();
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
        Parent parent = FXMLLoader.load(Main.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage returnToMain = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        returnToMain.setScene(scene);
        returnToMain.show();
    }

    public void onActionSave(ActionEvent actionEvent) {}
}
