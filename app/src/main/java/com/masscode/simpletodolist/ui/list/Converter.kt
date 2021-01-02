package com.masscode.simpletodolist.ui.list

import androidx.room.TypeConverter
import com.masscode.simpletodolist.data.model.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}