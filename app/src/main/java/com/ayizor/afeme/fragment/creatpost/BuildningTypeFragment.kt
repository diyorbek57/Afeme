package com.ayizor.afeme.fragment.creatpost

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.CreatePostBuildingTypeAdapter
import com.ayizor.afeme.api.ApiInterface
import com.ayizor.afeme.api.Client
import com.ayizor.afeme.databinding.FragmentBuildningTypeBinding
import com.ayizor.afeme.eventbus.MessageEvent
import com.ayizor.afeme.model.Category
import com.ayizor.afeme.model.response.CategoryResponse
import com.ayizor.afeme.utils.Logger
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BuildningTypeFragment : Fragment(),
    CreatePostBuildingTypeAdapter.OnBuildingTypeItemClickListener {
    lateinit var binding: FragmentBuildningTypeBinding
    val TAG: String = BuildningTypeFragment::class.java.simpleName
    var dataService: ApiInterface? = null
    var fragmentNumber = 3
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBuildningTypeBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        activity?.findViewById<ProgressBar>(R.id.progress_bar_main_creat_post)?.progress=2

        binding.progressBar.visibility = View.VISIBLE
        dataService = Client.getClient()?.create(ApiInterface::class.java)
        binding.rvBuildingType.layoutManager = GridLayoutManager(
            requireContext(), 2, GridLayoutManager.VERTICAL, false
        )




        getAllCategory()
    }

    private fun refreshCategoryAdapter(filters: ArrayList<Category>) {
        val adapter = CreatePostBuildingTypeAdapter(requireContext(), filters, this)
        binding.rvBuildingType.adapter = adapter


    }

    private fun getAllCategory() {
        dataService!!.getAllCategory().enqueue(object : Callback<CategoryResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                Logger.d(TAG, response.body().toString())
                response.body()?.data?.let { refreshCategoryAdapter(it) }
                binding.rvBuildingType.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                t.message?.let { Logger.d(TAG, it) }
                //progressBar!!.visibility = View.GONE
            }
        })

    }

    override fun onBuildingTypeItemClickListener(name: Int?) {

    }

}