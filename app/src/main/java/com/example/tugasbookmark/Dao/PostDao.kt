package com.example.tugasbookmark.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.localdatabase.database.Post

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insert(post: Post)

    @Update
    fun update(post: Post)

    @Delete
    fun delete(post: Post)

    @get:Query("SELECT * FROM posts ORDER BY id ASC")
    val allPost: LiveData<List<Post>>

    @get:Query("SELECT * FROM posts WHERE bookmark=true ORDER BY id ASC ")
    val allBookmark: LiveData<List<Post>>

    @Query("UPDATE posts SET bookmark=true where id= :id")
    fun bookmarking(id:Int)

    @Query("UPDATE posts SET bookmark=false where id= :id")
    fun unbookmarking(id:Int)

    @Query("DELETE FROM posts")
    fun nukeTable();
}