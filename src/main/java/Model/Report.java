package Model;

public class Report {
    private int customerCount;
    private String customerName;
    public String appointmentMonth;
    public int totalAppointments;



    public Report(String customerName, int customerCount){
        this.customerCount = customerCount;
        this.customerName = customerName;
    }

    public String getCustomerName(){
        return customerName;
    }
    public int getCustomerCount(){
        return customerCount;
    }

}
