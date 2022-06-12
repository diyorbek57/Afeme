package com.ayizor.afeme.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Animatable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.databinding.ItemPostLargeBinding
import com.ayizor.afeme.model.Post
import com.bumptech.glide.Glide

class ViewAllAdapter(
    var context: Context,
    var postsList: ArrayList<Post>,
    private val onViewAllItemClickListener: OnViewAllItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var binding: ItemPostLargeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        binding = ItemPostLargeBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewAllViewHolder(binding)


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder) {
            with(postsList[position]) {
              //  binding.tvNamePostLarge.text = post_name
               // binding.tvLocationPostLarge.text = post_location
                binding.tvPricePostLarge.text = "$$post_price_usd"
                binding.tvTypePostLarge.text = post_building_type.toString()
               // binding.tvPeriodPostLarge.text = "/$post_period"
                binding.tvRatingPostLarge.text = "3.7"
                Glide.with(holder.itemView.context)
                    .load(post_images)
                    .into(binding.ivImagePostLarge)

                binding.rlImagePostLarge.setOnClickListener {
                    if (post_id != null) {
                        onViewAllItemClickListener.onViewAllItemClickListener(post_id)
                    }
                }
//                binding.ivLikePostSmall.setOnClickListener {
//                    heartAnimation(binding.ivHeartAnim)
//                }
            }

        }
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    private fun heartAnimation(animationHeart: ImageView) {
        animationHeart.alpha = 0.70f
        (animationHeart.drawable as? Animatable)?.start()

    }

    inner class ViewAllViewHolder(val binding: ItemPostLargeBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnViewAllItemClickListener {
        fun onViewAllItemClickListener(id: String)
    }
}


