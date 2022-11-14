package com.example.noteapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.Adapter.NoteAdapter
import com.example.noteapp.ViewModel.NoteViewModel
import com.example.noteapp.databinding.FragmentListNotesBinding
import com.example.noteapp.db.Note
import kotlinx.coroutines.flow.collect

class ListNoteFragment:Fragment() {
    private var _binding:FragmentListNotesBinding?=null
    private val binding get() = _binding!!
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //setup adapter
        binding.noteRecylerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        noteAdapter = NoteAdapter {
            val bundle = Bundle().apply {
                putParcelable("note",it)
            }
            findNavController().navigate(R.id.action_listNoteFragment_to_createEditNoteFragment, bundle)
        }
        binding.noteRecylerView.adapter = noteAdapter

        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listNoteFragment_to_createEditNoteFragment)
        }

        //display notes
        lifecycleScope.launchWhenStarted {
            viewModel._noteList.collect{
                noteAdapter.diffUtil.submitList(it)
            }
        }




    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}