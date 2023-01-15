package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public final class Customer {
    private Integer customerID ;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerPostalCode;
    private String divisionName;
    private int divisionID;

    public int getCustomerID(){
    return customerID;
    }
    public String getCustomerName(){
        return customerName;
    }
    public String getCustomerAddress(){
        return customerAddress;
    }
    public String getCustomerPhone(){
        return customerPhone;
    }
    public String getCustomerPostalCode(){
        return customerPostalCode;
    }
    public String getDivisionName(){

        return divisionName;
    }
        public Integer getDivisionID(){
        return divisionID;
        }
    public void setCustomerID(int customerID){
        this.customerID =customerID;
    }
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }
    public void setCustomerAddress(String customerAddress){
        this.customerAddress = customerAddress;
    }
    public void setDivisionName(String divisionName){
        this.divisionName = divisionName;
    }
    public void setCustomerPhone(String customerPhone){
        this.customerPhone = customerPhone;
    }
    public void setCustomerZip(String customerPostalCode){
        this.customerPostalCode = customerPostalCode;
    }
    public void setDivisionID(Integer divisionID){
        this.divisionID = divisionID;
    }
    public Customer(int customerID, String customerName, String customerAddress, String customerPhone, String divisionName, String customerPostalCode, Integer divisionID){
        this.customerID= customerID;
        this.customerName= customerName;
        this.customerAddress= customerAddress;
        this.customerPhone= customerPhone;
        this.divisionName= divisionName;
        this.customerPostalCode= customerPostalCode;
        this.divisionID = divisionID;
    }


}