package ru.job4j.grabber;

import java.sql.Timestamp;
import java.util.Objects;

public class Post {
    private long id;
    private String name;
    private String desc;
    private String link;
    private Timestamp created;

    public Post() {
    }

    public Post(String name, String desc, String link, Timestamp created) {
        this.name = name;
        this.desc = desc;
        this.link = link;
        this.created = created;
    }

    public Post(long id, String name, String desc, String link, Timestamp created) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.link = link;
        this.created = created;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp crated) {
        this.created = crated;
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
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", link='" + link + '\''
                + ", created=" + created
                + '}';
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
