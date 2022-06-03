package com.ayizor.afeme.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.CategoryAdapter
import com.ayizor.afeme.adapter.CreatePostBuildingTypeAdapter
import com.ayizor.afeme.api.ApiInterface
import com.ayizor.afeme.api.Client
import com.ayizor.afeme.databinding.ActivityCreatePostBinding
import com.ayizor.afeme.databinding.ActivityDetailsBinding
import com.ayizor.afeme.fragment.HomeFragment
import com.ayizor.afeme.model.Category
import com.ayizor.afeme.model.response.CategoryResponse
import com.ayizor.afeme.utils.Logger
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreatePostActivity : BaseActivity() {
    lateinit var binding: ActivityCreatePostBinding
    val TAG: String = HomeFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
    }

    private fun inits() {


    }


//    private fun changeIcons() {
//        if (binding.cvSaleTypePost.isChecked) {
//            binding.ivSaleTypePost.setImageResource(R.drawable.ic_creat_post_house_selected);
//        } else {
//            binding.ivSaleTypePost.setImageResource(R.drawable.ic_creat_post_house_unselected);
//        }
//        if (binding.cvMortgageTypePost.isChecked) {
//            binding.ivMortgageTypePost.setImageResource(R.drawable.ic_creat_post_calendar_pen_selected);
//        } else {
//            binding.ivMortgageTypePost.setImageResource(R.drawable.ic_creat_post_calendar_pen_unselected);
//        }
//        if (binding.cvRentTypePost.isChecked) {
//            binding.ivRentTypePost.setImageResource(R.drawable.ic_creat_post_calendar_selected);
//        } else {
//            binding.ivRentTypePost.setImageResource(R.drawable.ic_creat_post_calendar_unselected);
//        }
//
//
//    }


    override fun onBackPressed() {
        super.onBackPressed()

    }


}