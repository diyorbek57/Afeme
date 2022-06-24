package com.ayizor.afeme.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Animatable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.databinding.ItemPostLargeBinding
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.model.post.Post
import com.ayizor.afeme.utils.Utils
import com.bumptech.glide.Glide

class ViewAllAdapter(
    var context: Context,
    var postsList: ArrayList<GetPost>,
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
                binding.tvNamePostLarge.text = post_built_year
                val locationName = post_latitude?.let {
                    post_longitude?.let { it1 ->
                        Utils.getCoordinateName(
                            context,
                            it.toDouble(),
                            it1.toDouble()
                        )
                    }
                }

                if (locationName != null) {
                    binding.tvLocationPostLarge.text = locationName.state + locationName.city
                }
                binding.tvPricePostLarge.text = "$$post_price_usd"
                binding.tvTypePostLarge.text = post_building_type.toString()
                binding.tvPeriodPostLarge.visibility = View.GONE
                if (post_rating != null) {
                    binding.tvRatingPostLarge.text = post_rating.toString()
                } else {
                    binding.cvRatingPostLarge.visibility = View.GONE
                }
                }
                binding.ivLikePostLarge.setOnClickListener {
//                    heartAnimation(binding.ivHeartAnim)
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


