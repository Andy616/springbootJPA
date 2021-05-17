package com.andy.springbootjpa.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "published_on", nullable = false)
    private Timestamp publishedOn;

    @Column(name = "last_updated", nullable = false)
    private Timestamp lastUpdated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User author) {
        this.user = author;
    }

    public Timestamp getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Timestamp publishedOn) {
        if (publishedOn == null) {
            Date date = new Date();
            this.publishedOn = new Timestamp(date.getTime());
        } else {
            this.publishedOn = publishedOn;
        }
    }

    public Timestamp getLastUpdated() {
        if (this.lastUpdated == null) {
            return this.publishedOn;
        } else {
            return lastUpdated;

        }
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        if (lastUpdated == null) {
            Date date = new Date();
            this.lastUpdated = new Timestamp(date.getTime());
        } else {
            this.lastUpdated = lastUpdated;
        }
    }

}
