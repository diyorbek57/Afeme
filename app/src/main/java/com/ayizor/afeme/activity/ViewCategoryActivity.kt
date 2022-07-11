package com.ayizor.afeme.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayizor.afeme.adapter.LargePostsAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivityViewCategoryBinding
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.model.response.GetPostResponse
import com.ayizor.afeme.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewCategoryActivity : AppCompatActivity(), LargePostsAdapter.OnLargePostItemClickListener {

    lateinit var binding: ActivityViewCategoryBinding
    var category_id = 0
    val TAG: String = ViewCategoryActivity::class.java.simpleName
    var dataService: ApiInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
    }

    private fun inits() {
        binding.progressBar.visibility = View.VISIBLE
        dataService = Client.getClient(this)?.create(ApiInterface::class.java)
        binding.tvTitle.text = intent.getStringExtra("category_name")
        category_id = intent.getIntExtra("category_id", 0)
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.rvViewAll.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        getAllPosts(category_id)
    }


    private fun refreshPostsAdapter(filters: ArrayList<GetPost>) {
        val adapter = LargePostsAdapter(this, filters, this)
        binding.rvViewAll.adapter = adapter

    }

    private fun getAllPosts(id: Int) {
        dataService!!.getPostsByCategory(id)
            .enqueue(object : Callback<GetPostResponse> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<GetPostResponse>,
                    response: Response<GetPostResponse>
                ) {
                    Logger.d(TAG, response.body()?.status.toString())
                    response.body()?.data?.let { refreshPostsAdapter(it) }
                    binding.rvViewAll.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<GetPostResponse>, t: Throwable) {
                    t.message?.let { Logger.d(TAG, it) }
                    //progressBar!!.visibility = View.GONE
                }
            })

    }

    override fun onLargePostItemClickListener(id: Int, latitude: String, longitude: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("POST_ID", id)
        intent.putExtra("POST_LATITUDE", latitude)
        intent.putExtra("POST_LONGITUDE", longitude)
        startActivity(intent)

    }
}