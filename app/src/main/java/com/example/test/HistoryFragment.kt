package com.example.test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.data.Result
import com.example.test.data.Type
import com.example.test.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment(), ListAdapter.OnItemClickListener {

    private lateinit var mUserViewModel: HistoryViewModel
    private lateinit var binding: FragmentHistoryBinding
    private val adapter = ListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        // Recyclerview

        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        mUserViewModel = ViewModelProvider(
            this, HistoryViewModelFactory(
                requireActivity().application,
                HistoryFragmentArgs.fromBundle(this.arguments as Bundle).type,
            )
        )[HistoryViewModel::class.java]
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


