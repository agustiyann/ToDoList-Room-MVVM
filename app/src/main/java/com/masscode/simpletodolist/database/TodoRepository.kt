package com.masscode.simpletodolist.database

class TodoRepository(private val TodoDAO: TodoDAO) {

    val allTodos = TodoDAO.loadTodos()

    fun insert(todo: Todo) {
        TodoDAO.insertTodo(todo)
    }

    fun update(todo: Todo) {
        TodoDAO.updateTodo(todo)
    }

    fun deleteSelectedTodos() {
        TodoDAO.deleteSelectedTodos()
    }

    fun clearTodos() {
        TodoDAO.clearTodos()
    }

}