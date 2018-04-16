package com.purebook.backend.entity;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
public class BookReview {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "book_id", nullable = false)
    private int bookId;
    @Column(name = "user_id", nullable = false)
    private String userId;
    private String userName;
    private String userAvatar;
    @Column(nullable = false)
    private String review;
    @Column(nullable = false)
    private Timestamp time;
    private String title;

    public BookReview() {}

    public BookReview(int bookId, String userId, String userName, String userAvatar, String review, Timestamp time, String title) {
        this.bookId = bookId;
        this.userId = userId;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.review = review;
        this.time = time;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
