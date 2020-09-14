package com.example.demo.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.ConstructorArgs;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class PostDTO {
    private int id;
    private String title;
    private String content;
    private String delete_password;
    private LocalDateTime date;
    private String writer;
    private int user_id;
    private String deleteYn;

    public PostDTO(int postId, String title, String content) {
        this.id=postId;
        this.title=title;
        this.content=content;
    }

    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDelete_password() {
        return delete_password;
    }

    public void setDelete_password(String delete_password) {
        this.delete_password = delete_password;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public PostDTO() {}

    public PostDTO(int id){
        this.id=id;
    }

    public PostDTO(int id, String writer)
    {
        this.id=id;
        this.writer=writer;
    }
    public PostDTO(int id, int user_id)
    {
        this.id=id;
        this.user_id=user_id;
    }

    public PostDTO(int id, String title, String content, String delete_password, String writer,int user_id) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.delete_password = delete_password;
        this.writer = writer;
        this.user_id=user_id;
    }

    public PostDTO(String title,String content,String delete_password,String writer,int user_id) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.delete_password = delete_password;
        this.user_id=user_id;
    }

    public PostDTO(String title, String content, String delete_password, LocalDateTime date, String writer) {
        this.title = title;
        this.content = content;
        this.delete_password = delete_password;
        this.date = date;
        this.writer=writer;
    }
    public PostDTO(int id,String title, String content, String delete_password, LocalDateTime date, String writer) {
        this.id=id;
        this.title = title;
        this.content = content;
        this.delete_password = delete_password;
        this.date = date;
        this.writer=writer;
    }
}