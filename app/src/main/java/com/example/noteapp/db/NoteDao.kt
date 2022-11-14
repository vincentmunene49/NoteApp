package com.example.noteapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    //insert/update note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNote(note: Note)

    //select all notes
    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun selectNotes(): Flow<List<Note>>

    //search
    @Query("SELECT * FROM notes_table WHERE note_title LIKE '%'||:searchQuery||'%' OR note_text LIKE '%'||:noteText||'%'")
    fun searchNote(searchQuery:String?,noteText:String?): Flow<List<Note>>

}