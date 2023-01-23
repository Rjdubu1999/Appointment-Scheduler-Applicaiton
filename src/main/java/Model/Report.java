package Model;

public class Report {
    private int divisionTotal;
    private String divisionName;


    /**
     * Creating a class to collect and sum the number of appointments from customers form different divisions
     * @param
     * @param
     */
    public Report(int divisionTotal, String divisionName){
        this.divisionTotal = divisionTotal;
        this.divisionName = divisionName;
    }

    public int getDivisionTotal(){
        return divisionTotal;
    }
    public String getDivisionName(){
        return divisionName;
    }
    public void setDivisionName(){
        this.divisionName = divisionName;
    }
    public void setDivisionTotal(){
        this.divisionTotal = divisionTotal;
    }

}
