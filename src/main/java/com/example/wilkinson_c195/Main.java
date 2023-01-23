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

