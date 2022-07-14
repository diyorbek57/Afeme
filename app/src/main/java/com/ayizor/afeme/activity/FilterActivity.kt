package com.ayizor.afeme.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivityFilterBinding
import com.ayizor.afeme.model.response.CategoryResponse
import com.ayizor.afeme.model.response.RegionsResponse
import com.ayizor.afeme.model.response.SellResponse
import com.ayizor.afeme.utils.Logger
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FilterActivity : AppCompatActivity() {
    lateinit var binding: ActivityFilterBinding
    var dataService: ApiInterface? = null
    val TAG: String = FilterActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
    }

    private fun inits() {
        dataService = Client.getClient(this)?.create(ApiInterface::class.java)

        getAllSellTypes()
        getAllCategory()
        getAllRegions()
    }


    private fun getAllSellTypes() {
        dataService!!.getAllSellTypes().enqueue(object : Callback<SellResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<SellResponse>,
                response: Response<SellResponse>
            ) {
                Logger.d(TAG, response.body().toString())
                val selTypes = response.body()?.data
                val spinnerItems: MutableList<IconSpinnerItem> = ArrayList()
                for (i in 0 until selTypes?.size!!) {
                    selTypes[i].post_type_name_en?.let { IconSpinnerItem(it) }?.let {
                        spinnerItems.add(
                            it
                        )
                    }
                }
                val iconSpinnerAdapter = IconSpinnerAdapter(binding.spinnerCategory)
                binding.spinnerCategory.setSpinnerAdapter(iconSpinnerAdapter)
                binding.spinnerCategory.setItems(spinnerItems)


            }

            override fun onFailure(call: Call<SellResponse>, t: Throwable) {
                t.message?.let { Logger.d(TAG, it) }
                //progressBar!!.visibility = View.GONE
            }
        })

    }

    private fun getAllCategory() {
        dataService!!.getAllCategory().enqueue(object : Callback<CategoryResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                Logger.d(TAG, response.body().toString())
                val category = response.body()?.data
                val spinnerItems: MutableList<IconSpinnerItem> = ArrayList()
                for (i in 0 until category?.size!!) {
                    category[i].category_name_en?.let { IconSpinnerItem(it) }?.let {
                        spinnerItems.add(
                            it
                        )
                    }
                }
                val iconSpinnerAdapter = IconSpinnerAdapter(binding.spinnerPostType)
                binding.spinnerPostType.setSpinnerAdapter(iconSpinnerAdapter)
                binding.spinnerPostType.setItems(spinnerItems)

            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                t.message?.let { Logger.d(TAG, it) }
                //progressBar!!.visibility = View.GONE
            }
        })
    }
    private fun getAllRegions() {
        dataService!!.getRegions().enqueue(object : Callback<RegionsResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<RegionsResponse>,
                response: Response<RegionsResponse>
            ) {
                Logger.d(TAG, response.body().toString())
                val category = response.body()?.data
                val spinnerItems: MutableList<IconSpinnerItem> = ArrayList()
                for (i in 0 until category?.size!!) {
                    category[i].region_name_en?.let { IconSpinnerItem(it) }?.let {
                        spinnerItems.add(
                            it
                        )
                    }
                }
                val iconSpinnerAdapter = IconSpinnerAdapter(binding.spinnerPostType)
                binding.spinnerPostType.setSpinnerAdapter(iconSpinnerAdapter)
                binding.spinnerPostType.setItems(spinnerItems)

            }

            override fun onFailure(call: Call<RegionsResponse>, t: Throwable) {
                t.message?.let { Logger.d(TAG, it) }
                //progressBar!!.visibility = View.GONE
            }
        })

    }
}