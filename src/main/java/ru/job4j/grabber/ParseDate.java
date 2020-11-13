package ru.job4j.grabber;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ParseDate {
    private Map<String, Integer> months = new HashMap<>();

    public Timestamp parseDate(String str) {
        if (str.startsWith("сегодня")) {
            return getDate(str, LocalDate::now);
        } else if (str.startsWith("вчера")) {
            return getDate(str, () -> LocalDate.now().minusDays(1));
        }
        String[] dateTime = str.split(" ");
        int year = Integer.parseInt(dateTime[2].substring(0, 2)) + 2000;
        init();
        String s = dateTime[1];
        int month = months.get(s);
        int day = Integer.parseInt(dateTime[0]);
        int hours = Integer.parseInt(dateTime[3].split(":")[0]);
        int minutes = Integer.parseInt(dateTime[3].split(":")[1]);
        return Timestamp.valueOf(LocalDateTime.of(year, month, day, hours, minutes));
    }

    private Timestamp getDate(String str, Supplier<LocalDate> sup) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(
                FormatStyle.SHORT);
        String time = str.split(" ")[1];
        LocalTime localTime = LocalTime.parse(time, formatter);
        return Timestamp.valueOf(LocalDateTime.of(sup.get(), localTime));
    }

    private void init() {
        months.put("янв", 1);
        months.put("фев", 2);
        months.put("мар", 3);
        months.put("апр", 4);
        months.put("май", 5);
        months.put("июн", 6);
        months.put("июл", 7);
        months.put("авг", 8);
        months.put("сен", 9);
        months.put("окт", 10);
        months.put("ноя", 11);
        months.put("дек", 12);
    }
}
