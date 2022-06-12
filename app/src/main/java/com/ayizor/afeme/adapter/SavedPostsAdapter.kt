package com.ayizor.afeme.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Animatable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.databinding.ItemPostSmallBinding
import com.ayizor.afeme.databinding.ItemViewAllBinding
import com.ayizor.afeme.model.Post
import com.bumptech.glide.Glide

class SavedPostsAdapter(
    var context: Context,
    var postsList: ArrayList<Post>,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var binding: ItemPostSmallBinding
    private lateinit var viewAllBinding: ItemViewAllBinding
    private val TYPE_ITEM_VIEWALL = 0
    private val TYPE_ITEM_POST = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        binding = ItemPostSmallBinding.inflate(LayoutInflater.from(context), parent, false)
        return SmallPostsViewHolder(binding)


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemViewType) {
            with(postsList[position]) {
                //  binding.tvNamePostSmall.text = post_name
                //   binding.tvLocationPostSmall.text = post_location
                binding.tvPricePostSmall.text = "$$post_price_usd"
                binding.tvTypePostSmall.text = post_building_type.toString()
                //  binding.tvPeriodPostSmall.text = "/$post_period"
                binding.tvRatingPostSmall.text = "3.6"
                Glide.with(holder.itemView.context)
                    .load(post_images)
                    .into(binding.ivImagePostSmall)

                binding.rlImagePostSmall.setOnClickListener {
                    if (post_id != null) {
                        onItemClickListener.onItemClickListener(post_id)
                    }
                }
                binding.ivLikePostSmall.setOnClickListener {
                    heartAnimation(binding.ivHeartAnim)
                }
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

    inner class SmallPostsViewHolder : RecyclerView.ViewHolder {
        private var smallPostBinding: ItemPostSmallBinding? = null
        private var smallPostViewAllBinding: ItemViewAllBinding? = null

        constructor(binding: ItemPostSmallBinding) : super(binding.root) {
            smallPostBinding = binding
        }

        constructor(binding: ItemViewAllBinding) : super(binding.root) {
            smallPostViewAllBinding = binding
        }
    }

    interface OnItemClickListener {
        fun onItemClickListener(id: String)
    }
}