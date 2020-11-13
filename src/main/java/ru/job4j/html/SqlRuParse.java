package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class SqlRuParse {
    private ParseDate pd;

    public SqlRuParse(ParseDate pd) {
        this.pd = pd;
    }

    public Set<Post> parsePages() throws Exception {
        for (int i = 1; i <= 5; i++) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                Elements siblings = td.nextElementSiblings();
                Element date = siblings.get(3);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                System.out.println(date.text());
            }
        }
        return null;
    }

    private TwoTuple getDetail(String link) throws Exception {
        Document doc = Jsoup.connect(link).get();
        Element descElem = doc.select(".msgBody").get(1);
        Element dateElem = doc.select(".msgFooter").get(0);
        String desc = descElem.text();
        String date = dateElem.text().substring(0, dateElem.text().indexOf('[') - 1);
        return new TwoTuple(desc, pd.parseDate(date));
    }

    private static class TwoTuple {
        private String desc;
        private Timestamp created;

        public TwoTuple(String desc, Timestamp created) {
            this.desc = desc;
            this.created = created;
        }

        @Override
        public String toString() {
            return "TwoTuple{"
                    + "desc='" + desc + '\''
                    + ", created=" + created
                    + '}';
        }
    }

    public static void main(String[] args) throws Exception {
//        for (int i = 1; i <= 5; i++) {
//            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
//            Elements row = doc.select(".postslisttopic");
//            for (Element td : row) {
//                Element href = td.child(0);
//                Elements siblings = td.nextElementSiblings();
//                Element date = siblings.get(3);
//                System.out.println(href.attr("href"));
//                System.out.println(href.text());
//                System.out.println(date.text());
//            }
//        }

        ParseDate parseDate = new ParseDate();
        System.out.println(parseDate.parseDate("сегодня, 16:01"));
        System.out.println(parseDate.parseDate("вчера, 22:01"));
        System.out.println(parseDate.parseDate("6 ноя 20, 10:18"));

        SqlRuParse sqlRuParse = new SqlRuParse(parseDate);
        System.out.println(sqlRuParse.getDetail(
                "https://www.sql.ru/forum/1330328/back-end-razrabotchik-pl-sql"));

    }
}
