package com.example.test

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.data.Result

import com.example.test.databinding.CustomRowBinding


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
        holder.binding.timeTxt.text = currentItem.time
        holder.binding.dateTxt.text = currentItem.date
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