package com.example.noteapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.NoteRepository.Repository
import com.example.noteapp.db.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: Repository):ViewModel() {
    //define variable to observe
    val _noteList = noteRepository.selectNotes()

    private val _searchNote = MutableStateFlow<List<Note>>(emptyList())
    val searchNote:MutableStateFlow<List<Note>> = _searchNote

    //insertNote
    fun upsertNote(note: Note) = viewModelScope.launch {
        noteRepository.upsertNote(note)
    }

    //searchNote

    fun searchNote(title:String?,text:String?) = viewModelScope.launch {
        noteRepository.searchNotes(title,text).collect{
            _searchNote.emit(it)
        }
    }
}