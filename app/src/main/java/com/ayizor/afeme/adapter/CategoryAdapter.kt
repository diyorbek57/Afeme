package com.ayizor.afeme.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Animatable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.R
import com.ayizor.afeme.api.main.Api
import com.ayizor.afeme.databinding.ItemHomeCategoryBinding
import com.ayizor.afeme.model.Category
import com.bumptech.glide.Glide

class CategoryAdapter(
    var context: Context,
    var categoryList: ArrayList<Category>,
    private val onCategoryItemClickListener: OnCategoryItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var binding: ItemHomeCategoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        binding = ItemHomeCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return CategoryViewHolder(binding)


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemViewType) {
            with(categoryList[position]) {
                binding.tvCategoryName.text = category_name
                if (category_name != null) {
                    //setImageToCategory(category_name, binding.ivCategoryImage, context)
                    Glide.with(context)
                        .load(Api.CATEGORY_IMAGE_URL + category_icon)
                        .into(binding.ivCategoryImage)
                }
                binding.cvCategory.setOnClickListener {
                    if (category_name != null) {
                        onCategoryItemClickListener.onCategoryItemClickListener(category_name)
                    }
                }

            }

        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    private fun heartAnimation(animationHeart: ImageView) {
        animationHeart.alpha = 0.70f
        (animationHeart.drawable as? Animatable)?.start()

    }

    inner class CategoryViewHolder(val binding: ItemHomeCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnCategoryItemClickListener {
        fun onCategoryItemClickListener(name: String)
    }
}

private fun setImageToCategory(category_name: String, imageView: ImageView, context: Context) {
    if (category_name.contentEquals("House")) {
        Glide.with(context)
            .load(R.drawable.ic_house)
            .into(imageView)
    } else if (category_name.contentEquals("Villa")) {
        Glide.with(context)
            .load(R.drawable.ic_villa)
            .into(imageView)
    } else if (category_name.contentEquals("Apartment")) {
        Glide.with(context)
            .load(R.drawable.ic_flat_house)
            .into(imageView)
    }
}
