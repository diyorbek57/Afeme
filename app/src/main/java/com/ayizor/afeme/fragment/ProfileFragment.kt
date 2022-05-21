package com.ayizor.afeme.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.FragmentProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProfileFragment : Fragment() {


    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        /*val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomBar)

        if (navBar != null) {
            navBar.visibility=View.GONE
        }*/
    }

}