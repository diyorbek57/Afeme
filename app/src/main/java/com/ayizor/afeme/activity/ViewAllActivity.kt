package com.ayizor.afeme.activity

import android.os.Bundle
import com.ayizor.afeme.databinding.ActivityViewAllBinding
import com.ayizor.afeme.fragment.HomeFragment

class ViewAllActivity : BaseActivity(){

    lateinit var binding: ActivityViewAllBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
    }

    private fun inits() {
        binding.tvTitle.text =intent.getStringExtra("category_name")
    }


}