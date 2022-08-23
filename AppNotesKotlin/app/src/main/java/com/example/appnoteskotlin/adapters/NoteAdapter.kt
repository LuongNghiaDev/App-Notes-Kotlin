package com.example.appnoteskotlin.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appnoteskotlin.activities.UpdateNoteActivity
import com.example.appnoteskotlin.databinding.ItemContainerNoteBinding
import com.example.appnoteskotlin.db.NoteEntity
import com.example.appnoteskotlin.utils.Constants

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private lateinit var binding: ItemContainerNoteBinding
    private lateinit var context: Context

    inner class ViewHolder :RecyclerView.ViewHolder(binding.root) {
        fun bind(item : NoteEntity) {
            binding.apply {
                textTitle.text = item.noteTitle
                textSubtitle.text = item.noteSubtitle
                textDateTime.text = item.dateTime

                root.setOnClickListener {
                    val intent= Intent(context, UpdateNoteActivity::class.java)
                    intent.putExtra(Constants.BUNDLE_NOTE_ID, item.noteId)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        binding = ItemContainerNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}