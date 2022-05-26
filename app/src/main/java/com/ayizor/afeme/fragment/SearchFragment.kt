package com.ayizor.afeme.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils

import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.FragmentSearchBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    val TAG: String = SearchFragment::class.java.simpleName
    var isDown = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        setupBottomSheet()

        binding.rl1Search.setOnClickListener {
            if (isDown) {
                slideDown(binding.cvSearch);
            } else {

                slideUp(binding.cvSearch);

            }
            isDown = !isDown;
        }
    }

    private fun setupBottomSheet() {
        BottomSheetBehavior.from(binding.bsSearch).apply {
            peekHeight = 1000
            isHideable = true
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    // slide the view from below itself to the current position
    fun slideDown(view: View) {
        val slide_down: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.slide_down
        )
        view.startAnimation(slide_down)
    }

    // slide the view from its current position to below itself
    fun slideUp(view: View) {
        view.visibility = View.GONE
        val slide_up: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.slide_up
        )
        view.startAnimation(slide_up)
    }

}
