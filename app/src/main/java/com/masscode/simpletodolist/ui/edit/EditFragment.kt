package com.masscode.simpletodolist.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.masscode.simpletodolist.R
import com.masscode.simpletodolist.data.source.local.entity.Todo
import com.masscode.simpletodolist.databinding.FragmentEditBinding
import com.masscode.simpletodolist.utils.hideKeyboard
import com.masscode.simpletodolist.utils.parsePriority
import com.masscode.simpletodolist.utils.shortToast
import com.masscode.simpletodolist.viewmodel.TodoViewModel
import com.masscode.simpletodolist.viewmodel.TodoViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var mTodo: Todo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mTodo = EditFragmentArgs.fromBundle(requireArguments()).todo!!
        // Inflate the layout for this fragment
        binding = FragmentEditBinding.inflate(inflater).apply {
            todo = mTodo
        }

        val viewModelFactory = TodoViewModelFactory.getInstance(requireContext())
        todoViewModel = ViewModelProvider(this, viewModelFactory)[TodoViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener {
            val updatedTitle = binding.title.text.toString()
            val updatedDesc = binding.description.text.toString()
            val priority = binding.priorityMenu.text.toString()

            if (updatedTitle.isNotBlank() && updatedDesc.isNotBlank()) {
                todoViewModel.updateTodo(
                    mTodo.id,
                    updatedTitle,
                    updatedDesc,
                    mTodo.checked,
                    parsePriority(priority)
                )
                activity?.hideKeyboard()
                findNavController().popBackStack()
            } else {
                context?.shortToast(getString(R.string.fill_all_fields))
            }
        }
    }

}
