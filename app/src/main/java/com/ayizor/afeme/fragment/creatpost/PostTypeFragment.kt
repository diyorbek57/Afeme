package com.ayizor.afeme.fragment.creatpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.activity.CreatePostActivity
import com.ayizor.afeme.databinding.ActivityCreatePostBinding
import com.ayizor.afeme.databinding.FragmentPostTypeBinding
import com.ayizor.afeme.eventbus.MessageEvent
import com.ayizor.afeme.fragment.HomeFragment
import com.ayizor.afeme.utils.Logger
import org.greenrobot.eventbus.EventBus


class PostTypeFragment : Fragment(), View.OnClickListener{

    lateinit var binding: FragmentPostTypeBinding
    var fragmentNumber = 2
    val TAG: String = HomeFragment::class.java.simpleName
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostTypeBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        activity?.findViewById<ProgressBar>(R.id.progress_bar_main_creat_post)?.progress=1
       // mainBinding.progressBarMainCreatPost.progress=1
        setupOnClicks()

    }

    private fun setupOnClicks() {
        binding.cvSell.setOnClickListener {
            Logger.d("OnClick", "cvsell")
//            EventBus.getDefault().post(MessageEvent(2))
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
                .replace(R.id.fragment_container_creat_post, BuildningTypeFragment())
                .addToBackStack(PostTypeFragment::class.java.name).commit()
        }
        binding.cvMortgage.setOnClickListener(this)
        binding.cvRentByDay.setOnClickListener(this)
        binding.cvTakeLongTime.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var fragment: Fragment? = null
        when (v?.id) {
            R.id.cv_sell -> {
                fragment = BuildningTypeFragment()
            }
//            R.id.cv_mortgage ->{
//                fragment = BuildningTypeFragment()
//            }
            R.id.cv_take_long_time -> {
                fragment = BuildningTypeFragment()
            }
            //            R.id.cv_mortgage ->{
//                fragment = BuildningTypeFragment()
//


        }
        assert(fragment != null)
        if (fragment != null) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_creat_post, fragment).commit()
        }
    }




}