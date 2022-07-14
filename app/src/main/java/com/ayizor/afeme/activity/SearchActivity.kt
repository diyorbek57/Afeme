package com.ayizor.afeme.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayizor.afeme.adapter.LargePostsAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivitySearchBinding
import com.ayizor.afeme.fragment.creatpost.BuildningTypeFragment
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.model.response.GetPostResponse
import com.ayizor.afeme.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchActivity : BaseActivity(), LargePostsAdapter.OnLargePostItemClickListener {
    lateinit var binding: ActivitySearchBinding
    val TAG: String = BuildningTypeFragment::class.java.simpleName
    var dataService: ApiInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inits()
    }

    private fun inits() {
        binding.progressBar.visibility = View.GONE
        dataService = Client.getClient(this)?.create(ApiInterface::class.java)
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
        binding.etSearch.isFocusableInTouchMode = true;
        binding.etSearch.requestFocus();

        binding.rvSearchResults.layoutManager = LinearLayoutManager(
           this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.ivFilterSearch.setOnClickListener {
            val intent = Intent(this@SearchActivity, FilterActivity::class.java)
            startActivity(intent)
        }

        binding.etSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.progressBar.visibility = View.VISIBLE
                searchPosts(binding.etSearch.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun searchPosts(keyword: String) {
        dataService!!.searchPosts(
            null,
            null,
            null,
            keyword,
            15,
            null,
            null,
            null,
            null,
            null
        ).enqueue(object : Callback<GetPostResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<GetPostResponse>,
                response: Response<GetPostResponse>
            ) {
                Logger.d(TAG, response.body().toString())
                response.body()?.data?.let { refreshSearchAdapter(it) }
               // binding.rvSearchResults.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<GetPostResponse>, t: Throwable) {
                t.message?.let { Logger.d(TAG, it) }
                //progressBar!!.visibility = View.GONE
            }
        })
    }

    private fun refreshSearchAdapter(filters: ArrayList<GetPost>) {
        val adapter = LargePostsAdapter(this, filters, this)
        binding.rvSearchResults.adapter = adapter
    }

    override fun onLargePostItemClickListener(id: Int, latitude: String, longitude: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("POST_ID", id)
        intent.putExtra("POST_LATITUDE", latitude)
        intent.putExtra("POST_LONGITUDE", longitude)
        startActivity(intent)
    }

}