package com.example.appnoteskotlin.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.appnoteskotlin.utils.Constants

@Entity(tableName = Constants.NOTE_TABLE)
data class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    val noteId: Int,
    @ColumnInfo(name = "note_title")
    val noteTitle: String,
    @ColumnInfo(name = "note_subtitle")
    val noteSubtitle: String,
    @ColumnInfo(name = "note")
    val note: String,
    @ColumnInfo(name = "date_time")
    val dateTime: String,
)