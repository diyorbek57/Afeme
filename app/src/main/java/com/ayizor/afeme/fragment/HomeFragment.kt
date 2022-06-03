package com.ayizor.afeme.fragment

import android.annotation.SuppressLint
import android.content.Context
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
import com.ayizor.afeme.api.ApiInterface
import com.ayizor.afeme.api.Client
import com.ayizor.afeme.databinding.FragmentHomeBinding
import com.ayizor.afeme.model.Category
import com.ayizor.afeme.model.response.CategoryResponse
import com.ayizor.afeme.model.Post
import com.ayizor.afeme.utils.Logger

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), SmallPostsAdapter.OnItemClickListener,
    CategoryAdapter.OnCategoryItemClickListener {

    lateinit var binding: FragmentHomeBinding
    val TAG: String = HomeFragment::class.java.simpleName
    var dataService: ApiInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        binding.progressBar.visibility=View.VISIBLE
        dataService = Client.getClient()?.create(ApiInterface::class.java)
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
        getAllCategory()
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


    }

    private fun refreshPopularAdapter(filters: ArrayList<Post>) {
        val adapter = SmallPostsAdapter(requireContext(), filters, this)
        binding.rvHomePopular.adapter = adapter

    }

    private fun refreshNearbyAdapter(filters: ArrayList<Post>) {
        val adapter = SmallPostsAdapter(requireContext(), filters, this)
        binding.rvHomeNearby.adapter = adapter

    }

    private fun refreshCheapAdapter(filters: ArrayList<Post>) {
        val adapter = SmallPostsAdapter(requireContext(), filters, this)
        binding.rvHomeCheap.adapter = adapter
    }


    private fun getAllCategory() {
        dataService!!.getAllCategory().enqueue(object : Callback<CategoryResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
               Logger.d("Category", response.body().toString())
                response.body()?.data?.let { refreshCategoryAdapter(it) }
                binding.llMainHome.visibility =View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                t.message?.let { Logger.d("Category", it) }
                //progressBar!!.visibility = View.GONE
            }
        })

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




    override fun onAttach(context: Context) {
        super.onAttach(context)

        Logger.d(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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