package com.masscode.simpletodolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDAO {
    @Query("SELECT * FROM todo")
    fun loadTodos(): LiveData<List<Todo>>

    @Insert
    fun insertTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Query("DELETE FROM todo WHERE checked = 1")
    fun deleteSelectedTodos()

    @Query("DELETE FROM todo")
    fun clearTodos()
}