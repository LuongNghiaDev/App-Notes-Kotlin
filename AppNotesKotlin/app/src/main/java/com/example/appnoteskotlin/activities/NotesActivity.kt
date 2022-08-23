package com.example.appnoteskotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import com.example.appnoteskotlin.R
import com.example.appnoteskotlin.adapters.NoteAdapter
import com.example.appnoteskotlin.databinding.ActivityNotesBinding
import com.example.appnoteskotlin.db.NoteDatabase
import com.example.appnoteskotlin.db.NoteEntity
import com.example.appnoteskotlin.utils.Constants

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private val noteDB: NoteDatabase by lazy {
        Room.databaseBuilder(this, NoteDatabase::class.java, Constants.NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private val noteAdapter by lazy { NoteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageAddNotesMain.setOnClickListener {
            startActivity(Intent(this@NotesActivity, AddNoteActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        checkItem()
    }

    private fun checkItem() {
        binding.apply {
            if(noteDB.noteDao().getAllNotes().isNotEmpty()) {
                notesRecyclerView.visibility = View.VISIBLE
                noteAdapter.differ.submitList(noteDB.noteDao().getAllNotes())
                setUpRecyclerView()
            } else {
                notesRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.notesRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = noteAdapter
        }
    }
}