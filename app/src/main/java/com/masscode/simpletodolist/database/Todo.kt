package com.masscode.simpletodolist.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String,
    var description: String,
    var checked: Boolean
)