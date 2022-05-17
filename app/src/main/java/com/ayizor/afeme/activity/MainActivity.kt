package com.ayizor.afeme.activity

import android.os.Bundle
import android.view.View
import com.ayizor.afeme.databinding.ActivityMainBinding
import com.ayizor.afeme.utils.Logger

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        inits()
    }

    private fun inits() {

    }
}