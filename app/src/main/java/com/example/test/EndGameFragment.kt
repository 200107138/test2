package com.example.test

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.test.data.Type
import com.example.test.databinding.FragmentEndGameBinding

import com.example.test.databinding.FragmentTrainingBinding

class EndGameFragment : Fragment(R.layout.fragment_end_game) {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentEndGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEndGameBinding.inflate(inflater, container, false)


        binding.tester.setOnClickListener { view : View ->
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Setup a click listener for the Submit and Skip buttons.

        binding.lifecycleOwner = viewLifecycleOwner
    }
}

