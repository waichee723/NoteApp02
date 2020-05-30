package com.waichee.noteapp02.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.waichee.noteapp02.domain.Note

@Dao
interface NoteDao {
    @Query("select * from notes_table")
    fun getNotes(): LiveData<List<DatabaseNote>>

    @Insert
    fun insert(databaseNote: DatabaseNote): Long

    @Update
    fun update(databaseNote: DatabaseNote)

    @Query("SELECT * from notes_table WHERE id = :key")
    fun get(key: Long): Note?
}

@Database(entities = [DatabaseNote::class], version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao
}

private lateinit var INSTANCE: NotesDatabase

fun getDatabase(context: Context): NotesDatabase {
    synchronized(NotesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            NotesDatabase::class.java,
            "notes_table").build()
        }
    }
    return INSTANCE
}