package com.masscode.simpletodolist.adapter

import android.graphics.Paint
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.masscode.simpletodolist.data.source.local.entity.Todo
import com.masscode.simpletodolist.ui.home.HomeFragmentDirections
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
                true
            )
        } else {
            viewModel.updateTodo(
                todo.id,
                todo.title,
                todo.description,
                false
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
fun goToEditFragment(imageView: ImageView, todo: Todo) {
    imageView.setOnClickListener { view ->
        view.findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToEditFragment(todo))
    }
}