package com.ayizor.afeme.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.FragmentHomeBinding
import com.ayizor.afeme.databinding.FragmentPostTypeBinding


class PostTypeFragment : Fragment() {

    lateinit var binding: FragmentPostTypeBinding
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
binding.tvNamePostDetails

    }

}