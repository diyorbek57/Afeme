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
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.OnBoardingAdapter


class OnBoardingActivity : BaseActivity() {

    //Variables
    lateinit var viewPager: ViewPager
    lateinit var dotsLayout: LinearLayout
    lateinit var sliderAdapter: OnBoardingAdapter
    lateinit var next: Button
    lateinit var skip: Button
    lateinit var animation: Animation
    var currentPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_on_boarding)
        if (onlyOnce()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            inits()
        }

    }

    private fun inits() {
        //Hooks
        viewPager = findViewById(R.id.slider)
        dotsLayout = findViewById(R.id.dots)
        next = findViewById(R.id.next_btn)
        skip = findViewById(R.id.skip_btn)
        //Call adapter
        sliderAdapter = OnBoardingAdapter(this)
        viewPager.setAdapter(sliderAdapter)

        //Dots
        addDots(0)
        viewPager.addOnPageChangeListener(changeListener)
        next.setOnClickListener {
            next()
        }


        skip.setOnClickListener {
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
        viewPager.currentItem = currentPos + 1
        if (next.text.toString().contains("Done")) {
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
        val dots = arrayOfNulls<TextView>(4)
        dotsLayout.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]?.text = Html.fromHtml("&#8226;")
            dots[i]?.textSize = 35F
            dotsLayout.addView(dots[i])
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
            currentPos = position
            if (position == 0) {
                next.setText(R.string.next)
                skip.visibility = View.VISIBLE
            } else if (position == 1) {
                next.setText(R.string.next)
                skip.visibility = View.VISIBLE
            } else if (position == 2) {
                next.setText(R.string.next)
                skip.visibility = View.VISIBLE
            } else {
                next.setText(R.string.skip)
                skip.visibility = View.INVISIBLE
            }


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