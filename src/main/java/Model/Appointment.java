package Model;

import java.time.*;

public class Appointment {
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime start;
    private LocalDateTime end;
    public int customerID;
    public int userID;
    public int contactID;


    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID){

        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;

    }
    public String getAppointmentType(){
        return appointmentType;
    }
    public int getAppointmentID(){
        return appointmentID;
    }
    public String getAppointmentTitle(){
        return appointmentTitle;
    }
    public String getAppointmentDescription(){
        return appointmentDescription;
    }

    public String getAppointmentLocation(){
        return appointmentLocation;
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
