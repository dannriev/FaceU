package com.faceu.faceu.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faceu.faceu.data.response.PredictionsItem
import com.faceu.faceu.databinding.ItemFaceHistoryBinding

class PredictionsAdapter : RecyclerView.Adapter<PredictionsAdapter.ViewHolder>() {

    private val list = ArrayList<PredictionsItem>()

    fun setList(items: ArrayList<PredictionsItem>){
        list.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder (private val binding: ItemFaceHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PredictionsItem){
            binding.apply {
                Glide.with(itemView)
                    .load(item.imageUrl)
                    .into(ivFaceItem)
                tvLabel.text = item.predictedClass
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFaceHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

}