package com.example.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.test.databinding.FragmentFirstGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.System.exit


class FirstGameFragment : Fragment() {
    private val viewModel: FirstGameViewModel by viewModels()
    private lateinit var binding: FragmentFirstGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFirstGameBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup a click listener for the Submit and Skip buttons.
        binding.firstgamestarttext.setOnClickListener {onSubmitWord()
            // Update the UI
            if (binding.firstgamestarttext.getTag().equals("white")) {
                binding.firstgamestarttext.setText("")
                binding.firstgamestarttext.setBackgroundResource(R.drawable.first_game_start_green)
            }

        }
    }
    private fun onSubmitWord() {



            if (viewModel.nextWord()) {
                updateNextWordOnScreen()
            } else {
                showFinalScoreDialog()
            }

    }

    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored, viewModel.score))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .show()
    }

    private fun updateNextWordOnScreen() {
        binding.firstgamestarttext.setTag(viewModel.currentTag)
    }
}


