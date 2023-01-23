package Model;

public class Report {
    private int divisionTotal;
    private String divisionName;


    /**
     * Creating a class to collect and sum the number of appointments from customers form different divisions that
     * will be used in the main report controller to display the data
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

    /**
     * @return gets division name
     */
    public String getDivisionName(){
        return divisionName;
    }
    public void setDivisionName(){
        this.divisionName = divisionName;
    }

    /**
     * sets division total
     */
    public void setDivisionTotal(){
        this.divisionTotal = divisionTotal;
    }

}
