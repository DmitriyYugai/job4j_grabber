package ru.job4j.html;

import java.sql.Timestamp;
import java.util.Objects;

public class Post {
    private long id;
    private String name;
    private String desc;
    private String link;
    private Timestamp crated;

    public Post() {
    }

    public Post(String name, String desc, String link, Timestamp crated) {
        this.name = name;
        this.desc = desc;
        this.link = link;
        this.crated = crated;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Timestamp getCrated() {
        return crated;
    }

    public void setCrated(Timestamp crated) {
        this.crated = crated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id
                && Objects.equals(link, post.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link);
    }
}
