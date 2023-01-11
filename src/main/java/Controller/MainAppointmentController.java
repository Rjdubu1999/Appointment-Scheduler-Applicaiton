package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainAppointmentController implements Initializable {

    @FXML private
    TableView CustomerTableView;
    @FXML private TableColumn CustomerIDColumn;
    @FXML private TableColumn CustomerColumn;
    @FXML private TableView MonthlyApptTableView;
    @FXML private TableColumn MonthlyLocationCol;
    @FXML private TableColumn MonthlyDescriptionCol;
    @FXML private TableColumn MonthlyContactCol;
    @FXML private TableColumn MonthlyStartCol;
    @FXML private TableColumn MonthlyEndCol;
    @FXML private Tab WeeklyTab;
    @FXML private TableView WeeklyTableView;
    @FXML private TableColumn WeeklyDescriptionCol;
    @FXML private TableColumn WeeklyContactCol;
    @FXML private TableColumn WeeklyStartCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
