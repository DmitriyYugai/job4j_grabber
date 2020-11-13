package ru.job4j.grabber;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {
    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            String url = cfg.getProperty("jdbc.url");
            String login = cfg.getProperty("jdbc.username");
            String password = cfg.getProperty("jdbc.password");
            cnn = DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement ps = cnn.prepareStatement(
                "insert into posts(name, description, link, created) values(?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDesc());
            ps.setString(3, post.getLink());
            ps.setTimestamp(4, post.getCreated());
            ps.execute();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement ps = cnn.prepareStatement(
                "select * from posts")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("link"),
                            rs.getTimestamp("created")
                    );
                    posts.add(post);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(String id) {
        try (PreparedStatement ps = cnn.prepareStatement(
                "select * from posts where id = ?")) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Post post = new Post(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("link"),
                            rs.getTimestamp("created")
                    );
                    post.setId(Integer.parseInt(id));
                    return post;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) throws Exception {
        ParseDate parseDate = new ParseDate();
        SqlRuParse sqlRuParse = new SqlRuParse(parseDate);
        Post post = sqlRuParse.detail(
                "https://www.sql.ru/forum/1330254/java-razrabotchik-ee");
        System.out.println(post.getName());
        System.out.println(post.getLink());
        System.out.println(post.getDesc());
        System.out.println(post.getCreated());
        System.out.println("-------------------------------");
        Properties properties = new Properties();
        properties.load(new BufferedReader(new FileReader("src/main/resources/app.properties")));
        try (PsqlStore store = new PsqlStore(properties)) {
            store.save(post);
            System.out.println(post.getId());
            System.out.println(store.getAll());
            System.out.println(store.findById("1"));
        }
    }
}
