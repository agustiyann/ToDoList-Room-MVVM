package com.masscode.simpletodolist.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String,
    var description: String,
    var checked: Boolean
) : Parcelable