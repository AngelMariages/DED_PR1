package uoc.ded.practica.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    public static Date createDate(String stringDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try {
            return sdf.parse(stringDate);
        } catch (ParseException e) {
            System.out.println("ERROR: Couldn't parsing date: " + stringDate);
            e.printStackTrace();

            return null;
        }
    }

    public static LocalDate createLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return LocalDate.parse(date, formatter);
    }
}
