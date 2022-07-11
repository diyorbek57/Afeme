package com.ayizor.afeme.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Animatable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.databinding.ItemPostSmallBinding
import com.ayizor.afeme.databinding.ItemViewAllBinding
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.utils.Logger
import com.ayizor.afeme.utils.Utils
import com.bumptech.glide.Glide
import java.io.IOException

class SmallPostsAdapter(
    var context: Context,
    var postsList: ArrayList<GetPost>,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TAG: String = SmallPostsAdapter::class.java.simpleName
    private lateinit var binding: ItemPostSmallBinding
    private lateinit var viewAllBinding: ItemViewAllBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        binding = ItemPostSmallBinding.inflate(LayoutInflater.from(context), parent, false)
        return SmallPostsViewHolder(binding)


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemView) {
            with(postsList[position]) {

                binding.tvNamePostSmall.text = post_built_year


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
                    binding.tvLocationPostSmall.text = locationName.state + locationName.city
                }

                binding.tvPricePostSmall.text = post_price_usd?.let { Utils.formatUsd(it) }
                binding.tvTypePostSmall.text = post_building_type?.category_name_uz
                binding.tvPeriodPostSmall.visibility = View.GONE
                if (post_rating != null) {
                    binding.tvRatingPostSmall.text = post_rating.toString()
                } else {
                    binding.cvRatingPostSmall.visibility = View.GONE
                }
                try {

                    Glide.with(context)
                        .load(
                            post_images?.get(0)?.image_url.toString()
                        )
                        .into(binding.ivImagePostSmall)
                } catch (e: IndexOutOfBoundsException) {
                    Logger.e(TAG, "Image: " + e.message.toString())
                } catch (e: IOException) {
                    Logger.e(TAG, "Image: " + e.message.toString())
                }



                binding.rlImagePostSmall.setOnClickListener {
                    if (post_id != null) {
                        if (post_latitude != null) {
                            if (post_longitude != null) {
                                onItemClickListener.onItemClickListener(
                                    post_id,
                                    post_latitude,
                                    post_longitude
                                )
                            }
                        }
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

    inner class SmallPostsViewHolder(binding: ItemPostSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var smallPostBinding: ItemPostSmallBinding? = binding
        private var smallPostViewAllBinding: ItemViewAllBinding? = null

    }

    interface OnItemClickListener {
        fun onItemClickListener(id: Int, latitude: String, post_longitude: String)
    }


}