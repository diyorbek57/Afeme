package com.ayizor.afeme.fragment


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.activity.EditPublicProfileActivity
import com.ayizor.afeme.activity.authentication.WelcomeActivity
import com.ayizor.afeme.adapter.ProfileViewPagerAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.FragmentProfileBinding
import com.ayizor.afeme.databinding.ItemBottomSheetSettingsBinding
import com.ayizor.afeme.manager.PostPrefsManager
import com.ayizor.afeme.model.User
import com.ayizor.afeme.model.response.UserResponse
import com.ayizor.afeme.utils.Logger
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment() {


    lateinit var binding: FragmentProfileBinding
    lateinit var adapter: ProfileViewPagerAdapter
    val TAG: String = ProfileFragment::class.java.simpleName
    var dataService: ApiInterface? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        dataService = Client.getClient(requireContext())?.create(ApiInterface::class.java)
        binding.ivSettingProfile.setOnClickListener {
            if (PostPrefsManager(requireContext()).loadImages().isNullOrEmpty()) {
                val i = Intent(requireContext(), WelcomeActivity::class.java)
                startActivity(i)
            } else {
                showSettingsBottomsheet()
            }

        }
        setupFeaturesViewPager()
        getUserDatas()

    }

    private fun displayUserDatas(user: User) {

        Glide.with(this).load(user.user_photo).into(binding.ivProfile)
        if (!user.user_description.isNullOrEmpty())
            binding.tvDescription.text = user.user_description

        binding.tvFullName.text = user.user_name + " " + user.user_last_name
    }

    private fun setupFeaturesViewPager() {
        adapter = ProfileViewPagerAdapter(parentFragmentManager)
        adapter.addFragment(CreatedPostsFragment(), getString(R.string.created))
        adapter.addFragment(SavedPostsFragment(), getString(R.string.saved))
        binding.vpProfile.adapter = adapter
        binding.tlProfile.setupWithViewPager(binding.vpProfile)
        binding.tlProfile.getTabAt(1)?.select()
        binding.tlProfile.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                if (position == 1) {
                    binding.llSearchProfile.visibility = View.VISIBLE
                } else {
                    binding.llSearchProfile.visibility = View.GONE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val position = tab.position
                if (position == 1) {
                    binding.llSearchProfile.visibility = View.VISIBLE
                } else {
                    binding.llSearchProfile.visibility = View.GONE
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        //set margin between tabs
        for (i in 0 until binding.tlProfile.tabCount) {
            val tab = (binding.tlProfile.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(0, 0, 50, 0)
            tab.requestLayout()
        }

    }


    private fun getUserDatas() {
        dataService!!.getCurrentUser().enqueue(object : Callback<UserResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Logger.d("Profile", response.body()?.data.toString())
                response.body()?.data?.let { displayUserDatas(it) }
                //progressBar!!.visibility = View.GONE
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.message?.let { Logger.d("Profile", it) }
                //progressBar!!.visibility = View.GONE
            }
        })

    }

    fun showSettingsBottomsheet() {
        val sheetDialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        val bottomSheetBinding: ItemBottomSheetSettingsBinding =
            ItemBottomSheetSettingsBinding.inflate(layoutInflater)
        sheetDialog.setContentView(bottomSheetBinding.root)


        bottomSheetBinding.tvSettings.setOnClickListener {

        }
        bottomSheetBinding.tvAddCover.setOnClickListener {

        }
        bottomSheetBinding.tvEditPublicProfile.setOnClickListener {
            val i = Intent(requireContext(), EditPublicProfileActivity::class.java)
            startActivity(i)
        }
        bottomSheetBinding.tvCopyProfileLink.setOnClickListener {

        }

        sheetDialog.show();
        sheetDialog.window?.attributes?.windowAnimations = R.style.DialogAnimaton;
    }
}