package com.example.appnoteskotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.appnoteskotlin.R
import com.example.appnoteskotlin.databinding.ActivityAddNoteBinding
import com.example.appnoteskotlin.db.NoteDatabase
import com.example.appnoteskotlin.db.NoteEntity
import com.example.appnoteskotlin.utils.Constants
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddNoteBinding
    private lateinit var noteEntity: NoteEntity
    private val noteDB: NoteDatabase by lazy {
        Room.databaseBuilder(this, NoteDatabase::class.java, Constants.NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textDateTime.text = SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
            .format(Date())

        binding.apply {
            imageSave.setOnClickListener {
                val title = inputNoteTitle.text.toString()
                val subTitle = inputNoteSubtitle.text.toString()
                val note = inputNote.text.toString()
                val time = textDateTime.text.toString()
                if(title.isNotEmpty() || subTitle.isNotEmpty() || note.isNotEmpty()) {
                    noteEntity = NoteEntity(0, title, subTitle, note, time)
                    noteDB.noteDao().insertNote(noteEntity)
                    finish()

                } else {
                    Snackbar.make(it, "Please enter Title or Subtitle or Note", Snackbar.LENGTH_LONG).show()
                }
            }

            imageBack.setOnClickListener {
                finish()
            }
        }

    }

}