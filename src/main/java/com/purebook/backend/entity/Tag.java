package com.purebook.backend.entity;

import javax.persistence.*;

@Entity
public class Tag {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "book_id")
    private int bookId;
    @Column(nullable = false)
    private String field;
    private int count;

    public Tag() {}

    public Tag(String field, int count) { this.field = field; this.count = count; }

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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
