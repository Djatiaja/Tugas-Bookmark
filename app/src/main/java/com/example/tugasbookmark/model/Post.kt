package com.example.localdatabase.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    val id:Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "bookmark")
    val bookmark:Boolean
)
