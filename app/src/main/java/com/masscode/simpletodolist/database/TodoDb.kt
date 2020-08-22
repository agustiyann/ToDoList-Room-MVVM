package com.masscode.simpletodolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [Todo::class])
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