package com.masscode.simpletodolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.masscode.simpletodolist.database.Todo

class EditViewModel(todo: Todo) : ViewModel() {

    private var _todo = MutableLiveData<Todo>()
    val todo: LiveData<Todo>
        get() = _todo

    init {
        _todo.value = todo
    }

}

@Suppress("UNCHECKED_CAST")
class EditViewModelFactory(private val todo: Todo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditViewModel(todo) as T
    }
}