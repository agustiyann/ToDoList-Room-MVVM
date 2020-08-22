package com.masscode.simpletodolist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.masscode.simpletodolist.databinding.FragmentAddBinding
import com.masscode.simpletodolist.viewmodel.TodoViewModel
import com.masscode.simpletodolist.viewmodel.TodoViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater)

        todoViewModel = ViewModelProvider(
            requireActivity(),
            TodoViewModelFactory(
                requireActivity().application
            )
        ).get(TodoViewModel::class.java)

        binding.submitButton.setOnClickListener {
            val title = binding.title.text.toString()
            val description = binding.description.text.toString()

            if (title.trim().isNotEmpty() && description.trim().isNotEmpty()) {
                todoViewModel.addTodo(title, description)
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
