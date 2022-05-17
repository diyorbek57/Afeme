package com.ayizor.afeme.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.ayizor.afeme.R
import com.bumptech.glide.Glide


class OnBoardingAdapter(var context: Context) : PagerAdapter() {

    private lateinit var layoutInflater: LayoutInflater


    private var images = intArrayOf(
        R.drawable.house_1,
        R.drawable.house_2, R.drawable.house_3
    )
    var headings = intArrayOf(
        R.string.onboarding_first_page,
        R.string.onboarding_second_page,
        R.string.onboarding_third_page
    )
    var descriptions = intArrayOf(
        R.string.text,
        R.string.text,
        R.string.text,
        R.string.text
    )

    override fun getCount(): Int {
        return headings.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.item_onboarding, container, false)
        val imageView: ImageView = view.findViewById(R.id.slider_image)
        val headerText = view.findViewById<TextView>(R.id.slider_heading)
        val descText = view.findViewById<TextView>(R.id.slider_desc)
        Glide.with(context).load(images[position]).into(imageView)
        headerText.setText(headings[position])
        descText.setText(descriptions[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}