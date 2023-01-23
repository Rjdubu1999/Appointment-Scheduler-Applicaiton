package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @Author Ryan Wilkinson
 * C195- Software II
 */

/**
 * Creating model class customer
 */
public class Customer {
    private int customerID ;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerPostalCode;
    private String divisionName;
    private int divisionID;

    /**
     * @return gets customer id
     */
    public int getCustomerID(){
    return customerID;
    }

    /**
     * @return gets customer name
     */
    public String getCustomerName(){
        return customerName;
    }

    /**
     * @return gets customer address
     */
    public String getCustomerAddress(){
        return customerAddress;
    }

    /**
     * @return gets customer phone
     */
    public String getCustomerPhone(){
        return customerPhone;
    }

    /**
     * @return gets customer postal code
     */
    public String getCustomerPostalCode(){
        return customerPostalCode;
    }

    /**
     * @return gets division name
     */
    public String getDivisionName(){

        return divisionName;
    }

    /**
     * @return gets division Id
     */
        public Integer getDivisionID(){
        return divisionID;
        }

    /**
     * @param customerID sets customer id
     */
    public void setCustomerID(Integer customerID){
        this.customerID =customerID;
    }

    /**
     * @param customerName sets customer name
     */
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }
    public void setCustomerAddress(String customerAddress){
        this.customerAddress = customerAddress;
    }

    /**
     * @param divisionName sets division name
     */
    public void setDivisionName(String divisionName){
        this.divisionName = divisionName;
    }
    public void setCustomerPhone(String customerPhone){
        this.customerPhone = customerPhone;
    }
    public void setCustomerZip(String customerPostalCode){
        this.customerPostalCode = customerPostalCode;
    }

    /**
     * @param divisionID sets division id
     */
    public void setDivisionID(Integer divisionID){
        this.divisionID = divisionID;
    }

    /**
     * @param customerID constructor for customer class
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhone
     * @param divisionID
     * @param divisionName
     */
    public Customer(int customerID, String customerName, String customerAddress,String customerPostalCode, String customerPhone,  int divisionID, String divisionName){
        this.customerID = customerID;
        this.customerName= customerName;
        this.customerAddress= customerAddress;
        this.customerPostalCode= customerPostalCode;
        this.customerPhone= customerPhone;
        this.divisionName= divisionName;

        this.divisionID = divisionID;
    }


}