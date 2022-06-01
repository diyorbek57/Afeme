package com.ayizor.afeme.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.LastSavedPostsAdapter
import com.ayizor.afeme.adapter.ProfileViewPagerAdapter
import com.ayizor.afeme.adapter.SavedPostsAdapter
import com.ayizor.afeme.databinding.FragmentProfileBinding
import com.ayizor.afeme.databinding.FragmentSavedPostsBinding
import com.bumptech.glide.Glide


class SavedPostsFragment : Fragment() {

    lateinit var binding: FragmentSavedPostsBinding
    lateinit var savedPostsAdapter: SavedPostsAdapter
    lateinit var lastsavedPostsAdapter: LastSavedPostsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSavedPostsBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        refreshSavedPostsAdapter()

    }



    private fun refreshSavedPostsAdapter() {

    }

}

