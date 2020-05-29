package com.waichee.noteapp02.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.waichee.noteapp02.domain.Note

@Entity(tableName = "notes_table")
data class DatabaseNote constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0L,
    var title: String? = "TITLE",
    var body: String? = "BODY"
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