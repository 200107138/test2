package com.example.test

import android.animation.ObjectAnimator
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.test.data.ResultDatabase
import com.example.test.data.Type
import com.example.test.databinding.FragmentRatingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RatingFragment : Fragment() {
    private val viewModel: RatingViewModel by viewModels()
    private lateinit var binding: FragmentRatingBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db: ResultDatabase
    var listFirstGame = mutableListOf<Int>()
    var listSecondGame = mutableListOf<Int>()
    var listThirdGame = mutableListOf<Int>()

    val referencer= FirebaseDatabase.getInstance().reference.child("users")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rating, container, false)
db = ResultDatabase.getDatabase(requireContext())
        val ad = binding.statBackground.background as AnimationDrawable
        auth = FirebaseAuth.getInstance()
        ad.setEnterFadeDuration(10)
        ad.setExitFadeDuration(5000)
        ad.start()
        binding.button.setOnClickListener {
            viewModel.fillDestinations()
            GameSettingsRepository.getInstance().isRatingModeEnabled = true
            this.findNavController().navigate(
                viewModel.getNextNavDestination())
        }



        if(auth.currentUser != null){

            referencer.child(auth.currentUser!!.uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    GlobalScope.launch {
                        if(snapshot.hasChild("firstGameRatingResult")) {
                            for (i: DataSnapshot in snapshot.child("firstGameRatingResult").children) {
                                listFirstGame.add(
                                    i.value.toString()
                                        .filter { it.isLetterOrDigit() }.toInt()
                                )
                            }
                            for (i: DataSnapshot in snapshot.child("secondGameRatingResult").children) {
                                listSecondGame.add(
                                    i.value.toString()
                                        .filter { it.isLetterOrDigit() }.toInt()
                                )
                            }
                            for (i: DataSnapshot in snapshot.child("thirdGameRatingResult").children) {
                                listThirdGame.add(
                                    i.value.toString()
                                        .filter { it.isLetterOrDigit() }.toInt()
                                )
                            }

                            displaydata(listFirstGame, listSecondGame, listThirdGame)
                        }
                    //prints "Do you have data? You'll love Firebase."
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }
        else{
            readdata()
        }




        return binding.root
    }

    private fun readdata(){
lateinit var dataFirstGame: List<Int>
        lateinit var dataSecondGame: List<Int>
        lateinit var dataThirdGame: List<Int>
GlobalScope.launch {
    dataFirstGame = db.resultDao().readAlllData(Type.ReactionTime)

    dataSecondGame = db.resultDao().readAlllData(Type.PeripheralVision)
    dataThirdGame = db.resultDao().readAlllData(Type.Memory)
    displaydata(dataFirstGame, dataSecondGame, dataThirdGame)
}
    }
    suspend fun displaydata(dataFirstGame: List<Int>, dataSecondGame: List<Int>, dataThirdGame: List<Int>){
        withContext(Dispatchers.Main){
            if(dataFirstGame.isNotEmpty()) {
                binding.reactionTimeStat.text =
                    (dataFirstGame.sum() / dataFirstGame.size).toString()
                binding.peripheralVisionStat.text =
                    (dataSecondGame.sum() / dataSecondGame.size).toString()
                binding.memoryStat.text = (dataThirdGame.sum() / dataThirdGame.size).toString()

                val dataSum =
                    (dataFirstGame.sum() / dataFirstGame.size) + (dataSecondGame.sum() / dataSecondGame.size) + (dataThirdGame.sum() / dataThirdGame.size)
                val overallRating = 99 * 16330 / dataSum

                binding.overallRating.text = overallRating.toString()
                binding.reactionTimeProgressBar.max = 100
                val firstGameProgress = 130 * 100 / (dataFirstGame.sum() / dataFirstGame.size)
                ObjectAnimator.ofInt(binding.reactionTimeProgressBar, "progress", firstGameProgress)
                    .setDuration(2000)
                    .start()

                binding.peripheralVisionProgressBar.max = 100
                val secondGameProgress = 200 * 100 / (dataSecondGame.sum() / dataSecondGame.size)
                ObjectAnimator.ofInt(
                    binding.peripheralVisionProgressBar,
                    "progress",
                    secondGameProgress
                )
                    .setDuration(2000)
                    .start()

                binding.memoryProgressBar.max = 100
                val thirdGameProgress = 16000 * 100 / (dataThirdGame.sum() / dataThirdGame.size)
                ObjectAnimator.ofInt(binding.memoryProgressBar, "progress", thirdGameProgress)
                    .setDuration(2000)
                    .start()
            }
        }
    }


}

