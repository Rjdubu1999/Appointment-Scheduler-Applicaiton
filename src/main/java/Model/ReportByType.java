package Model;

public class ReportByType {
    public String appointmentType;
    public int totalAppointments;


    public ReportByType(String appointmentType, int totalAppointments){
        this.appointmentType = appointmentType;
        this.totalAppointments = totalAppointments;
    }

    public String getAppointmentType(){
        return  appointmentType;
    }
    public int getTotalAppointments(){
        return totalAppointments;
    }
}
