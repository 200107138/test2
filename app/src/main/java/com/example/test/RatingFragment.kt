package com.example.test

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.test.databinding.FragmentRatingBinding

import com.example.test.databinding.FragmentTrainingBinding

class RatingFragment : Fragment(R.layout.fragment_rating) {
    private val viewModel: RatingViewModel by viewModels()
    private lateinit var binding: FragmentRatingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRatingBinding.inflate(inflater, container, false)
        val ad = binding.statBackground.background as AnimationDrawable

        ad.setEnterFadeDuration(10)
        ad.setExitFadeDuration(5000)
        ad.start()
        binding.button.setOnClickListener {
            viewModel.fillDestinations()
            GameSettingsRepository.getInstance().isRatingModeEnabled = true
            this.findNavController().navigate(
                viewModel.getNextNavDestination())
        }
        viewModel.readAdllData.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.avgReactionTime()
                binding.reactionTimeStat.text = viewModel.sum.toString()
            }
        })


        return binding.root
    }


}

