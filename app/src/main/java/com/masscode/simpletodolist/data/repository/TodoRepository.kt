package com.masscode.simpletodolist.data.repository

import com.masscode.simpletodolist.data.source.local.entity.Todo
import com.masscode.simpletodolist.data.source.local.room.TodoDAO

class TodoRepository(private val TodoDAO: TodoDAO) {

    val allTodos = TodoDAO.loadTodos()

    suspend fun insert(todo: Todo) {
        TodoDAO.insertTodo(todo)
    }

    suspend fun update(todo: Todo) {
        TodoDAO.updateTodo(todo)
    }

    suspend fun deleteSelectedTodos() {
        TodoDAO.deleteSelectedTodos()
    }

    suspend fun clearTodos() {
        TodoDAO.clearTodos()
    }

}