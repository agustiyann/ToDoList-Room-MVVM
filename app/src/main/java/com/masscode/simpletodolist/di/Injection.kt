package com.masscode.simpletodolist.di

import android.content.Context
import com.masscode.simpletodolist.data.repository.TodoRepository
import com.masscode.simpletodolist.data.source.local.LocalDataSource
import com.masscode.simpletodolist.data.source.local.room.TodoDb

object Injection {

    fun provideRepository(context: Context): TodoRepository {
        val database = TodoDb.getInstance(context)

        val localDataSource = LocalDataSource.getInstance(database.todoDAO())

        return TodoRepository.getInstance(localDataSource)
    }
}