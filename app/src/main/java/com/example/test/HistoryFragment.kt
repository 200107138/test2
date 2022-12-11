package com.example.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.data.Result
import com.example.test.data.Type
import com.example.test.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    lateinit var mUserViewModel: HistoryViewModel
    lateinit var binding: FragmentHistoryBinding
    val args: HistoryFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        // Recyclerview


        val adapter = ResultAdapter(ResultListener { result ->
            mUserViewModel.deleteUser(result)

        })

        binding.recyclerview.adapter = adapter
        binding.setLifecycleOwner(this)
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        mUserViewModel = ViewModelProvider(
            this, HistoryViewModelFactory(
                requireActivity().application,
                args.type,
            )
        )[HistoryViewModel::class.java]

        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })


        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        // Setup a click listener for the Submit and Skip buttons.




        (activity as AppCompatActivity).supportActionBar?.title = "History"

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here

            }



            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        activity?.onBackPressed()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        binding.lifecycleOwner = viewLifecycleOwner
    }

}


