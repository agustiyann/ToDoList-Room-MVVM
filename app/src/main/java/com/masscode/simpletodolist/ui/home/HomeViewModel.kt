package com.masscode.simpletodolist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModel(today: String) : ViewModel() {

    private var _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date

    init {
        _date.value = today
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val today: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(today) as T
    }
}