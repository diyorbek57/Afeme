package com.ayizor.afeme.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayizor.afeme.adapter.LargePostsAdapter
import com.ayizor.afeme.databinding.ActivityViewAllBinding
import com.ayizor.afeme.model.post.GetPost

class ViewAllActivity : BaseActivity(){

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
      //  refreshViewAllAdapter(getAllPosts())
    }

//    override fun onViewAllItemClickListener(id: String) {
//        val intent = Intent(this, DetailsActivity::class.java)
//        intent.putExtra("POST_ID", id)
//        startActivity(intent)
//    }

//    private fun refreshViewAllAdapter(filters: ArrayList<GetPost>) {
//        val adapter = LargePostsAdapter(this, filters, this)
//        binding.rvViewAll.adapter = adapter
//
//    }




}