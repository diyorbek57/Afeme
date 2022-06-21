package com.ayizor.afeme.activity.authentication

import android.content.Intent
import android.os.Bundle
import com.ayizor.afeme.activity.BaseActivity
import com.ayizor.afeme.databinding.ActivityWelcomeBinding

class WelcomeActivity : BaseActivity() {

    lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
    }

    private fun inits() {
        binding.llSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        binding.llSignUp.setOnClickListener {
            val intent = Intent(this, OtherInformationActivity::class.java)
            startActivity(intent)
        }
    }
}