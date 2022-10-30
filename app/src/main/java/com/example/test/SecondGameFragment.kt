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
import com.example.test.databinding.FragmentSecondGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder



class SecondGameFragment : Fragment() {
    private val viewModel: SecondGameViewModel by viewModels()

    private lateinit var binding: FragmentSecondGameBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second_game, container, false)
        binding.gameViewModel = viewModel

        binding.maxNoOfWords = MAX_NO_OF_WORDS

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startSecondGame.setOnClickListener {

            onClickStart()

        }
        binding.secondGameNumber1.setOnClickListener {

            onClick1Button()

        }
        binding.secondGameNumber2.setOnClickListener {

            onClick2Button()

        }
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun onClickStart() {

        if (viewModel.startGame()) {

        } else {
            showFinalScoreDialog()
        }

    }

    private fun onClick1Button() {

        if (viewModel.nextGame1()) {

                Toast.makeText(context, viewModel.reactiontime.toString(), Toast.LENGTH_LONG)
                    .show()

        } else {
            showFinalScoreDialog()
        }

    }

    private fun onClick2Button() {

        if (viewModel.nextGame2()) {

                Toast.makeText(context, viewModel.reactiontime.toString(), Toast.LENGTH_LONG)
                    .show()

        } else {
            showFinalScoreDialog()
        }

    }

    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.first_game_result))
            .setMessage(getString(R.string.average_time, viewModel.averagereactiontime.value))
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
        findNavController().navigate(R.id.action_fragment_second_game_to_fragment_training)
    }

    private fun restartGame() {
        viewModel.reinitializeData()

    }
}