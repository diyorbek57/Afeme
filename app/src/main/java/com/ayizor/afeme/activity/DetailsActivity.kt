package com.ayizor.afeme.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.ActivityDetailsBinding
import com.ayizor.afeme.databinding.ActivityMainBinding

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    val TAG:String=DetailsActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }
}