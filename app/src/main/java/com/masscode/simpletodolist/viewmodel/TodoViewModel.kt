package com.masscode.simpletodolist.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.masscode.simpletodolist.data.source.local.entity.Todo
import com.masscode.simpletodolist.data.source.local.room.TodoDAO
import com.masscode.simpletodolist.data.source.local.room.TodoDb
import com.masscode.simpletodolist.data.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    // add repository
    private val repository: TodoRepository
    private val todoDAO: TodoDAO = TodoDb.getInstance(application).todoDAO()

    private var _todos: LiveData<List<Todo>>
    val todos: LiveData<List<Todo>>
        get() = _todos

    init {
        repository = TodoRepository(todoDAO)
        _todos = repository.allTodos
    }

    fun addTodo(title: String, desc: String) {
        viewModelScope.launch {
            repository.insert(Todo(0, title, desc, false))
        }
    }

    fun updateTodo(id: Int, title: String, desc: String, checked: Boolean) {
        viewModelScope.launch {
            repository.update(Todo(id, title, desc, checked))
        }
    }

    fun deleteSelected() {
        viewModelScope.launch {
            repository.deleteSelectedTodos()
        }
    }

    fun clearTodos() {
        viewModelScope.launch {
            repository.clearTodos()
        }
    }
}

@Suppress("UNCHECKED_CAST")
class TodoViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoViewModel(application) as T
    }
}