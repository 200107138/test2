package com.example.test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.SecondGameViewModel
import com.example.test.databinding.FragmentSecondGameBinding
import com.example.test.databinding.FragmentSecondGameHistoryBinding

class SecondGameHistoryFragment : Fragment(), ListAdapter.OnItemClickListener {

    private lateinit var mUserViewModel: SecondGameViewModel
    private lateinit var binding: FragmentSecondGameHistoryBinding
    private val adapter = ListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second_game_history, container, false)
        // Recyclerview

        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        mUserViewModel = ViewModelProvider(this).get(SecondGameViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { result ->
            adapter.setData(result)
        })

        return binding.root

    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(result: Result) {
        mUserViewModel.deleteUser(result)
       adapter.notifyDataSetChanged()
    }
}


