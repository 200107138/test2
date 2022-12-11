package com.example.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.databinding.FragmentLeaderboardBinding
import com.google.firebase.database.*

class LeaderboardFragment : Fragment() {
    private lateinit var dbref : DatabaseReference
    private lateinit var userArrayList : ArrayList<Users>
    private lateinit var binding: FragmentLeaderboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, com.example.test.R.layout.fragment_leaderboard, container, false)

        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<Users>()

        getUserData()

        return binding.root
    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("users")

        dbref.orderByChild("score").addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){

                        val user = userSnapshot.getValue(Users::class.java)
                        userArrayList.add(user!!)

                    }

                    binding.recyclerview.adapter = LeaderboardAdapter(userArrayList)




                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })




    }


}


