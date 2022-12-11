package com.example.test

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.test.data.Type
import com.example.test.databinding.FragmentEndGameBinding

import com.example.test.databinding.FragmentTrainingBinding

class EndGameFragment : Fragment(R.layout.fragment_end_game) {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentEndGameBinding
    val args: EndGameFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEndGameBinding.inflate(inflater, container, false)


        binding.tryAgain.setOnClickListener {
            findNavController().navigate(getNextNavDestination())
        }
        binding.home.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
    fun getNextNavDestination(): Int {
        if (args.type == Type.ReactionTime){
            return R.id.fragment_first_game
        }
        if (args.type == Type.PeripheralVision){
            return R.id.fragment_second_game
        }
        if (args.type == Type.Memory){
            return R.id.fragment_third_game
        }
        return R.id.fragment_training
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Result"

        binding.result.text = getString(R.string.game_result, args.time)
        binding.lifecycleOwner = viewLifecycleOwner
    }
}

