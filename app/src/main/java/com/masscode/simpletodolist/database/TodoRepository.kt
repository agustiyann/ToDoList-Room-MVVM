package com.masscode.simpletodolist.database

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