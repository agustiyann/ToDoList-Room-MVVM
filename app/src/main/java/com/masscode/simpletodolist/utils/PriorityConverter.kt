package com.masscode.simpletodolist.utils

import androidx.room.TypeConverter
import com.masscode.simpletodolist.data.model.Priority

class PriorityConverter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}