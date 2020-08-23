package com.masscode.simpletodolist.adapter

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.masscode.simpletodolist.R
import com.masscode.simpletodolist.database.Todo
import com.masscode.simpletodolist.databinding.ItemTodoBinding
import com.masscode.simpletodolist.ui.HomeFragmentDirections
import com.masscode.simpletodolist.viewmodel.TodoViewModel

class ListAdapter(private val viewModel: TodoViewModel) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var dataList = emptyList<Todo>()

    class MyViewHolder(binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val desc = binding.description
        val checkTodo = binding.checkboxTodo
        val editBtn = binding.editButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: ItemTodoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_todo,
            parent,
            false
        )

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]

        holder.title.text = currentItem.title
        holder.desc.text = currentItem.description

        holder.checkTodo.setOnCheckedChangeListener(null)
        if (currentItem.checked) {
            holder.checkTodo.isChecked = true
            holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.desc.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.checkTodo.isChecked = false
            holder.title.paintFlags =
                holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.desc.paintFlags =
                holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        holder.checkTodo.setOnCheckedChangeListener { _, b ->
            if (b) {
                viewModel.updateTodo(
                    currentItem.id,
                    currentItem.title,
                    currentItem.description,
                    true
                )
            } else {
                viewModel.updateTodo(
                    currentItem.id,
                    currentItem.title,
                    currentItem.description,
                    false
                )
            }
            Log.i("checked", "checked " + currentItem.checked)
        }

        holder.editBtn.setOnClickListener { view ->
            view.findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToEditFragment(
                        currentItem.id,
                        currentItem.title,
                        currentItem.description,
                        currentItem.checked
                    )
                )
        }
    }

    fun setData(toDoData: List<Todo>) {
        val toDoDiffUtil = TodoDiffUtil(dataList, toDoData)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = toDoData
        toDoDiffResult.dispatchUpdatesTo(this)
    }

    fun deleteSelectedItems() {
        viewModel.deleteSelected()
        notifyDataSetChanged()
    }
}