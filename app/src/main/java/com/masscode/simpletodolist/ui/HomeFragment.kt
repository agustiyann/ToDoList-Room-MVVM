package com.masscode.simpletodolist.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.masscode.simpletodolist.R

import com.masscode.simpletodolist.databinding.FragmentHomeBinding
import com.masscode.simpletodolist.adapter.TodoAdapter
import com.masscode.simpletodolist.viewmodel.HomeViewModel
import com.masscode.simpletodolist.viewmodel.HomeViewModelFactory
import com.masscode.simpletodolist.viewmodel.TodoViewModel
import com.masscode.simpletodolist.viewmodel.TodoViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mAdapter: TodoAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    private lateinit var viewModel: TodoViewModel

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)

        viewModel = ViewModelProvider(
            requireActivity(),
            TodoViewModelFactory(
                requireActivity().application
            )
        ).get(TodoViewModel::class.java)

        mLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mAdapter = TodoAdapter(viewModel)

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("EEEE, dd-MM-yyyy")
        val currentDate = formatter.format(date)

        val vmFactory = HomeViewModelFactory(currentDate)
        binding.viewModel = ViewModelProvider(this, vmFactory).get(HomeViewModel::class.java)

        binding.rvTodo.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            adapter = mAdapter
        }

        binding.addTaskBtn.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_addFragment)
        )

        viewModel.todos.observe(requireActivity(), Observer { list ->
            mAdapter.submitList(list.toMutableList())

            if (list.isEmpty()) {
                binding.noDataImage.visibility = View.VISIBLE
            } else {
                binding.noDataImage.visibility = View.GONE
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val delete: String

        when (item.itemId) {
            R.id.delete_selected -> {
                delete = "selected"
                showDialog(delete)
            }

            R.id.delete_all -> {
                delete = "all"
                showDialog(delete)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDialog(delete: String) {
        if (delete == "selected") {

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Delete Checked Lists")
            builder.setMessage("All checked lists will be deleted. Are you sure?")
            builder.setPositiveButton(android.R.string.yes) { _, _ ->
                viewModel.deleteSelected()
                mAdapter.notifyDataSetChanged()

                Toast.makeText(
                    requireContext(),
                    "All checked lists have been deleted.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            builder.setNegativeButton(android.R.string.no) { dialog, _ ->
                dialog.cancel()
            }
            builder.show()

        } else if (delete == "all") {

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Clear Lists")
            builder.setMessage("All lists will be deleted. Are you sure?")
            builder.setPositiveButton(android.R.string.yes) { _, _ ->
                viewModel.clearTodos()
                mAdapter.notifyDataSetChanged()

                Toast.makeText(
                    requireContext(),
                    "All lists have been deleted. Add your To Do!",
                    Toast.LENGTH_LONG
                ).show()
            }
            builder.setNegativeButton(android.R.string.no) { dialog, _ ->
                dialog.cancel()
            }
            builder.show()

        }
    }

}
