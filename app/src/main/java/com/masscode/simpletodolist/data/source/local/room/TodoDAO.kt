package com.masscode.simpletodolist.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.masscode.simpletodolist.data.source.local.entity.Todo

@Dao
interface TodoDAO {
    @Query("SELECT * FROM todo")
    fun loadTodos(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo WHERE checked = 1")
    fun loadCompleted(): LiveData<List<Todo>>

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