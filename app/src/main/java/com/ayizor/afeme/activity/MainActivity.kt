package com.ayizor.afeme.activity

import android.os.Bundle
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}