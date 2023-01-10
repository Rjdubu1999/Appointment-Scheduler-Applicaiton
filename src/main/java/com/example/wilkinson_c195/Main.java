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



public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 375);
        stage.setTitle("Scheduler Application C195");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DataBaseConnection.openConnection();
        launch(args);
        DataBaseConnection.closeConnection();

    }
}

