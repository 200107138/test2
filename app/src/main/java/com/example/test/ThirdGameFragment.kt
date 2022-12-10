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
import androidx.navigation.ui.NavigationUI

import com.example.test.databinding.FragmentThirdGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.callbackFlow


class ThirdGameFragment : Fragment() {
    private val viewModel: ThirdGameViewModel by viewModels()

    private lateinit var binding: FragmentThirdGameBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_third_game, container, false)
        binding.gameViewModel = viewModel
        viewModel.changerounds()
        binding.maxNoOfWords = ROUNDS

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.shufflelist()
        binding.thirdGameStart.setOnClickListener {

            viewModel.startGame()

        }


        viewModel.currentThirdGameCount.observe(viewLifecycleOwner) { it ->
            it?.let {
                // Here, I'm calling a new function named setLoaderVisibility
                if(it > ROUNDS){
                    if(GameSettingsRepository.getInstance().isRatingModeEnabled){
                        viewModel.finalresult()

                        this.findNavController().navigate(
                            viewModel.getNextNavDestination())
                    }
                    else {
                        viewModel.finalresult()

                        this.findNavController().navigate(
                            ThirdGameFragmentDirections.actionFragmentThirdGameToEndGameFragment()
                        )

                    }
                }
                else{
                    (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.game_round_count, viewModel.currentThirdGameCount.value, ROUNDS)
                }

            }
        }

        binding.lifecycleOwner = viewLifecycleOwner
    }

}