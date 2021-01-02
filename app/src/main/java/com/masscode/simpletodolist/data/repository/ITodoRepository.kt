package com.masscode.simpletodolist.data.repository

import androidx.lifecycle.LiveData
import com.masscode.simpletodolist.data.source.local.entity.Todo

interface ITodoRepository {

    fun getAllTodos(): LiveData<List<Todo>>

    fun getAllCompleted(): LiveData<List<Todo>>

    suspend fun insert(todo: Todo)

    suspend fun update(todo: Todo)

    suspend fun deleteSelectedTodos()

    suspend fun clearTodos()

    fun sortByHighPriority(): LiveData<List<Todo>>

    fun sortByLowPriority(): LiveData<List<Todo>>
}