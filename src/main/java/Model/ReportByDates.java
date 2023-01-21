package Model;

public class ReportByDates {
    public String appointmentByMonth;
    public int appointmentTotal;



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
