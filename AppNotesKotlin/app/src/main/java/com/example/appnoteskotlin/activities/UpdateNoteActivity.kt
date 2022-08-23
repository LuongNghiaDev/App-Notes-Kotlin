package com.example.appnoteskotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.appnoteskotlin.R
import com.example.appnoteskotlin.databinding.ActivityUpdateNoteBinding
import com.example.appnoteskotlin.db.NoteDatabase
import com.example.appnoteskotlin.db.NoteEntity
import com.example.appnoteskotlin.utils.Constants
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding:ActivityUpdateNoteBinding
    private lateinit var noteEntity: NoteEntity
    private val noteDB: NoteDatabase by lazy {
        Room.databaseBuilder(this, NoteDatabase::class.java, Constants.NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private var noteId = 0
    private var defaultTitle = ""
    private var defaultSubTitle = ""
    private var defaultNote = ""
    private var defaultTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            noteId = it.getInt(Constants.BUNDLE_NOTE_ID)
        }

        binding.apply {
            defaultTitle = noteDB.noteDao().getNote(noteId).noteTitle
            defaultSubTitle = noteDB.noteDao().getNote(noteId).noteSubtitle
            defaultNote = noteDB.noteDao().getNote(noteId).note
            defaultTime = noteDB.noteDao().getNote(noteId).dateTime

            inputNoteTitle.setText(defaultTitle)
            inputNoteSubtitle.setText(defaultSubTitle)
            inputNote.setText(defaultNote)
            textDateTime.setText(defaultTime)

            imageDelete.setOnClickListener {
                noteEntity = NoteEntity(noteId, defaultTitle, defaultSubTitle, defaultNote, defaultTime)
                noteDB.noteDao().deleteNote(noteEntity)
                finish()
            }

            imageSave.setOnClickListener {
                val title = inputNoteTitle.text.toString()
                val subTitle = inputNoteSubtitle.text.toString()
                val note = inputNote.text.toString()
                val time = SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                    .format(Date())
                if(title.isNotEmpty() || subTitle.isNotEmpty() || note.isNotEmpty()) {
                    noteEntity = NoteEntity(0, title, subTitle, note, time)
                    noteDB.noteDao().insertNote(noteEntity)
                    finish()

                } else {
                    Snackbar.make(it, "Please enter Title or Subtitle or Note", Snackbar.LENGTH_LONG).show()
                }
            }

        }

    }
}