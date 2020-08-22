package com.masscode.simpletodolist.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.toColorInt
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.masscode.simpletodolist.R
import com.masscode.simpletodolist.database.Todo
import com.masscode.simpletodolist.databinding.ItemTodoBinding
import com.masscode.simpletodolist.ui.HomeFragmentDirections
import com.masscode.simpletodolist.viewmodel.TodoViewModel

class TodoAdapter(private val viewModel: TodoViewModel) :
    ListAdapter<Todo, TodoAdapter.TodoViewHolder>(
        TodoDiffcallback()
    ) {

    class TodoViewHolder(binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val desc = binding.description
        val checkTodo = binding.checkboxTodo
        val editBtn = binding.editButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view: ItemTodoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_todo,
            parent,
            false
        )

        return TodoViewHolder(
            view
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val id = getItem(position).id
        val title = getItem(position).title
        val description = getItem(position).description
        val isChecked = getItem(position).checked

        holder.title.text = title //viewModel.todos.value!![position].task
        holder.desc.text = description

        holder.editBtn.setOnTouchListener { view, motionEvent ->
            val color = "#ef5777"
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    val imgView: ImageView = view as ImageView
                    imgView.drawable.setColorFilter(color.toColorInt(), PorterDuff.Mode.SRC_ATOP)
                    imgView.invalidate()
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val imgView: ImageView = view as ImageView
                    imgView.drawable.clearColorFilter()
                    imgView.invalidate()
                }
            }

            return@setOnTouchListener false
        }

        holder.checkTodo.setOnCheckedChangeListener { _, b ->
            if (b) {
                viewModel.updateTodo(
                    id,
                    getItem(position).title,
                    getItem(position).description,
                    true
                )
                holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.desc.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                viewModel.updateTodo(
                    id,
                    getItem(position).title,
                    getItem(position).description,
                    false
                )
                holder.title.paintFlags =
                    holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                holder.desc.paintFlags =
                    holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            Log.i("checked", "checked " + getItem(position).checked)
        }

        holder.checkTodo.isChecked = isChecked

        holder.editBtn.setOnClickListener { view ->
            view.findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToEditFragment(
                        id,
                        title,
                        description,
                        isChecked
                    )
                )
        }
    }
}

class TodoDiffcallback : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}