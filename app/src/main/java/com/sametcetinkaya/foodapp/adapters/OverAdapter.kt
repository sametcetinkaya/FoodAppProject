package com.sametcetinkaya.foodapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sametcetinkaya.foodapp.data.Over
import com.sametcetinkaya.foodapp.databinding.OverRowBinding

class OverAdapter() : RecyclerView.Adapter<OverAdapter.ViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Over>() {
        override fun areItemsTheSame(oldItem: Over, newItem: Over): Boolean {
            return  oldItem.idMeal == newItem.idMeal
        }
        override fun areContentsTheSame(oldItem: Over, newItem: Over): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,diffUtil)

    class ViewHolder(val binding : OverRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OverRowBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        Glide.with(holder.itemView)
             .load(data.strMealThumb)
             .into(holder.binding.overImageView)
    }

    override fun getItemCount() = differ.currentList.size
}