package com.example.noteapp.NoteRepository

import com.example.noteapp.db.Note
import com.example.noteapp.db.NoteDatabase

class Repository(private val note_database:NoteDatabase) {
    suspend fun upsertNote(note:Note) = note_database.note_dao().upsertNote(note)

    fun selectNotes() = note_database.note_dao().selectNotes()
}