package Model;

/**
 * @Author Ryan Wilkinson
 * C195 - Software II
 */

/**
 * Creating a report by date model that will be used to display in the report controller
 */
public class ReportByDates {
    public String appointmentByMonth;
    public int appointmentTotal;


    /**
     * @param appointmentByMonth constructor for report by dates model
     * @param appointmentTotal
     */
    public ReportByDates(String appointmentByMonth, int appointmentTotal){
        this.appointmentTotal = appointmentTotal;
        this.appointmentByMonth = appointmentByMonth;
    }

    public String getAppointmentByMonth(){
        return appointmentByMonth;
    }
    public int getAppointmentTotal(){
        return appointmentTotal;
    }
}
