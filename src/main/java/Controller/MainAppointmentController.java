package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainAppointmentController {

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
}
