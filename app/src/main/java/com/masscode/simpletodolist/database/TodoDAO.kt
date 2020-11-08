package com.masscode.simpletodolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDAO {
    @Query("SELECT * FROM todo")
    fun loadTodos(): LiveData<List<Todo>>

    @Insert
    suspend fun insertTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("DELETE FROM todo WHERE checked = 1")
    suspend fun deleteSelectedTodos()

    @Query("DELETE FROM todo")
    suspend fun clearTodos()
}