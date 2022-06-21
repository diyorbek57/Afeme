package com.ayizor.afeme.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.google.android.material.snackbar.Snackbar


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

    fun showTopSnackBar(view: View, text: String){
//        val snack: Snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
//        val view: View = snack.view
//        val params = view.layoutParams
//        params. = Gravity.TOP
//        view.setLayoutParams(params)
//        snack.show()
    }

}