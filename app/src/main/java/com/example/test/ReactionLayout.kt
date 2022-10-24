package com.example.test

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import com.example.test.databinding.ReactionLayoutBinding


class ReactionLayout : Fragment(R.layout.reaction_layout) {
    private lateinit var binding: ReactionLayoutBinding
    var reactiontimesec = 0
    var sec = 0
    var finished = 1;


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ReactionLayoutBinding.bind(view)
        binding.starter.setOnClickListener{
            binding.starter.visibility = View.GONE;
            object : CountDownTimer(2000, 1000) {

                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {
                    // declare ImageView reference

                    binding.imager.setBackgroundColor(Color.GREEN)
                    finished = 0;

                }
            }.start()
        }

        binding.imager.setOnClickListener{
            // Do nothing if nothing is checked (id == -1)\
            if(finished == 0){
                Navigation.findNavController(view).navigate(R.id.action_reaction_layout_to_endgame)
                reactiontimesec += sec

            }
            if(!binding.imager.background.equals("GREEN")){
                reactiontimesec = reactiontimesec + 1

            }


        }
        }
    }
