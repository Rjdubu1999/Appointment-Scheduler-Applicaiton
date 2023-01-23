package Data_Access_Object;

import Model.FLD;
import Utilities.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author Ryan Wilkinson
 * C195 -- Software II
 */

/**
 * Creating a first level division data access object that extend the first level division model
 */
public class FLD_DAO  extends FLD {


    /**
     * @param division_ID super constructor for the DAO class
     * @param divisionName
     * @param country_ID
     */
    public FLD_DAO(int division_ID, String divisionName, int country_ID) {
        super(division_ID, divisionName, country_ID);
    }

    /**
     * @return gets all data from the first level division table in the MYSQL database
     * @throws SQLException
     */
    public static ObservableList<FLD_DAO> getAllFLD() throws SQLException{
        ObservableList<FLD_DAO> fld_daoObservableList = FXCollections.observableArrayList();
        String query = "SELECT * from first_level_divisions";
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int divisionID = resultSet.getInt("Division_ID");
            String divisionName = resultSet.getString("Division");
            int countryID = resultSet.getInt("COUNTRY_ID");
            FLD_DAO firstLevelDivision = new FLD_DAO(divisionID,divisionName,countryID);
            fld_daoObservableList.add(firstLevelDivision);
        }
        return fld_daoObservableList;
    }
}
