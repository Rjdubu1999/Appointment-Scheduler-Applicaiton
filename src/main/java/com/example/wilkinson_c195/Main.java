package com.example.wilkinson_c195;

import Controller.LoginController;
import Utilities.DataBaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @Author Ryan Wilkinson
 * Software II
 */

/**
 * Main class which launches the scheduler application
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 375);
        stage.setTitle("Scheduler Application C195");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creating a method which will launch the application and connect to the MySQL database to retrieve data for use in
     * this program.
     * @param args
     */
    public static void main(String[] args) {
        DataBaseConnection.openConnection();
        launch(args);
        DataBaseConnection.closeConnection();

    }
}

/**
 *                 RESOURCES/REFERENCES:
 * Western Governors University.Organizing Your Code. https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=1fd36595-7809-4475-a395-ada401479280
 * Western Governors University.When Appointments Collide! https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=77f9a513-5b57-4e35-8365-ad9200f7d210
 * Western Governors University.Lambda Expressions https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=c8661983-892f-495e-ae71-ab4901128c6e
 * Western Governors University.Converting Between Timezones https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=cbb62695-df1d-4f44-9e12-ab49011203ae
 * Western Governors University.Course Chatter. https://srm--c.na127.visual.force.com/apex/FDP/CommonsExpandedChatter?code=C195
 * Western Governors University. Localization and Resource Bundle Parts 1 and 2
 * Western Governors University. WGU Code Resource Repository. https://westerngovernorsuniversity.sharepoint.com/sites/CISoftwareTeamResourcesRepo/Student%20Resources/Forms/AllItems.aspx?viewid=a13d9dcb%2D9ff5%2D42bd%2Db2d4%2Def3c17a6fb8c&id=%2Fsites%2FCISoftwareTeamResourcesRepo%2FStudent%20Resources%2FC195%20Code%20Repository
 * Western Governors University. JDBC Code For MySQL DataBase. https://westerngovernorsuniversity.sharepoint.com/sites/CISoftwareTeamResourcesRepo/Student%20Resources/Forms/AllItems.aspx?id=%2Fsites%2FCISoftwareTeamResourcesRepo%2FStudent%20Resources%2FC195%20Code%20Repository%2FC195%5FQSG%2FC195%5FQSG%2FConnecting%5Fto%5Fthe%5FDatabase%5FIntelliJ&viewid=a13d9dcb%2D9ff5%2D42bd%2Db2d4%2Def3c17a6fb8c
 */
  // Western Governors University. Connecting to DataBase with Intellij. https://wgu.webex.com/recordingservice/sites/wgu/recording/e0de37fdf5471039aefd00505681be2e/playback