package com.masscode.simpletodolist.data.source.local

import androidx.lifecycle.LiveData
import com.masscode.simpletodolist.data.source.local.entity.Todo
import com.masscode.simpletodolist.data.source.local.room.TodoDAO

class LocalDataSource private constructor(private val todoDAO: TodoDAO) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(todoDAO: TodoDAO): LocalDataSource =
            INSTANCE ?: LocalDataSource(todoDAO)
    }

    fun getAllTodos(): LiveData<List<Todo>> = todoDAO.loadTodos()

    suspend fun insert(todo: Todo) = todoDAO.insertTodo(todo)

    suspend fun update(todo: Todo) = todoDAO.updateTodo(todo)

    suspend fun deleteSelectedTodos() = todoDAO.deleteSelectedTodos()

    suspend fun clearTodos() = todoDAO.clearTodos()
}