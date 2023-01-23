package Model;

/**
 * @Author Ryan Wilkinson
 * C195- Software II
 */

/**
 * Creating model class country
 */
public class Country {

    private int countryID;
    private String countryName;


    /**
     * @param countryID constructor for model class country
     * @param countryName
     */
    public Country(int countryID, String countryName){
        this.countryName = countryName;
        this.countryID = countryID;
    }

    /**
     * @return gets country id
     */
    public int getCountryID(){
        return countryID;
    }

    /**
     * @return gets country name
     */
    public String getCountryName(){
        return countryName;
    }
}
