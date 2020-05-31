package com.waichee.noteapp02.domain

import android.os.Parcelable
import com.waichee.noteapp02.database.DatabaseNote
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note (
    var title: String,
    var body: String,
    var id: Long
) : Parcelable

fun Note.asDatabaseNote(): DatabaseNote {
    return DatabaseNote(
        id = id,
        title = title,
        body = body
    )
}