package Model;

import Utilities.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.LocalDateStringConverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DataBaseAppointment {
   /**
    public static ObservableList<Appointment> getMonthlyAppointments(int id) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1);
        try {
            Statement statement = DataBaseConnection.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE customerId = '" + id + "' AND" + "start >= '" + start + "' AND start <= '" + end + "'";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                appointment = new Appointment(resultSet.getInt("appointmentId"), resultSet.getInt("customerId"), resultSet.getString("start"), resultSet.getString("end"), resultSet.getString("description"), resultSet.getString("title"), resultSet.getString("location"), resultSet.getString("contact"));
                appointments.add(appointment);
            }
            statement.close();
            return appointments;
        } catch (SQLException sqlException) {
            System.out.println("Exception : " + sqlException.getMessage());
            return null;
        }

    }

    public static ObservableList<Appointment> getWeeklyAppointments(int id) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusWeeks(1);
        try {
            Statement statement = DataBaseConnection.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE customerId = '" + id + "' AND" + "start >= '" + start + "' AND start <= '" + end + "'";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                appointment = new Appointment(resultSet.getInt("appointmentId"), resultSet.getInt("customerId"), resultSet.getString("start"), resultSet.getString("end"), resultSet.getString("description"), resultSet.getString("title"), resultSet.getString("location"), resultSet.getString("contact"));
                appointments.add(appointment);

            }
            statement.close();
            return appointments;
        } catch (SQLException sqlException) {
            System.out.println("Exception : " + sqlException.getMessage());
            return null;
        }
    }


    public static Appointment appointmentFifteenMinutes() {
        Appointment appointment;
        LocalDateTime current = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = current.atZone(zoneId);
        LocalDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        String user = DatabaseUser.getActiveUser().getUsername();
        try {
            Statement statement = DataBaseConnection.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE start BETWEEN = '" + current + "' AND '" + localDateTime + "'AND'" + "contact='" + user + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                appointment = new Appointment(resultSet.getInt("appointmentId"), resultSet.getInt("customerId"), resultSet.getString("start"), resultSet.getString("end"), resultSet.getString("description"), resultSet.getString("title"), resultSet.getString("location"), resultSet.getString("contact"));
                return appointment;
            }
        } catch (SQLException sqlException) {
            System.out.println("Exception : " + sqlException.getMessage());

        }
        return null;
    }

    public static String createTimeStamp(String date, String time, String location, boolean startMode){
        String hour = time.split(":")[0];
        int parseHour = Integer.parseInt(hour);
        if(parseHour < 9){
            parseHour +=12;
        }
        if(!startMode){
            parseHour+=1;
        }
        String rawDate = String.format("%s %02d:%s", date, parseHour,"00");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(rawDate, dateTimeFormatter);
        ZoneId zoneid;
        if(location.equals("New York")){
            zoneid = ZoneId.of("America/New_York");
        }else if(location.equals("Phoenix")){
            zoneid = ZoneId.of("America/Phoenix");
        }else{
            zoneid = ZoneId.of("Europe/London");
        }
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneid);
        ZonedDateTime utc = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        localDateTime = utc.toLocalDateTime();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        return timestamp.toString();

    }

    public static boolean appointmentsOverlap(int id, String location, String date, String time) {
        String start = createTimeStamp(date, time, location, true);
        try {
            Statement statement = DataBaseConnection.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE start = '" + start + "' AND location= '" + location + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                if (resultSet.getInt("appointmentId") == id) {
                    statement.close();
                    return false;
                }
                statement.close();
                return true;
            } else {
                statement.close();
                return false;

            }

        }catch (SQLException sqlException){
            System.out.println("Exception =" + sqlException.getMessage());
            return true;
        }
    }
    public static boolean saveAppointment(int id, String type, String contact, String location, String date, String time){
        String title = type.split(":")[0];
        String description = type.split(":")[1];
        String timestampStart = createTimeStamp(date, time, location, true);
        String timeStampEnd = createTimeStamp(date, time, location, false);

    try {
        Statement statement = DataBaseConnection.getConnection().createStatement();
        String query = "INSERT INTO appointment SET customerId= '" + id + "', title= '" + title + "', description= '" + description + "', contact= '" + contact + "', location= '" + location + "', start" + timestampStart + "', end='" + timeStampEnd + "', url='', createDate=NOW(), createdBy = '', lastUpdate=NOW(), lastUpdateBy= ''";
        int update = statement.executeUpdate(query);
        if (update == 1) {
            return true;
        }
    }catch (SQLException sqlException){
        System.out.println("Exception : "+ sqlException.getMessage());
    }
    return false;
    }

    public static boolean deleteAppointment(int id){
        try{
            Statement statement = DataBaseConnection.getConnection().createStatement();
            String query = "DELETE FROM appointment WHERE appointmentId = " + id;
            int update = statement.executeUpdate(query);
            if(update == 1){
                return true;
            }
        }catch (SQLException sqlException){
            System.out.println("Exception: " + sqlException.getMessage());
        }
        return false;
    }
    public static boolean updateAppointment(int id, String type, String contact, String location, String date, String time){
        String title = type.split(":")[0];
        String description = type.split(":")[1];
        String timestampStart = createTimeStamp(date, time, location, true);
        String timeStampEnd = createTimeStamp(date, time, location, false);
        try{
            Statement statement = DataBaseConnection.getConnection().createStatement();
            String query = "UPDATE appointment SET title'" + title + "', description='" + description + "', contact='" + contact + "', location='" + location + "', timeStampStart='" + timestampStart + "', timeStampEnd='" + timeStampEnd + "' WHERE " + "appointmentId= '" + id + "'";
            int update = statement.executeUpdate(query);
            if(update == 1){
                return true;
            }
        }catch (SQLException sqlException){
            System.out.println("Exception: " + sqlException.getMessage());
        }
        return false;
    }
**/
}

