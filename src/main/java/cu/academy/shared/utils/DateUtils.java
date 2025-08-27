package cu.academy.shared.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String dateTimeFormat = "yyyy/MM/dd HH:mm:ss";

    public static String getCurrentDateFormat(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now().format(formatter);
    }

    public static String getDateFormat(LocalDateTime dateTime, String format) {
        if (dateTime == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }

    public static boolean datesAreEquals(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        return dateTime1.format(dateTimeFormatter).compareTo(dateTime2.format(dateTimeFormatter)) == 0;
    }

}
