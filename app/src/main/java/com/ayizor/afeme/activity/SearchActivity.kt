package com.ayizor.afeme.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    var dataService: ApiInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}