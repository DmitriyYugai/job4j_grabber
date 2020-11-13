package ru.job4j.html;

import java.sql.Timestamp;
import java.util.Objects;

public class Post {
    private String desc;
    private Timestamp crated;

    public Post() {
    }

    public Post(String desc, Timestamp crated) {
        this.desc = desc;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(desc, post.desc)
                && Objects.equals(crated, post.crated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(desc, crated);
    }
}
