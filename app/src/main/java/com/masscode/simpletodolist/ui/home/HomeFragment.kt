package com.masscode.simpletodolist.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.masscode.simpletodolist.R
import com.masscode.simpletodolist.adapter.ListAdapter
import com.masscode.simpletodolist.databinding.FragmentHomeBinding
import com.masscode.simpletodolist.utils.hideKeyboard
import com.masscode.simpletodolist.utils.shortToast
import com.masscode.simpletodolist.viewmodel.TodoViewModel
import com.masscode.simpletodolist.viewmodel.TodoViewModelFactory
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        setHasOptionsMenu(true)
        activity?.hideKeyboard()
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = TodoViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, viewModelFactory)[TodoViewModel::class.java]

        val mDate = Calendar.getInstance().time
        val formatter = SimpleDateFormat("EEEE, MMM dd yyyy")
        val currentDate = formatter.format(mDate)

        binding.apply {
            date.text = currentDate
            seeAllButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_listFragment)
            )
        }

        viewModel.getAllTodos().observe(viewLifecycleOwner, { list ->
            if (list.isEmpty()) binding.totalTask.text = "0"
            else binding.totalTask.text = list.size.toString()
        })

        viewModel.getAllCompleted().observe(viewLifecycleOwner, { list ->
            if (list.isNotEmpty()) binding.completed.text = list.size.toString()
            else binding.completed.text = "0"
        })
    }
}
