package com.example.test

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.example.test.databinding.FragmentFirstGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.NonCancellable.cancel


class FirstGameFragment : Fragment() {
    private val viewModel: FirstGameViewModel by viewModels()

    private lateinit var binding: FragmentFirstGameBinding
private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstGameBinding.inflate(inflater, container, false)
        timer = object : CountDownTimer(5000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                if(viewModel.currentTag == "white" && viewModel.clicked){
                    viewModel.setgreen()
                    viewModel.starttimer()
                    binding.firstgamestarttext.setBackgroundResource(R.drawable.first_game_start_green)
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Setup a click listener for the Submit and Skip buttons.
        binding.firstgamestarttext.setOnClickListener {

            onClickStart()

        }


    }

    private fun onClickStart() {
        if(viewModel.currentTag == "green"){
            viewModel.clicked()
            timer.cancel()
            viewModel.endtimer()
            if (viewModel.nextGame()) {

                Toast.makeText(context, viewModel.reactiontime.toString(), Toast.LENGTH_LONG).show()
                updateNextWordOnScreen()
            }
            else{
                showFinalScoreDialog()
            }
        }
        else if(viewModel.currentTag == "white" && !viewModel.clicked){
            viewModel.clicked()
            timer.start()

                binding.firstgamestarttext.setText("")

        }
        else if(viewModel.currentTag == "white" && viewModel.clicked){
            timer.cancel()
            viewModel.endtimer()
            viewModel.clicked()
            if (viewModel.nextGame()) {
            Toast.makeText(context, viewModel.reactiontime.toString(), Toast.LENGTH_LONG).show()
            updateNextWordOnScreen()
            }
            else{
                showFinalScoreDialog()
            }
        }


    }




    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("rtgrtgtr")
            .setMessage("rthrthtrht")

            .show()
    }

    private fun exitGame() {
        activity?.finish()
    }

    private fun restartGame() {
        viewModel.reinitializeData()
        updateNextWordOnScreen()
    }
    private fun updateNextWordOnScreen() {
        binding.firstgamestarttext.setText("Start")
        binding.firstgamestarttext.setBackgroundResource(R.drawable.first_game_start)
    }

}
