package com.ayizor.afeme.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ayizor.afeme.R


class CreatePostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val returnIntent = Intent()
        returnIntent.putExtra("exit", "exit")
        setResult(RESULT_OK, returnIntent)
        finish()
    }
}