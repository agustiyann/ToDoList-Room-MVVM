package com.masscode.simpletodolist.adapter

import android.graphics.Paint
import android.util.Log
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.masscode.simpletodolist.R
import com.masscode.simpletodolist.data.model.Priority
import com.masscode.simpletodolist.data.source.local.entity.Todo
import com.masscode.simpletodolist.ui.home.HomeFragmentDirections
import com.masscode.simpletodolist.ui.list.ListFragmentDirections
import com.masscode.simpletodolist.viewmodel.TodoViewModel

@BindingAdapter(value = ["todo", "vm"])
fun isChecking(checkBox: CheckBox, todo: Todo, viewModel: TodoViewModel) {
    checkBox.setOnCheckedChangeListener(null)
    checkBox.isChecked = todo.checked

    checkBox.setOnCheckedChangeListener { _, b ->
        if (b) {
            viewModel.updateTodo(
                todo.id,
                todo.title,
                todo.description,
                true,
                todo.priority
            )
        } else {
            viewModel.updateTodo(
                todo.id,
                todo.title,
                todo.description,
                false,
                todo.priority
            )
        }
        Log.i("checked", "checked " + todo.checked)
    }
}

@BindingAdapter("android:strikeThrough")
fun isStriked(textView: TextView, isCheck: Boolean) {
    if (isCheck) {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        textView.paintFlags =
            textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}

@BindingAdapter("android:goToEdit")
fun goToEditFragment(view: LinearLayout, todo: Todo) {
    view.setOnClickListener { view ->
        view.findNavController()
            .navigate(ListFragmentDirections.actionListFragmentToEditFragment(todo))
    }
}

@BindingAdapter("android:priorityText")
fun priorityText(autoCompleteTextView: AutoCompleteTextView, text: Priority) {
    val items = listOf("High Priority", "Medium Priority", "Low Priority")
    val adapter = ArrayAdapter(autoCompleteTextView.context, R.layout.list_item, items)
    autoCompleteTextView.setAdapter(adapter)

    when (text) {
        Priority.HIGH -> autoCompleteTextView.setHint("High Priority")
        Priority.MEDIUM -> autoCompleteTextView.setHint("Medium Priority")
        Priority.LOW -> autoCompleteTextView.setHint("Low Priority")
    }
}