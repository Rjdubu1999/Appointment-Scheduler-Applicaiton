package Model;

public class FLD {
    private int division_ID;
    private String divisionName;
    public int country_ID;
    public FLD(int division_ID, String divisionName, int country_ID){
        this.division_ID = division_ID;
        this.country_ID = country_ID;
        this.divisionName = divisionName;
    }
    public int getDivision_ID(){
        return division_ID;
    }
    public String getDivisionName(){
        return divisionName;
    }
    public int getCountry_ID(){
        return country_ID;
    }
}
