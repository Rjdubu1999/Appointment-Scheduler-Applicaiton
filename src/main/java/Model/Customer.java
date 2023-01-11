package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public final class Customer {
    private final SimpleIntegerProperty customerID = new SimpleIntegerProperty();
    private final SimpleStringProperty customerName = new SimpleStringProperty();
    private final SimpleStringProperty customerAddress = new SimpleStringProperty();
    private final SimpleStringProperty customerPhone = new SimpleStringProperty();
    private final SimpleStringProperty customerCity = new SimpleStringProperty();
    private final SimpleStringProperty customerZip = new SimpleStringProperty();


    public Customer() {
    }
    public int getCustomerID(){
    return customerID.get();
    }
    public String getCustomerName(){
        return customerName.get();
    }
    public String getCustomerAddress(){
        return customerAddress.get();
    }
    public String getCustomerPhone(){
        return customerPhone.get();
    }
    public String getCustomerCity(){
        return customerCity.get();
    }
    public String getCustomerZip(){
        return customerZip.get();
    }

    public void setCustomerID(int customerID){
        this.customerID.set(customerID);
    }
    public void setCustomerName(String customerName){
        this.customerName.set(customerName);
    }
    public void setCustomerAddress(String customerAddress){
        this.customerAddress.set(customerAddress);
    }
    public void setCustomerCity(String customerCity){
        this.customerCity.set(customerCity);
    }
    public void setCustomerPhone(String customerPhone){
        this.customerPhone.set(customerPhone);
    }
    public void setCustomerZip(String customerZip){
        this.customerZip.set(customerZip);
    }
    public Customer(int id, String name, String address, String phone, String city, String zip){
        setCustomerID(id);
        setCustomerName(name);
        setCustomerAddress(address);
        setCustomerPhone(phone);
        setCustomerCity(city);
        setCustomerZip(zip);
    }


}