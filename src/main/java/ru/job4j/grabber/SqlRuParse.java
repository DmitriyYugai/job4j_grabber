package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {
    private ParseDate pd;

    public SqlRuParse(ParseDate pd) {
        this.pd = pd;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            for (int i = 3; i < row.size(); i++) {
                Element href = row.get(i).child(0);
                Post post = detail(href.attr("href"));
                posts.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post detail(String link) {
        Post post = null;
        try {
            Document doc = Jsoup.connect(link).get();
            Element descElem = doc.select(".msgBody").get(1);
            Element dateElem = doc.select(".msgFooter").get(0);
            Element nameElem = doc.select(".messageHeader").get(0);
            String desc = descElem.text();
            String date = dateElem.text().substring(0, dateElem.text().indexOf('[') - 1);
            String name = nameElem.text().substring(0, nameElem.text().indexOf("[") - 1);
            post = new Post(name, desc, link, pd.parseDate(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
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
        Post post = sqlRuParse.detail(
                "https://www.sql.ru/forum/1330254/java-razrabotchik-ee");
        System.out.println(post.getName());
        System.out.println(post.getLink());
        System.out.println(post.getDesc());
        System.out.println(post.getCreated());

        sqlRuParse.list("https://www.sql.ru/forum/job-offers/13");

    }
}
