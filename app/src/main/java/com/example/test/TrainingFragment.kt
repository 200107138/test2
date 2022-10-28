package com.example.test

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

import com.example.test.databinding.FragmentTrainingBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [home_layout.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrainingFragment : Fragment(R.layout.fragment_training) {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentTrainingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTrainingBinding.inflate(inflater, container, false)

        binding.firstGameLauncher.setOnClickListener { view : View ->
            view.findNavController().navigate(TrainingFragmentDirections.actionTrainingFragmentToFirstGameFragment())

        }
        return binding.root
    }


    }

