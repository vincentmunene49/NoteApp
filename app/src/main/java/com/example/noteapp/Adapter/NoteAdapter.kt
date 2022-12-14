package com.example.noteapp.Adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.databinding.ListNoteItemsBinding
import com.example.noteapp.db.Note
import kotlin.collections.ArrayList
import kotlin.random.Random

class NoteAdapter(val onCLick: (Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(private val binding: ListNoteItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //find card view
        val cardView = binding.cardView
        fun bind(note: Note) {

            binding.listNoteTitle.text = note.note_title
            binding.listNoteText.text = note.note_text
            binding.root.setOnClickListener {
                onCLick.invoke(note)
            }
        }

        fun randomColor(): Int {
            val list = ArrayList<Int>()
            list.add(R.color.note_color1)
            list.add(R.color.note_color2)
            list.add(R.color.note_color3)
            list.add(R.color.note_color4)
            list.add(R.color.note_color5)
            list.add(R.color.note_color6)

            val seed = System.currentTimeMillis().toInt()
            val randomColor = Random(seed).nextInt(list.size)
            return list[randomColor]

        }


    }
    //using diffutil

    private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }

    val diffUtil = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = ListNoteItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NoteViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = diffUtil.currentList[position]
        holder.bind(note)
        holder.cardView.setCardBackgroundColor(holder.itemView.resources.getColor(holder.randomColor(),null))
    }

    override fun getItemCount() = diffUtil.currentList.size
}