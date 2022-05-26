package com.ayizor.afeme.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayizor.afeme.R
import com.ayizor.afeme.activity.DetailsActivity
import com.ayizor.afeme.activity.NotificationActivity
import com.ayizor.afeme.activity.ViewAllActivity
import com.ayizor.afeme.adapter.CategoryAdapter
import com.ayizor.afeme.adapter.SmallPostsAdapter
import com.ayizor.afeme.databinding.FragmentHomeBinding
import com.ayizor.afeme.model.Category
import com.ayizor.afeme.model.Post
import com.ayizor.afeme.utils.Logger
import com.ayizor.afeme.viewmodel.HomeFragmentViewModel


class HomeFragment : Fragment(), SmallPostsAdapter.OnItemClickListener,
    CategoryAdapter.OnCategoryItemClickListener {

    lateinit var binding: FragmentHomeBinding
    val TAG: String = HomeFragment::class.java.simpleName
    private val viewModel by navGraphViewModels<HomeFragmentViewModel>(R.id.main_navigation)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        binding.ivNotificationsHome.setOnClickListener {
            callNotificationsActivity()
        }
        binding.rvHomeCategory.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvHomePopular.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvHomeNearby.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.rvHomeCheap.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        refreshPopularAdapter(getAllPosts())
        refreshNearbyAdapter(getAllPosts())
        refreshCheapAdapter(getAllPosts())
        refreshCategoryAdapter(getAllCategory())
        setupClickableViews()
    }

    private fun callNotificationsActivity() {
        val intent = Intent(requireContext(), NotificationActivity::class.java)
        startActivity(intent)
    }

    private fun setupClickableViews() {
        binding.tvPopularViewAll.setOnClickListener {
            callViewAllActivity("Popular")
        };
        binding.tvCheapViewAll.setOnClickListener {
            callViewAllActivity("Cheap")
        }
        binding.tvNearbyViewAll.setOnClickListener {
            callViewAllActivity("Nearby your location")
        }
    }


    private fun refreshCategoryAdapter(filters: ArrayList<Category>) {
        val adapter = CategoryAdapter(requireContext(), filters, this)
        binding.rvHomeCategory.adapter = adapter

        if (viewModel.categoryListState != null) {
            binding.rvHomeCategory.layoutManager?.onRestoreInstanceState(viewModel.categoryListState)
            viewModel.categoryListState = null
        }
    }

    private fun refreshPopularAdapter(filters: ArrayList<Post>) {
        val adapter = SmallPostsAdapter(requireContext(), filters, this)
        binding.rvHomePopular.adapter = adapter
        if (viewModel.popularListState != null){
            binding.rvHomePopular.layoutManager?.onRestoreInstanceState(viewModel.popularListState)
            viewModel.popularListState = null
        }
    }

    private fun refreshNearbyAdapter(filters: ArrayList<Post>) {
        val adapter = SmallPostsAdapter(requireContext(), filters, this)
        binding.rvHomeNearby.adapter = adapter
        if (viewModel.nearbyListState != null){
            binding.rvHomeNearby.layoutManager?.onRestoreInstanceState(viewModel.nearbyListState)
            viewModel.nearbyListState = null
        }
    }

    private fun refreshCheapAdapter(filters: ArrayList<Post>) {
        val adapter = SmallPostsAdapter(requireContext(), filters, this)
        binding.rvHomeCheap.adapter = adapter
        if (viewModel.cheapListState != null){
            binding.rvHomeCheap.layoutManager?.onRestoreInstanceState(viewModel.cheapListState)
            viewModel.cheapListState = null
        }
    }

    private fun getAllCategory(): ArrayList<Category> {
        val feeds: ArrayList<Category> = ArrayList<Category>()
        feeds.add(Category("Apartment"))
        feeds.add(Category("Villa"))
        feeds.add(Category("House"))
        feeds.add(Category("House"))
        feeds.add(Category("House"))
        feeds.add(Category("House"))

        return feeds
    }

    fun getAllPosts(): ArrayList<Post> {
        val feeds: ArrayList<Post> = ArrayList<Post>()
        feeds.add(
            Post(
                R.drawable.house_1,
                "Test",
                "1",
                "1",
                "1",
                1,
                "1000",
                "Rent",
                "Villa",
                "month",
                null,
                null,
                "Andijan",
                3.4,
                1
            )
        )
        feeds.add(
            Post(
                R.drawable.house_1,
                "Test",
                "1",
                "1",
                "1",
                1,
                "1000",
                "Rent",
                "Villa",
                "month",
                null,
                null,
                "Andijan",
                3.4,
                1
            )
        )
        feeds.add(
            Post(
                R.drawable.house_1,
                "Test",
                "1",
                "1",
                "1",
                1,
                "1000",
                "Rent",
                "Villa",
                "month",
                null,
                null,
                "Andijan",
                3.4,
                1
            )
        )
        feeds.add(
            Post(
                R.drawable.house_1,
                "Test",
                "1",
                "1",
                "1",
                1,
                "1000",
                "Rent",
                "Villa",
                "month",
                null,
                null,
                "Andijan",
                3.4,
                1
            )
        )
        feeds.add(
            Post(
                R.drawable.house_1,
                "Test",
                "1",
                "1",
                "1",
                1,
                "1000",
                "Rent",
                "Villa",
                "month",
                null,
                null,
                "Andijan",
                3.4,
                0
            )
        )
        return feeds
    }

    override fun onItemClickListener(id: String) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra("POST_ID", id)
        startActivity(intent)
    }

    override fun onCategoryItemClickListener(name: String) {
        Toast.makeText(requireContext(), name, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.popularListState = binding.rvHomePopular.layoutManager?.onSaveInstanceState()
        viewModel.cheapListState = binding.rvHomeCheap.layoutManager?.onSaveInstanceState()
        viewModel.nearbyListState = binding.rvHomeNearby.layoutManager?.onSaveInstanceState()
        viewModel.categoryListState = binding.rvHomeCategory.layoutManager?.onSaveInstanceState()
    }




    override fun onResume() {
        super.onResume()
        Logger.d(TAG, "onResume")
    }

    override fun onStart() {
        super.onStart()
        Logger.d(TAG, "onStart")
    }



    private fun callViewAllActivity(name: String) {
        val intent = Intent(requireContext(), ViewAllActivity::class.java)
        intent.putExtra("category_name",name)
        startActivity(intent)
    }


}