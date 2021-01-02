package com.masscode.simpletodolist.data.source.local.room

import android.content.Context
import androidx.room.*
import com.masscode.simpletodolist.data.source.local.entity.Todo
import com.masscode.simpletodolist.ui.list.Converter

@Database(version = 1, entities = [Todo::class], exportSchema = false)
@TypeConverters(Converter::class)
abstract class TodoDb : RoomDatabase() {

    abstract fun todoDAO(): TodoDAO

    companion object { // singleton
        @Volatile
        private var INSTANCE: TodoDb? = null

        fun getInstance(context: Context): TodoDb {
            // memastikan dgn synchronize tidak dijalankan secara concurrent / bersamaan langsung
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDb::class.java,
                        "todo_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}