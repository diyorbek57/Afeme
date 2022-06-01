package com.ayizor.afeme.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.FragmentCreatedPostsBinding
import com.ayizor.afeme.databinding.FragmentProfileBinding


class CreatedPostsFragment : Fragment() {

    lateinit var binding: FragmentCreatedPostsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreatedPostsBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {

    }


}