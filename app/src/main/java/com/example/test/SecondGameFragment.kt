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
        viewModel.changerounds()
        binding.maxNoOfWords = ROUNDS



        viewModel.currentSecondGameCount.observe(viewLifecycleOwner) { it ->
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
                            SecondGameFragmentDirections.actionFragmentSecondGameToEndGameFragment()
                        )

                    }
                }
                else{
                    (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.game_round_count, viewModel.currentSecondGameCount.value, ROUNDS)
                }

            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNextGame()
        binding.secondGameNumber1.setOnClickListener {

            viewModel.button1clicked()

        }
        binding.secondGameNumber2.setOnClickListener {

            viewModel.button1clicked()

        }
        binding.lifecycleOwner = viewLifecycleOwner
    }


}