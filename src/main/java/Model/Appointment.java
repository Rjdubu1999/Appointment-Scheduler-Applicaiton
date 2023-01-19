package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Appointment {
    private int appointmentID;
    private String appointmentTitle;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    public int customerID;
    public int userID;
    public int contactID;


    public Appointment(int appointmentID, String appointmentTitle, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID){
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }
    public String getType(){
        return type;
    }
    public int getAppointmentID(){
        return appointmentID;
    }
    public String getAppointmentTitle(){
        return appointmentTitle;
    }
    public String getDescription(){
        return description;
    }

    public String getLocation(){
        return location;
    }

    public LocalDateTime getStart(){
        System.out.println("Appointment Starts At :" + start);
        return  start;
    }

    public LocalDateTime getEnd(){
        System.out.println("Appointment End At :" + start);
        return end;
    }

    public int getCustomerID(){
        return customerID;
    }
    public int getContactID(){
        return contactID;
    }
    public int getUserID(){
        return userID;
    }





}
