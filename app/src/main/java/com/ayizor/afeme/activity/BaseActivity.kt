package com.ayizor.afeme.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.ayizor.afeme.R
import com.ayizor.afeme.activity.authentication.SignInActivity

open class BaseActivity : AppCompatActivity() {
    lateinit var context: Context
    var progressDialog: AppCompatDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }

//    fun showLoading(activity: Activity?) {
//        if (activity == null) return
//
//        if (progressDialog != null && progressDialog!!.isShowing()) {
//
//        } else {
//            progressDialog = AppCompatDialog(activity, R.style.CustomDialog)
//            progressDialog!!.setCancelable(false)
//            progressDialog!!.setCanceledOnTouchOutside(false)
//            progressDialog!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            progressDialog!!.setContentView(R.layout.custom_progress_dialog)
//            val iv_progress = progressDialog!!.findViewById<ImageView>(R.id.iv_progress)
//            val animationDrawable = iv_progress!!.getDrawable() as AnimationDrawable
//            animationDrawable.start()
//            if (!activity.isFinishing) progressDialog!!.show()
//        }
//    }

    protected fun dismissLoading() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    fun callMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun callOnBoardingActivity(context: Context) {
        val intent = Intent(context, OnBoardingActivity::class.java)
        startActivity(intent)
        finish()
    }

}