package com.example.noteapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.ViewModel.NoteViewModel
import com.example.noteapp.databinding.FragmentCreateEditNoteBinding
import com.example.noteapp.db.Note

class CreateEditNoteFragment:Fragment() {
    private var _binding:FragmentCreateEditNoteBinding?=null
    private val binding get() =  _binding!!
    private lateinit var viewModel: NoteViewModel
    private val args by navArgs<CreateEditNoteFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateEditNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        args.note?.let {
            binding.noteTitle.setText(it.note_title)
            binding.noteText.setText(it.note_text)
        }
        //updating ans inserting note

        binding.saveNote.setOnClickListener{
            val id = args.note?.id ?:0
            val noteTitle = binding.noteTitle.text.toString()
            val noteText = binding.noteText.text.toString()

            Note(id,noteTitle,noteText).also {
                if(noteTitle.isEmpty() && noteText.isEmpty()){
                    Toast.makeText(context, "All fields should be filled", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                viewModel.upsertNote(it)
                findNavController().navigateUp()
            }

        }

    }





    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}