package com.example.test

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

import com.example.test.databinding.FragmentTrainingBinding

class TrainingFragment : Fragment(R.layout.fragment_training) {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentTrainingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTrainingBinding.inflate(inflater, container, false)


        binding.firstGameLauncher.setOnClickListener { view : View ->
            view.findNavController().navigate(TrainingFragmentDirections.actionTrainingFragmentToFirstGameFragment())

        }
        binding.secondGameLauncher.setOnClickListener { view : View ->
            view.findNavController().navigate(TrainingFragmentDirections.actionFragmentTrainingToSecondGameFragment())

        }
        binding.secondGameHistory.setOnClickListener { view : View ->
            view.findNavController().navigate(TrainingFragmentDirections.actionFragmentTrainingToFragmentSecondGameHistory())

        }
        binding.thirdGameLauncher.setOnClickListener { view : View ->
            view.findNavController().navigate(TrainingFragmentDirections.actionFragmentTrainingToFragmentThirdGame())

        }

        return binding.root
    }


    }

