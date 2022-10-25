package com.example.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.test.databinding.FragmentEndgameBinding


class Endgame : Fragment(R.layout.fragment_endgame) {
    private lateinit var binding: FragmentEndgameBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEndgameBinding.bind(view)
        val args = EndgameArgs.fromBundle(requireArguments())
        binding.textViewId.setText("Average time: ${args.averagereactiontime}")
        val time = {args.averagereactiontime}
        Toast.makeText(context, "NumCorrect: ${args.averagereactiontime}", Toast.LENGTH_LONG).show()
    }




}