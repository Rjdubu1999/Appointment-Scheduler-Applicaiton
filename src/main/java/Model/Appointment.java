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

    /**
     * @Author Ryan Wilkinson
     * C195 - Software II
     */

    /**
     * Creating appointment model
     * constructor for appointment
     * @param appointmentID
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentType
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     */

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

    /**
     * @return gets appointment type
     */
    public String getAppointmentType(){
        return appointmentType;
    }

    /**
     * @return gets appointment ID
     */
    public int getAppointmentID(){
        return appointmentID;
    }

    /**
     * gets appointment title
     * @return
     */
    public String getAppointmentTitle(){
        return appointmentTitle;
    }

    /**
     * gets appointment description
     * @return
     */
    public String getAppointmentDescription(){
        return appointmentDescription;
    }

    /**
     * gets appointment location
     * @return
     */
    public String getAppointmentLocation(){
        return appointmentLocation;
    }

    /**
     * gets appointment start time
     * @return
     */
    public LocalDateTime getStart(){
        System.out.println("Appointment Starts At :" + start);
        return  start;
    }

    /**
     * gets appointment end time
     * @return
     */
    public LocalDateTime getEnd(){
        System.out.println("Appointment End At :" + start);
        return end;
    }

    /**
     * gets customer Id
     * @return
     */
    public int getCustomerID(){
        return customerID;
    }

    /**
     * gets contact id
     * @return
     */
    public int getContactID(){
        return contactID;
    }

    /**
     * gets user id
     * @return
     */
    public int getUserID(){
        return userID;
    }




}
