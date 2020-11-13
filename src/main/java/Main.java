import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static int prnt(String s) {
        System.out.println(s);
        return 1;
    }

    public static void main(String[] args) {
//        prnt("Hello World");
//        LocalDateTime date = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(
//                FormatStyle.MEDIUM);
//        String text = date.format(formatter);
//        System.out.println(text);
//        String s = "24 нояб. 2020 г., 15:54:00";
//        LocalDateTime localDateTime = LocalDateTime.parse(s, formatter);
//        System.out.println(localDateTime);
        Pattern p1 = Pattern.compile("\\D+");
        Matcher m1 = p1.matcher("24 сен 20, 15:54");
        m1.find();
        String s = m1.group();
        System.out.println(s);
//        24 сен 20, 15:54
    }
}
