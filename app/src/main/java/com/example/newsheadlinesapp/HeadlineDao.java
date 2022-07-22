package com.example.newsheadlinesapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface HeadlineDao {
    @Query("SELECT * FROM HEADLINE ORDER BY ID")
    List<HeadlineModel> loadAllHeadlines();

    @Insert
    void insertHeadline(HeadlineModel headline);

    @Update
    void updateHeadline(HeadlineModel headline);

    @Delete
    void deleteHeadline(HeadlineModel headline);

    /**
     * Query for opening article dialogs: query by article title
     * @param title
     * @return
     */
    @Query("SELECT * FROM HEADLINE WHERE title = :title")
    HeadlineModel loadHeadlineByTitle(String title);

}
