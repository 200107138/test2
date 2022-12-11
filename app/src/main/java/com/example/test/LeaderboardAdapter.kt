package com.example.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.data.Result
import com.example.test.databinding.CustomLeaderboardRowBinding

import com.example.test.databinding.CustomRowBinding
import com.google.firebase.auth.FirebaseAuth


class LeaderboardAdapter(private val userList : ArrayList<Users>) : RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder>() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_leaderboard_row,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        auth = FirebaseAuth.getInstance()
        val currentitem = userList[position]
        holder.id.text = (position + 1).toString()
        holder.nickname.text = currentitem.nickname
        holder.score.text = currentitem.score.toString()
        if(currentitem.uid == auth.uid){
            holder.nickname.text = "You"
        }
    }

    override fun getItemCount(): Int {

        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val nickname : TextView = itemView.findViewById(R.id.nickname)
        val score : TextView = itemView.findViewById(R.id.score)
        val id : TextView = itemView.findViewById(R.id.id_txt)
    }

}