package com.example.test

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.test.databinding.ReactionLayoutBinding
import kotlin.math.roundToInt


class ReactionLayout : Fragment(R.layout.reaction_layout) {
    private lateinit var binding: ReactionLayoutBinding
    var reactiontimesec = 0.000
    var finished = 1
    var attempt = 0
    var start = 0.000
    var end = 0.000

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ReactionLayoutBinding.bind(view)
        binding.starter.setOnClickListener{
            val rnds = (2500..7000).random().toLong()
            finished = 1
            binding.starter.visibility = View.GONE;
            object : CountDownTimer(rnds, 1000) {

                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {
                    // declare ImageView reference
                    if(finished != 2) {
                        binding.imager.setBackgroundColor(Color.GREEN)
                        finished = 0;
                        start = System.currentTimeMillis().toDouble()
                    }


                }
            }.start()
        }

        binding.imager.setOnClickListener{

            end = System.currentTimeMillis().toDouble()
            if(finished == 0){
                finished = 2
                attempt++
                reactiontimesec += end - start
                val averagereactiontime = reactiontimesec.roundToInt()
                binding.textviewid.setText(averagereactiontime.toString())
                if(attempt < 3){
                    binding.starter.visibility = View.VISIBLE
                    binding.imager.setBackgroundColor(Color.BLACK)
                }
                else{
                    view.findNavController()
                        .navigate(ReactionLayoutDirections
                            .actionReactionLayoutToEndgame(averagereactiontime.toString()))
                }


            }
            else if(finished != 0){
                finished = 2
                attempt++
                reactiontimesec += 1000.0
                val averagereactiontime = reactiontimesec.roundToInt()
                binding.textviewid.setText(averagereactiontime.toString())
                if(attempt < 3){
                    binding.starter.visibility = View.VISIBLE
                    binding.imager.setBackgroundColor(Color.BLACK)
                }
                else{
                    view.findNavController()
                        .navigate(ReactionLayoutDirections
                            .actionReactionLayoutToEndgame(averagereactiontime.toString()))
                }
            }


        }


    }
}
