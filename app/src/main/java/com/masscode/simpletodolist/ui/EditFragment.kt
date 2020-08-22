package com.masscode.simpletodolist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.masscode.simpletodolist.R
import com.masscode.simpletodolist.database.Todo
import com.masscode.simpletodolist.databinding.FragmentEditBinding
import com.masscode.simpletodolist.viewmodel.EditViewModel
import com.masscode.simpletodolist.viewmodel.EditViewModelFactory
import com.masscode.simpletodolist.viewmodel.TodoViewModel
import com.masscode.simpletodolist.viewmodel.TodoViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditBinding.inflate(inflater)

        val todo: Todo

        val id = EditFragmentArgs.fromBundle(requireArguments()).id
        val title = EditFragmentArgs.fromBundle(requireArguments()).title
        val description = EditFragmentArgs.fromBundle(requireArguments()).desc
        val isChecked = EditFragmentArgs.fromBundle(requireArguments()).checked

        todo = Todo(id, title!!, description!!, isChecked)

        val vmFactory = EditViewModelFactory(todo)
        binding.viewModel = ViewModelProvider(this, vmFactory).get(EditViewModel::class.java)

        todoViewModel = ViewModelProvider(
            requireActivity(),
            TodoViewModelFactory(
                requireActivity().application
            )
        ).get(TodoViewModel::class.java)

        binding.submitButton.setOnClickListener {
            val updatedTitle = binding.title.text.toString()
            val updatedDesc = binding.description.text.toString()

            if (updatedTitle.trim().isNotEmpty() && updatedDesc.trim().isNotEmpty()) {
                todoViewModel.updateTodo(id, updatedTitle, updatedDesc, isChecked)
                findNavController().popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    "All fields must be inputted!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }

}
