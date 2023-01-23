package Data_Access_Object;

import Model.Country;
import Utilities.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author Ryan Wilkinson
 * C195 - Software II
 */

/**
 * Creates a data access object for the country model to be able to access the data in the countries table
 * in the MySQL database
 */
public class CountryDAO extends Country {


    /**
     * @param countryID super constructor for the countryDAO model
     * @param countryName
     */
    public CountryDAO(int countryID, String countryName) {
        super(countryID, countryName);
    }

    /**
     * @return this method gets an observable list of all of the countries by id from the countries table in the
     * MYSQL database
     * @throws SQLException
     */
    public static ObservableList<CountryDAO> getCountryList() throws SQLException{
        ObservableList<CountryDAO> countryDAOObservableList = FXCollections.observableArrayList();
        String query = "SELECT Country_ID, Country from countries";
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int countryID = resultSet.getInt("Country_ID");
            String countryName = resultSet.getString("Country");
            CountryDAO country = new CountryDAO(countryID, countryName);
            countryDAOObservableList.add(country);
        }
        return countryDAOObservableList;
    }
}
