package com.masscode.simpletodolist.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.masscode.simpletodolist.R
import com.masscode.simpletodolist.databinding.FragmentAddBinding
import com.masscode.simpletodolist.utils.hideKeyboard
import com.masscode.simpletodolist.utils.parsePriority
import com.masscode.simpletodolist.utils.shortToast
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
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater)
        val viewModelFactory = TodoViewModelFactory.getInstance(requireContext())
        todoViewModel = ViewModelProvider(this, viewModelFactory)[TodoViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf("High Priority", "Medium Priority", "Low Priority")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.priorityMenu.setAdapter(adapter)

        binding.submitButton.setOnClickListener {
            val title = binding.title.text.toString()
            val description = binding.description.text.toString()
            val priority = binding.priorityMenu.text.toString()

            if (title.isNotBlank() && description.isNotBlank()) {
                todoViewModel.addTodo(title, description, parsePriority(priority))
                activity?.hideKeyboard()
                findNavController().popBackStack()
            } else {
                context?.shortToast(getString(R.string.fill_all_fields))
            }
        }
    }

}
