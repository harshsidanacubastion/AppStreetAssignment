package com.project.appstreetassignment.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.project.appstreetassignment.data.model.db.ArticleDbModel;

import java.util.List;

import io.reactivex.Single;


//this is the DAO(Data Access Object) which generates the SQL queries using annotations such as @Insert
//Room uses the DAO to create a clean API for your code.
@Dao
public interface SampleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAtricle(ArticleDbModel articleDbModel);

    @Query("select * from article_table where country =:country and category=:category order by article_id asc")
    Single<List<ArticleDbModel>> getAllArticles(String country, String category);

//    @Query("delete from ImageDbModel")
//    void deleteAll();

//    @Query("select * from ImageDbModel order by word_id asc")
//    Flowable<List<ImageDbModel>> getAllWords();

}

