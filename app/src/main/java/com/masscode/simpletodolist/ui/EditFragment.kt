package com.masscode.simpletodolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.masscode.simpletodolist.databinding.FragmentEditBinding
import com.masscode.simpletodolist.utils.hideKeyboard
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

        val todo = EditFragmentArgs.fromBundle(requireArguments()).todo

        val vmFactory = EditViewModelFactory(todo!!)
        binding.viewModel = ViewModelProvider(this, vmFactory).get(EditViewModel::class.java)
        binding.lifecycleOwner = this

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
                todoViewModel.updateTodo(todo.id, updatedTitle, updatedDesc, todo.checked)
                hideKeyboard(requireActivity())
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return binding.root
    }

}
