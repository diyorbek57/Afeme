package com.ayizor.afeme.activity


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.ActivityMainBinding
import com.ayizor.afeme.utils.Logger
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : BaseActivity() {


    lateinit var binding: ActivityMainBinding
val TAG:String=MainActivity::class.java.simpleName
    val LAUNCH_SECOND_ACTIVITY: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Logger.d(TAG,"onCreate")
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomBar, navHostFragment.navController)
        inits()
    }

    private fun inits() {

        binding.bottomBar.itemIconTintList = null




    }


    private fun callCreatePostActivity() {
        val i = Intent(this, CreatePostActivity::class.java)
        startActivityForResult(i, LAUNCH_SECOND_ACTIVITY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
//            binding.bottomBar.menu.getItem(0).setIcon(R.drawable.nav_ic_home_selected);

        }
    }

    override fun onStart() {
        super.onStart()
        Logger.d(TAG,"onStart")
    }
}




