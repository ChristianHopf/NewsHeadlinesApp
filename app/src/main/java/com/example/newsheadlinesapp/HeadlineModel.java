package com.example.newsheadlinesapp;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="headline")
public class HeadlineModel {
    @PrimaryKey(autoGenerate = true)
    int id;
    private String title;
    private String author;
    private String description;

    /**
     * Ignore this constructor; room will use the one with primarykey id
     * @param title
     * @param author
     * @param description
     */
    @Ignore
    public HeadlineModel(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    /**
     * Constructor to be used by room
     * @param id
     * @param title
     * @param author
     * @param description
     */
    public HeadlineModel(int id, String title, String author, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
