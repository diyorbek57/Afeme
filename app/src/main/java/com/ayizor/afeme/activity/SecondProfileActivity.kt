package com.ayizor.afeme.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.databinding.ActivityPreviewCreatedPostBinding
import com.ayizor.afeme.manager.PostPrefsManager

class SecondProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityPreviewCreatedPostBinding
    var dataService: ApiInterface? = null
    val TAG: String = PreviewCreatedPostActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewCreatedPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inits()
    }

    private fun inits() {

    }
}