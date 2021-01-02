package com.masscode.simpletodolist.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.masscode.simpletodolist.data.model.Priority

fun Activity.hideKeyboard() {
    val inputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    val currentFocusedView = currentFocus
    currentFocusedView?.let {
        inputMethodManager.hideSoftInputFromWindow(
            currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun Context.shortToast(message: String) = Toast.makeText(this, message, LENGTH_SHORT).show()

fun parsePriority(priority: String): Priority {
    return when(priority){
        "High Priority" -> { Priority.HIGH }
        "Medium Priority" -> { Priority.MEDIUM }
        "Low Priority" -> { Priority.LOW }
        else -> Priority.LOW
    }
}