package com.example.test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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


}


