package com.sametcetinkaya.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sametcetinkaya.foodapp.data.Categories
import com.sametcetinkaya.foodapp.databinding.CategoriesRowBinding
import com.sametcetinkaya.foodapp.databinding.FavoriteRowBinding

class CategoriesFragmentAdapter () :RecyclerView.Adapter<CategoriesFragmentAdapter.ViewHolder>() {

    lateinit var onCategoriesItemClick :((Categories) -> Unit)

    private val diffUtil = object : DiffUtil.ItemCallback<Categories>()
    {
        override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }
        override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this , diffUtil)

    inner class ViewHolder (val binding: CategoriesRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(CategoriesRowBinding.inflate(LayoutInflater.from(parent.context)))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(data.strCategoryThumb)
            .into(holder.binding.imgCategories)

        holder.binding.categoryName.text = data.strCategory

        holder.itemView.setOnClickListener {
            onCategoriesItemClick.invoke(data)
        }
    }
    override fun getItemCount() = differ.currentList.size
}