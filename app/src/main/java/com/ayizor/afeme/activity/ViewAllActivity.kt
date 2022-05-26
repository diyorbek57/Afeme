package com.ayizor.afeme.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.ViewAllAdapter
import com.ayizor.afeme.databinding.ActivityViewAllBinding
import com.ayizor.afeme.model.Post

class ViewAllActivity : BaseActivity(), ViewAllAdapter.OnViewAllItemClickListener {

    lateinit var binding: ActivityViewAllBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
    }

    private fun inits() {
        binding.tvTitle.text = intent.getStringExtra("category_name")
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.rvViewAll.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        refreshViewAllAdapter(getAllPosts())
    }

    override fun onViewAllItemClickListener(id: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("POST_ID", id)
        startActivity(intent)
    }

    private fun refreshViewAllAdapter(filters: ArrayList<Post>) {
        val adapter = ViewAllAdapter(this, filters, this)
        binding.rvViewAll.adapter = adapter

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
                1
            )
        )
        return feeds
    }


}