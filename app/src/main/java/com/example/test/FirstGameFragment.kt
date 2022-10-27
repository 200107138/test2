package com.example.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.test.databinding.FragmentFirstGameBinding


class FirstGameFragment : Fragment(R.layout.fragment_first_game) {
    private lateinit var binding: FragmentFirstGameBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstGameBinding.bind(view)

            }


}
