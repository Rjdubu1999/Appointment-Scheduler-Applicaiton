package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private final SimpleIntegerProperty appointmentID = new SimpleIntegerProperty();
    private final SimpleIntegerProperty appointmentCustomerID = new SimpleIntegerProperty();
    private final SimpleStringProperty appointmentStart = new SimpleStringProperty();
    private final SimpleStringProperty appointmentEnd = new SimpleStringProperty();
    private final SimpleStringProperty appointmentDescription = new SimpleStringProperty();
    private final SimpleStringProperty appointmentTitle = new SimpleStringProperty();
    private final SimpleStringProperty appointmentLocation = new SimpleStringProperty();
    private final SimpleStringProperty appointmentContact = new SimpleStringProperty();


    public Appointment(int id, int customerID, String start, String end, String description, String title, String location, String contact){
    setAppointmentID(id);
    setAppoitmentCustomerID(customerID);
    setAppointmentStart(start);
    setAppointmentEnd(end);
    setAppointmentDescription(description);
    setAppointmentTitle(title);
    setAppointmentLocation(location);
    setAppointmentContact(contact);

    }

    public int getAppointmentId(){
        return appointmentID.get();
    }
    public int getAppointmentCustomerID(){
        return appointmentCustomerID.get();
    }
    public String getAppointmentStart(){
        return appointmentStart.get();
    }
    public String getAppointmentEnd(){
        return appointmentEnd.get();
    }
    public String getAppointmentDescription(){
        return appointmentDescription.get();
    }
    public String getAppointmentTitle(){
        return appointmentTitle.get();
    }
    public String getAppointmentLocation(){
        return appointmentLocation.get();
    }
    public String getAppointmentContact(){
        return appointmentContact.get();
    }

    public void setAppointmentID(int appointmentID){
        this.appointmentID.set(appointmentID);
    }
    public void setAppoitmentCustomerID(int customerID){
        this.appointmentCustomerID.set(customerID);
    }
    public void setAppointmentTitle(String appointmentTitle){
        this.appointmentTitle.set(appointmentTitle);
    }
    public void setAppointmentStart(String appointmentStart){
        this.appointmentStart.set(appointmentStart);
    }
    public void setAppointmentEnd(String appointmentEnd){
        this.appointmentEnd.set(appointmentEnd);
    }
    public void setAppointmentLocation(String appointmentLocation){
        this.appointmentLocation.set(appointmentLocation);
    }
    public void setAppointmentContact(String appointmentContact){
        this.appointmentContact.set(appointmentContact);
    }
    public void setAppointmentDescription(String appointmentDescription){
        this.appointmentDescription.set(appointmentDescription);
    }
    public StringProperty getAppointmentStartProperty(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime localDateTime = LocalDateTime.parse(this.appointmentStart.getValue(), dateTimeFormatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"));
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime utc = zonedDateTime.withZoneSameInstant(zoneId);
        StringProperty date = new SimpleStringProperty(utc.toLocalDateTime().toString());
        return date;
    }

    public StringProperty getAppointmentEndProperty(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime localDateTime = LocalDateTime.parse(this.appointmentStart.getValue(), dateTimeFormatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"));
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime utc = zonedDateTime.withZoneSameInstant(zoneId);
        StringProperty date = new SimpleStringProperty(utc.toLocalDateTime().toString());
        return date;
    }
    public LocalDate getDateOnly() {
        Timestamp timestamp = Timestamp.valueOf(this.appointmentStart.get());
        ZonedDateTime zonedDateTime;
        ZoneId zoneId;
        LocalDate localDate;
        if (this.appointmentLocation.get().equals("New York")) {
            zoneId = ZoneId.of("America/New_York");
        } else if (this.appointmentLocation.get().equals("Phoenix")){
                zoneId = ZoneId.of("America/Phoenix");
            } else {
                zoneId = ZoneId.of("Europe/London");
            }
            zonedDateTime = timestamp.toLocalDateTime().atZone(zoneId);
            localDate = zonedDateTime.toLocalDate();
            return localDate;
        }

        public String getTimeOnly() {
            Timestamp timestamp = Timestamp.valueOf(this.appointmentStart.get());
            ZonedDateTime zonedDateTime;
            ZoneId zoneId;
            LocalTime localTime;
            if (this.appointmentLocation.get().equals("New York")) {
                zoneId = ZoneId.of("America/New_York");
                zonedDateTime = timestamp.toLocalDateTime().atZone(zoneId);
                localTime = zonedDateTime.toLocalTime().minusHours(4);
            } else if (this.appointmentLocation.get().equals("Phoenix")) {
                zoneId = ZoneId.of("America/Phoenix");
                zonedDateTime = timestamp.toLocalDateTime().atZone(zoneId);
                localTime = zonedDateTime.toLocalTime().minusHours(7);
            } else {
                zoneId = ZoneId.of("Europe/London");
                zonedDateTime = timestamp.toLocalDateTime().atZone(zoneId);
                localTime = zonedDateTime.toLocalTime().plusHours(1);
            }
            int hours = Integer.parseInt(localTime.toString().split(":")[0]);
            if (hours > 12) {
                hours -= 12;
            }
            String AMPM;
            if (hours < 9 || hours == 12) {
                AMPM = "PM";
            } else {
                AMPM = "AM";
            }
            String time = hours + ":00" + AMPM;
            return time;
        }

        public String getAppointmentInFifteen(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:S");
        LocalDateTime localDateTime = LocalDateTime.parse(this.appointmentStart.getValue(), dateTimeFormatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"));
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime utc = zonedDateTime.withZoneSameInstant(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm");
        LocalTime localTime = LocalTime.parse(utc.toString().substring(11,16), formatter);
        return localTime.toString();
        }



    public StringProperty getAppointmentTitleProperty(){
        return this.appointmentTitle;
    }
    public StringProperty getAppointmentDescriptionProperty(){
        return this.appointmentDescription;
    }
    public StringProperty getAppointmentLocationProperty(){
        return this.appointmentLocation;
    }
    public StringProperty getAppointmentContactProperty(){
        return this.appointmentContact;
    }

}
