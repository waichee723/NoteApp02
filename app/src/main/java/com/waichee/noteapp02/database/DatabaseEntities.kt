package com.waichee.noteapp02.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.waichee.noteapp02.domain.Note

@Entity(tableName = "notes_table")
data class DatabaseNote(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String = "TITLE",
    var body: String = "BODY"
)

fun List<DatabaseNote>.asDomainModel(): List<Note> {
    return map {
        Note(
            id = it.id,
            title = it.title,
            body = it.body
        )
    }
}

fun DatabaseNote.asDomainModel(): Note {
    return Note(
        id = id,
        title = title,
        body = body
    )
}