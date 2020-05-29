package com.waichee.noteapp02.domain

import android.os.Parcelable
import com.waichee.noteapp02.database.DatabaseNote
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note (
    var id: Long?,
    var title: String?,
    var body: String?
) : Parcelable

fun Note.asDatabaseNote(): DatabaseNote {
    return DatabaseNote(
        id = id,
        title = title,
        body = body
    )
}