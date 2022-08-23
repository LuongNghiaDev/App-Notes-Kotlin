package com.example.appnoteskotlin.db

import androidx.room.*
import com.example.appnoteskotlin.utils.Constants

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteEntity: NoteEntity)

    @Update
    fun updateNote(noteEntity: NoteEntity)

    @Delete
    fun deleteNote(noteEntity: NoteEntity)

    @Query("Select * from ${Constants.NOTE_TABLE} order by noteId desc")
    fun getAllNotes(): MutableList<NoteEntity>

    @Query("Select * from ${Constants.NOTE_TABLE} where noteId like :id")
    fun getNote(id: Int): NoteEntity

}