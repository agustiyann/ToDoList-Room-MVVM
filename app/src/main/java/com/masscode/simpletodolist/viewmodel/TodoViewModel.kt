package com.masscode.simpletodolist.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.masscode.simpletodolist.data.repository.TodoRepository
import com.masscode.simpletodolist.data.source.local.entity.Todo
import com.masscode.simpletodolist.di.Injection
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository): ViewModel() {

    fun getAllTodos(): LiveData<List<Todo>> = repository.getAllTodos()

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
class TodoViewModelFactory(private val mTodoRepository: TodoRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: TodoViewModelFactory? = null

        fun getInstance(context: Context): TodoViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: TodoViewModelFactory(Injection.provideRepository(context))
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoViewModel(mTodoRepository) as T
    }
}