package com.ayizor.afeme.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {
    lateinit var binding: ActivityFilterBinding
    var dataService: ApiInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}