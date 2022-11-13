package com.example.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.example.test.databinding.FragmentThirdGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder



class ThirdGameFragment : Fragment() {
    private val viewModel: ThirdGameViewModel by viewModels()

    private lateinit var binding: FragmentThirdGameBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_third_game, container, false)
        binding.gameViewModel = viewModel
        viewModel.currentThirdGameCount.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                // Here, I'm calling a new function named setLoaderVisibility
                    if(viewModel.currentThirdGameCount.value!! <= ROUNDS){}
                    else {
                        showFinalScoreDialog()
                    }

            }
        }
        binding.maxNoOfWords = ROUNDS

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.shufflelist()
        binding.thirdGameStart.setOnClickListener {

            if(viewModel.startGame()){}
            else {
            showFinalScoreDialog()
        }
        }
        binding.toolbar.close.setOnClickListener{
            findNavController().navigate(R.id.action_fragment_third_game_to_fragment_training)
        }

        binding.lifecycleOwner = viewLifecycleOwner
    }






    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.first_game_result))
            .setMessage(getString(R.string.average_time, viewModel.averagetime))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .show()
    }

    private fun exitGame() {
        findNavController().navigate(R.id.action_fragment_third_game_to_fragment_training)
    }

    private fun restartGame() {
        viewModel.reinitializeData()

    }
}