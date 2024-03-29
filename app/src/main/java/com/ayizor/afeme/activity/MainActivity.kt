package com.ayizor.afeme.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.activity.authentication.WelcomeActivity
import com.ayizor.afeme.databinding.ActivityMainBinding
import com.ayizor.afeme.databinding.ItemBottomSheetCreatePostBinding
import com.ayizor.afeme.fragment.ChatFragment
import com.ayizor.afeme.fragment.HomeFragment
import com.ayizor.afeme.fragment.ProfileFragment
import com.ayizor.afeme.fragment.SearchFragment
import com.ayizor.afeme.manager.PostPrefsManager
import com.ayizor.afeme.manager.PrefsManager
import com.ayizor.afeme.utils.Logger
import com.ayizor.afeme.utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : BaseActivity() {


    lateinit var binding: ActivityMainBinding
    val TAG: String = MainActivity::class.java.simpleName
    val LAUNCH_SECOND_ACTIVITY: Int = 1
    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val chatFragment = ChatFragment()
    private val profileFragment = ProfileFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = homeFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Logger.d(TAG, "onCreate")
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        NavigationUI.setupWithNavController(binding.bottomBar, navHostFragment.navController)
        inits()

    }

    private fun inits() {
        signInAnonymously()
        setupNavigationBar()
        binding.bottomBar.itemIconTintList = null

        binding.ivAdd.setOnClickListener {
            if (!PrefsManager(this).loadUserRegisteredToken().isNullOrEmpty()){
                callCreatePostActivity()
            }
            else{
                callRegisterActivity()
            }
        }

        loadFCMToken()
    }

    private fun callRegisterActivity() {
        val i = Intent(this, WelcomeActivity::class.java)
        startActivity(i)
    }

    private fun setupNavigationBar() {
        fragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, profileFragment).hide(profileFragment)
            add(R.id.fragment_container, searchFragment).hide(searchFragment)
            add(R.id.fragment_container, chatFragment).hide(chatFragment)
            add(R.id.fragment_container, homeFragment)
        }.commit()

        binding.bottomBar.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment)
                        .commit()
                    activeFragment = homeFragment
                    true
                }
                R.id.nav_search -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(searchFragment)
                        .commit()
                    activeFragment = searchFragment
                    true
                }
                R.id.nav_chat -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(chatFragment)
                        .commit()
                    activeFragment = chatFragment
                    true
                }
                R.id.nav_profile -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(profileFragment)
                        .commit()
                    activeFragment = profileFragment
                    true
                }
                else -> false
            }
        }
    }


    private fun callCreatePostActivity() {

        if (PostPrefsManager(this).loadImages().isNullOrEmpty()) {
            val i = Intent(this, CreatePostActivity::class.java)
            startActivity(i)
        } else {
            callCratePostBottomSheet()
        }
    }

    private fun callCratePostBottomSheet() {
        val sheetDialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        val bottomSheetBinding: ItemBottomSheetCreatePostBinding =
            ItemBottomSheetCreatePostBinding.inflate(layoutInflater)
        sheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetBinding.btnContinue.setOnClickListener {
            val i = Intent(this, PreviewCreatedPostActivity::class.java)
            startActivity(i)
        }
        bottomSheetBinding.btnRestart.setOnClickListener {
            PostPrefsManager(this).clearSavedPostDatas()
            val i = Intent(this, CreatePostActivity::class.java)
            startActivity(i)
            sheetDialog.cancel()
        }

        sheetDialog.show();
        sheetDialog.window?.attributes?.windowAnimations = R.style.DialogAnimaton;
    }


    override fun onStart() {
        super.onStart()
        Logger.d(TAG, "onStart")
    }

    private fun loadFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Logger.d(TAG + "Token", "Fetching FCM registration token failed")
                return@OnCompleteListener
            }
            val token = task.result
            Logger.d(TAG + "Token", token.toString())
        })
    }

    private fun signInAnonymously() {
        val mAuth = FirebaseAuth.getInstance()
        mAuth.signInAnonymously().addOnSuccessListener(this) {
            // do your stuff
        }
            .addOnFailureListener(
                this
            ) { exception ->
                Log.e(
                    TAG,
                    "signInAnonymously:FAILURE",
                    exception
                )
            }
    }
}




