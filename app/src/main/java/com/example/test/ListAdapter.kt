package com.example.test

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

import com.example.test.databinding.CustomRowBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ListAdapter(val listener: OnItemClickListener): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var resultList = emptyList<Result>()
    class MyViewHolder(val binding: CustomRowBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = resultList[position]
        holder.binding.idTxt.text = currentItem.id.toString()
        holder.binding.firstNameTxt.text = currentItem.time
        holder.binding.lastNameTxt.text = currentItem.date
        holder.binding.delete.setOnClickListener {
            listener.onItemClick(currentItem)
        }
    }

    interface OnItemClickListener{
        fun onItemClick(result: Result)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(result: List<Result>){
        this.resultList = result
        notifyDataSetChanged()
    }


}