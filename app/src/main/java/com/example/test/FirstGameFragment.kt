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
        viewModel.changerounds()
        binding.maxNoOfWords = ROUNDS

        viewModel.currentFirstGameCount.observe(viewLifecycleOwner) { it ->
            it?.let {
                // Here, I'm calling a new function named setLoaderVisibility
                if(viewModel.currentFirstGameCount.value!! > ROUNDS){
                    if(GameSettingsRepository.getInstance().isRatingModeEnabled){
                        this.findNavController().navigate(
                          viewModel.getNextNavDestination())
                    }
                    else {
                        viewModel.finalresult()
                        viewModel.reinitializeData()
                        this.findNavController().navigate(
                            FirstGameFragmentDirections
                                .actionFragmentFirstGameToEndGameFragment(R.id.fragment_first_game)
                        )
                    }
                }

            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Setup a click listener for the Submit and Skip buttons.
        viewModel.starttimer()
        binding.toolbar.close.setOnClickListener{
            this.findNavController().navigateUp()
        }
        binding.lifecycleOwner = viewLifecycleOwner
    }






}
