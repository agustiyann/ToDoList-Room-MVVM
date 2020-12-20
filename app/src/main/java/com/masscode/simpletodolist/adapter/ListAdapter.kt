package com.masscode.simpletodolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.masscode.simpletodolist.R
import com.masscode.simpletodolist.data.source.local.entity.Todo
import com.masscode.simpletodolist.databinding.ItemTodoBinding
import com.masscode.simpletodolist.viewmodel.TodoViewModel

class ListAdapter(private val viewModel: TodoViewModel) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var dataList = emptyList<Todo>()

    class MyViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo, viewModel: TodoViewModel) {
            binding.todo = todo
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
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
        holder.bind(currentItem, viewModel)
    }

    fun setData(toDoData: List<Todo>) {
        val toDoDiffUtil = TodoDiffUtil(dataList, toDoData)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = toDoData
        toDoDiffResult.dispatchUpdatesTo(this)
    }

}