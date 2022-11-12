package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.NoteRepository.NoteViewModelFactoryProvider
import com.example.noteapp.NoteRepository.Repository
import com.example.noteapp.ViewModel.NoteViewModel
import com.example.noteapp.db.NoteDatabase

class MainActivity : AppCompatActivity() {
    val viewModel: NoteViewModel by lazy {
        val database = NoteDatabase.getDatabase(this)
        val repo = Repository(database)
        val providerFactory = NoteViewModelFactoryProvider(repo)

        ViewModelProvider(this, providerFactory)[NoteViewModel::class.java]

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}