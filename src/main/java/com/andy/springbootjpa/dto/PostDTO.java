package com.andy.springbootjpa.dto;

import com.andy.springbootjpa.model.Post;
import java.sql.Timestamp;
import java.util.Date;

public class PostDTO {

    private String id;
    private String user_id;
    private String content;
    private Timestamp publishedOn;
    private Timestamp lastUpdated;


    public PostDTO() {
    }

    public static PostDTO createDTO(Post post){
        PostDTO dto = new PostDTO();
        dto.setId(post.getId().toString());
        dto.setUser_id(post.getUser().getId().toString());
        dto.setContent(post.getContent());
        dto.setPublishedOn(post.getPublishedOn());
        dto.setLastUpdated(post.getLastUpdated());
        return dto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getPublishedOn() {
        return this.publishedOn;
    }

    public void setPublishedOn(Timestamp publishedOn) {
        if (publishedOn == null){
            Date date = new Date();
            this.publishedOn = new Timestamp(date.getTime());
        }else {
            this.publishedOn = publishedOn;
        }
    }

    public Timestamp getLastUpdated() {
        if (this.lastUpdated == null){
            return this.publishedOn;
        }else{
            return lastUpdated;
        }
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        if (lastUpdated == null){
            Date date = new Date();
            this.lastUpdated = new Timestamp(date.getTime());
        }else {
            this.lastUpdated = lastUpdated;
        }
    }
}
