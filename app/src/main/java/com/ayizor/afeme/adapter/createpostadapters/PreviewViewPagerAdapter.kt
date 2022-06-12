package com.ayizor.afeme.adapter.createpostadapters

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.api.main.Api
import com.ayizor.afeme.databinding.ItemCreatePostSellTypeBinding
import com.ayizor.afeme.databinding.ItemViewPagerPreviewBinding
import com.ayizor.afeme.model.Image
import com.ayizor.afeme.model.SellType
import com.bumptech.glide.Glide

class PreviewViewPagerAdapter(
    var context: Context,
    var imagesList: ArrayList<Image>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var binding: ItemViewPagerPreviewBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        binding = ItemViewPagerPreviewBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewPagerViewHolder(binding)


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemViewType) {
            with(imagesList[position]) {
                Glide.with(context)
                    .load(image_url)
                    .into(binding.ivViewpager)
            }

        }
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }


    inner class ViewPagerViewHolder(val binding: ItemViewPagerPreviewBinding) :
        RecyclerView.ViewHolder(binding.root)


}