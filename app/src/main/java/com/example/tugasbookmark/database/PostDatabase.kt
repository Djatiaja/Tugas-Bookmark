package com.example.tugasbookmark.database

import android.content.Context
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.localdatabase.database.Post
import com.example.tugasbookmark.Dao.PostDao

@androidx.room.Database (entities =[Post::class], version=1, exportSchema=false)
abstract class PostDatabase: RoomDatabase() {
    abstract fun postDao():PostDao?
    companion object{
        @Volatile
        private var INSTANCE:PostDatabase?=null
        fun getDatabase(context : Context):PostDatabase?{
            if (INSTANCE==null){
                synchronized(PostDatabase::class.java){
                    INSTANCE = databaseBuilder(
                        context.applicationContext,
                        PostDatabase::class.java, "posts_database"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}