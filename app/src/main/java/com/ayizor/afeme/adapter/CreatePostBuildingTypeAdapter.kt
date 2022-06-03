package com.ayizor.afeme.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Animatable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.R
import com.ayizor.afeme.api.Api
import com.ayizor.afeme.databinding.ItemCreatePostSelectingCardsBinding
import com.ayizor.afeme.model.Category
import com.bumptech.glide.Glide

class CreatePostBuildingTypeAdapter(
    var context: Context,
    var categoryList: ArrayList<Category>,
    private val onBuildingTypeItemClickListener: OnBuildingTypeItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var binding: ItemCreatePostSelectingCardsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        binding =
            ItemCreatePostSelectingCardsBinding.inflate(LayoutInflater.from(context), parent, false)
        return BuildingTypeViewHolder(binding)


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemViewType) {
            with(categoryList[position]) {
                binding.tvBuildingTypeName.text = category_name
                if (category_name != null) {
                    //setImageToCategory(category_name, binding.ivCategoryImage, context)
                    Glide.with(context)
                        .load(Api.CATEGORY_IMAGE_URL + category_icon)
                        .into(binding.ivBuildingTypeIcon)
                }
                binding.cvBuildingTypeBackground.setOnClickListener {
                    if (category_name != null) {
                        onBuildingTypeItemClickListener.onBuildingTypeItemClickListener(
                            category_name
                        )
                    }
                }

            }

        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }


    inner class BuildingTypeViewHolder(val binding: ItemCreatePostSelectingCardsBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnBuildingTypeItemClickListener {
        fun onBuildingTypeItemClickListener(name: String)
    }
}


