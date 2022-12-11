package com.example.test

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.test.data.Type

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
        val menuHost: MenuHost = requireActivity()
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
                        findNavController().popBackStack()
                        this.findNavController().navigate(
                            viewModel.getNextNavDestination())
                    }
                    else {
                        viewModel.finalresult()

                        this.findNavController().navigate(
                            ThirdGameFragmentDirections.actionFragmentThirdGameToEndGameFragment(Type.Memory, viewModel.averagetime.toString())
                        )

                    }
                }
                else{
                    (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.game_round_count, viewModel.currentThirdGameCount.value, ROUNDS)
                }

            }
        }


        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here

            }



            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        activity?.onBackPressed()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        binding.lifecycleOwner = viewLifecycleOwner
    }

}