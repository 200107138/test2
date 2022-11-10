package com.example.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.test.databinding.FragmentFirstGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class FirstGameFragment : Fragment() {
    private val viewModel: FirstGameViewModel by viewModels()

    private lateinit var binding: FragmentFirstGameBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_game, container, false)
        binding.gameViewModel = viewModel

        binding.maxNoOfWords = ROUNDS

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Setup a click listener for the Submit and Skip buttons.
        binding.firstgamestarttext.setOnClickListener {

            onClickStart()

        }

        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun onClickStart() {

            if (viewModel.nextGame()) {
                if(viewModel.reactiontime == 1000) {
                    Toast.makeText(context, "Penalty! +1s", Toast.LENGTH_LONG)
                        .show()
                }
            }
            else{
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
        findNavController().navigate(R.id.action_fragment_first_game_to_fragment_training)
    }

    private fun restartGame() {
        viewModel.reinitializeData()

    }


}
