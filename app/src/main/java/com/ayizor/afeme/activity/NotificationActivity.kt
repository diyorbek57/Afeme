package com.ayizor.afeme.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ayizor.afeme.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    lateinit var binding: ActivityNotificationBinding
    val TAG: String = NotificationActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inits()
    }

    private fun inits() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}