package com.masscode.simpletodolist.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.masscode.simpletodolist.database.Todo
import com.masscode.simpletodolist.database.TodoDAO
import com.masscode.simpletodolist.database.TodoDb
import com.masscode.simpletodolist.database.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    // add repository
    private val repository: TodoRepository
    private val todoDAO: TodoDAO = TodoDb.getInstance(application).todoDAO()

    private var _todos: LiveData<List<Todo>>
    val todos: LiveData<List<Todo>>
        get() = _todos

    // coroutine
    private var vmJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + vmJob)

    init {
        repository = TodoRepository(todoDAO)
        _todos = repository.allTodos
    }

    fun addTodo(title: String, desc: String) {
        uiScope.launch {
            repository.insert(Todo(0, title, desc, false))
        }
    }

    fun updateTodo(id: Int, title: String, desc: String, checked: Boolean) {
        uiScope.launch {
            repository.update(Todo(id, title, desc, checked))
        }
    }

    fun deleteSelected() {
        uiScope.launch {
            repository.deleteSelectedTodos()
        }
    }

    fun clearTodos() {
        uiScope.launch {
            repository.clearTodos()
        }
    }

    override fun onCleared() {
        super.onCleared()
        vmJob.cancel()
    }

}

@Suppress("UNCHECKED_CAST")
class TodoViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoViewModel(application) as T
    }
}