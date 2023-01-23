package Model;

/**
 * @Author Ryan Wilkinson
 * C195 - Software II
 */

/**
 * Creating model class First level division
 */
public class FLD {
    private int division_ID;
    private String divisionName;
    public int country_ID;

    /**
     * @param division_ID constructor for first level division
     * @param divisionName
     * @param country_ID
     */
    public FLD(int division_ID, String divisionName, int country_ID){
        this.division_ID = division_ID;
        this.country_ID = country_ID;
        this.divisionName = divisionName;
    }

    /**
     * @return gets division ID
     */
    public int getDivision_ID(){
        return division_ID;
    }

    /**
     * @return gets division name
     */
    public String getDivisionName(){
        return divisionName;
    }

    /**
     * @return gets country id
     */
    public int getCountry_ID(){
        return country_ID;
    }
}
