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

    @Query("SELECT * FROM todo ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<Todo>>
}