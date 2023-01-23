package Utilities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author Ryan Wilkinson
 * C195 - Software II
 */
public class Time {


    /**
     * @param dateTime This utility method will be used to convert the times for appointments into UTC
     * @return
     */
    public static String convertTOUTC(String dateTime){
        Timestamp timestamp = Timestamp.valueOf(String.valueOf(dateTime));
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime zonedUTC = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime ldt = zonedUTC.toLocalDateTime();
        String format = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return format;
    }
}
