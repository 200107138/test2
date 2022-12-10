package com.example.test

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.test.databinding.FragmentFirstGameBinding


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

        val goview = layoutInflater.inflate(R.layout.go, null)
        val penaltyview = layoutInflater.inflate(R.layout.penalty, null)
        val builder = AlertDialog.Builder(requireContext())
        val penaltybuilder = AlertDialog.Builder(requireContext())
        builder.setView(goview)
        penaltybuilder.setView(penaltyview)
        val dialog = builder.create()
        val penaltydialog = penaltybuilder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        penaltydialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        viewModel.golayout.observe(viewLifecycleOwner) { it ->
            it?.let {
                // Here, I'm calling a new function named setLoaderVisibility
                if(it == true){

                    dialog.show()
                }
                else{

                    dialog.dismiss()
                }

            }
        }
        viewModel.penaltylayout.observe(viewLifecycleOwner) { it ->
            it?.let {
                // Here, I'm calling a new function named setLoaderVisibility
                if(it == true){
                    penaltydialog.show()
                }
                else {

                    penaltydialog.dismiss()
                }

            }
        }
        viewModel.currentFirstGameCount.observe(viewLifecycleOwner) { it ->
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
                            FirstGameFragmentDirections
                                .actionFragmentFirstGameToEndGameFragment()
                        )

                    }
                }
                else{
                    (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.game_round_count, viewModel.currentFirstGameCount.value, ROUNDS)
                }

            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        // Setup a click listener for the Submit and Skip buttons.
        viewModel.starttimer()





        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.game_round_count, 3, 2)

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
