package com.ayizor.afeme.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.OnBoardingAdapter
import com.ayizor.afeme.databinding.ActivityOnBoardingBinding
import com.ayizor.afeme.databinding.ActivitySplashBinding


class OnBoardingActivity : BaseActivity() {

    //Variables
    lateinit var sliderAdapter: OnBoardingAdapter
    lateinit var animation: Animation
    var currentPos = 0
    lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        if (onlyOnce()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            inits()
        }

    }

    private fun inits() {
        //Call adapter
        sliderAdapter = OnBoardingAdapter(this)
        binding.vpOnboarding.adapter = sliderAdapter

        //Dots
        addDots(0)
        binding.vpOnboarding.addOnPageChangeListener(changeListener)
        binding.nextBtn.setOnClickListener {
            next()
        }


        binding.skipBtn.setOnClickListener {
            skip()
        }


    }

    fun skip() {
        val sharedpreferences = getSharedPreferences("ONLYONCE", MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.putBoolean("ONLYONCE", true)
        editor.apply()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun next() {
        binding.vpOnboarding.currentItem = currentPos + 1
        if (binding.nextBtn.text.toString().contains("Done")) {
            val sharedpreferences = getSharedPreferences("ONLYONCE", MODE_PRIVATE)
            val editor = sharedpreferences.edit()
            editor.putBoolean("ONLYONCE", true)
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun addDots(position: Int) {
        val dots = arrayOfNulls<TextView>(3)
        binding.llDots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]?.text = Html.fromHtml("&#8226;")
            dots[i]?.textSize = 35F
            binding.llDots.addView(dots[i])
        }
        if (dots.isNotEmpty()) {
            dots[position]?.setTextColor(resources.getColor(R.color.bright_blue))
        }
    }

    var changeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

        }

        override fun onPageSelected(position: Int) {

            addDots(position)
            if (position == 0) {
                binding.nextBtn.setText(R.string.next)
                binding.skipBtn.visibility = View.VISIBLE
            } else if (position == 1) {
                binding.nextBtn.setText(R.string.next)
                binding.skipBtn.visibility = View.VISIBLE
            } else if (position == 2) {
                binding.nextBtn.setText(R.string.next)
                binding.skipBtn.visibility = View.VISIBLE
            } else {
                binding.nextBtn.setText(R.string.next)
                binding.skipBtn.visibility = View.INVISIBLE
            }
            ///

//                animation = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.bottom_anim);
//                letsGetStarted.setAnimation(animation);
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    fun onlyOnce(): Boolean {
        val sharedpreferences =
            getSharedPreferences("ONLYONCE", Context.MODE_PRIVATE)
        return sharedpreferences.getBoolean("ONLYONCE", false)
    }
}