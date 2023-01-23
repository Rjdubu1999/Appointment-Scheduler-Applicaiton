package Model;

/**
 * @Author Ryan Wilkinson
 * C195 - Software II
 */

/**
 * creating  report by type model that will be used in the report controller
 */
public class ReportByType {
    public String appointmentType;
    public int totalAppointments;


    /**
     * @param appointmentType report by type constructor
     * @param totalAppointments
     */
    public ReportByType(String appointmentType, int totalAppointments){
        this.appointmentType = appointmentType;
        this.totalAppointments = totalAppointments;
    }

    /**
     * @return gets appointment type
     */
    public String getAppointmentType(){
        return  appointmentType;
    }

    /**
     * @return gets the total number of appointments
     */
    public int getTotalAppointments(){
        return totalAppointments;
    }
}
