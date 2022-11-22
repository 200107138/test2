package com.example.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.data.Result

import com.example.test.databinding.CustomRowBinding


class ResultAdapter(val clickListener: ResultListener): ListAdapter<Result, ResultAdapter.MyViewHolder>(ResultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder.from(parent)

    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)


    }

    class MyViewHolder private constructor(
        val binding: CustomRowBinding
        ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result, clickListener: ResultListener) {
            binding.result = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
            binding.idTxt.text = (adapterPosition + 1).toString()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomRowBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

}

class ResultDiffCallback : DiffUtil.ItemCallback<Result>() {

    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }


}

class ResultListener(val clickListener: (result: Result) -> Unit) {
    fun onClick(result: Result) = clickListener(result)
}