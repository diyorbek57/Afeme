package com.ayizor.afeme.activity


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.createpostadapters.PreviewViewPagerAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivityPreviewCreatedPostBinding
import com.ayizor.afeme.manager.PostPrefsManager
import com.ayizor.afeme.model.BuildingMaterial
import com.ayizor.afeme.model.Image
import com.ayizor.afeme.model.Post
import com.ayizor.afeme.model.response.BuildingMaterialResponse
import com.ayizor.afeme.model.response.PostResponse
import com.ayizor.afeme.utils.Logger
import com.google.android.gms.tasks.Task
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class PreviewCreatedPostActivity : AppCompatActivity() {

    lateinit var binding: ActivityPreviewCreatedPostBinding
    var dataService: ApiInterface? = null
    val TAG: String = PreviewCreatedPostActivity::class.java.simpleName
    val imagesUrls: ArrayList<Image> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewCreatedPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inits()
    }

    @SuppressLint("ResourceType")
    private fun inits() {
        dataService = Client.getClient()?.create(ApiInterface::class.java)
        refreshViewPagerAdapter()
        displaySavedDatas()
        getAllBuildingMaterials()
        binding.chipGroupBuildingMaterials.setOnCheckedStateChangeListener { group, checkedIds ->
            Toast.makeText(
                this,
                checkedIds.toString().replace("[", "").replace("]", ""),
                Toast.LENGTH_SHORT
            )
                .show()
        }
        binding.btnPublish.setOnClickListener {
            uploadPost()
        }

    }

    private fun uploadPost() {
        val price = binding.etPrice.editText?.text.toString()
        val type = PostPrefsManager(this).loadPostType()
        val building_type = PostPrefsManager(this).loadBuildingType()
        val built_year = binding.etBuiltYear.editText?.text.toString()
        val latitude = PostPrefsManager(this).loadLatitude()
        val longitude = PostPrefsManager(this).loadLongitude()
        val floor = PostPrefsManager(this).loadFloor().house_floor
        val flat = PostPrefsManager(this).loadFloor().apartment_floor
        val area = PostPrefsManager(this).loadArea()
        val rooms = binding.etRooms.editText?.text.toString()
        val post = Post(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            price,
            type,
            building_type.toString(),
            null,
            built_year,
            latitude,
            longitude,
            33,
            10,
            null,
            rooms,
            null,
            floor,
            flat,
            area,
            null,



        )
        dataService!!.createPost(post)
            .enqueue(object : Callback<PostResponse> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>
                ) {
                    Logger.d(TAG, response.body().toString())
                    response.body()?.status.toString()
//                binding.rvSellType.visibility = View.VISIBLE
//                binding.progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    t.message?.let { Logger.d(TAG, it) }
                    //progressBar!!.visibility = View.GONE
                }
            })
    }

    private fun displaySavedDatas() {
        val area = PostPrefsManager(this).loadArea()
        val floor = PostPrefsManager(this).loadFloor()
        binding.etRooms.editText?.setText(PostPrefsManager(this).loadRooms().toString())
        binding.etTotalArea.editText?.setText(area.total_area)
        binding.etKitchenArea.editText?.setText(area.kitchen_area)
        binding.etLivingSpace.editText?.setText(area.living_area)
        binding.etPrice.editText?.setText(PostPrefsManager(this).loadPrice().toString())
        binding.etFloorsInTheHouse.editText?.setText(floor.house_floor)
        binding.tvDescriptionDetails.setText(PostPrefsManager(this).loadDescription().toString())
//
    }

    private fun refreshViewPagerAdapter() {
        val adapter = PreviewViewPagerAdapter(this, PostPrefsManager(this).loadImages())
        binding.viewpager.adapter = adapter
        binding.viewpager.clipToPadding = false
        binding.viewpager.clipChildren = false
        binding.viewpager.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        val transformer = CompositePageTransformer()

        transformer.addTransformer(MarginPageTransformer(2))
//        transformer.addTransformer { page, position ->
//            val v = 1 - Math.abs(position)
//            page.scaleY = 0.8f + v * 0.2f
//        }
        binding.viewpager.setPageTransformer(transformer)
        binding.viewpager.offscreenPageLimit = PostPrefsManager(this).loadImages().size
    }


    @SuppressLint("ResourceAsColor")
    private fun addMaterialsChip(item: ArrayList<BuildingMaterial>) {
        for (i in 0 until item.size) {
            val chip = Chip(this)
            val chipDrawable: ChipDrawable = ChipDrawable.createFromAttributes(
                this, null, 0,
                R.style.CustomChipGroupStyle
            )
            chip.id = i + 1
            chip.text = item[i].name
            chip.isCheckable = true
            chip.setChipDrawable(chipDrawable)

            binding.chipGroupBuildingMaterials.addView(chip)
        }


    }

    @SuppressLint("ResourceAsColor")
    private fun addExtraChip(item: ArrayList<BuildingMaterial>) {
        for (i in 0 until item.size) {
            val chip = Chip(this)
            val chipDrawable: ChipDrawable = ChipDrawable.createFromAttributes(
                this, null, 0,
                R.style.CustomChipGroupStyle
            )
            chip.id = i + 1
            chip.text = item[i].name
            chip.isCheckable = true
            chip.setChipDrawable(chipDrawable)

            binding.chipGroupBuildingMaterials.addView(chip)
        }


    }

    private fun getAllBuildingMaterials() {
        dataService!!.getAllBuildingMaterials()
            .enqueue(object : Callback<BuildingMaterialResponse> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<BuildingMaterialResponse>,
                    response: Response<BuildingMaterialResponse>
                ) {
                    Logger.d(TAG, response.body().toString())
                    response.body()?.data?.let { addMaterialsChip(it as ArrayList<BuildingMaterial>) }
//                binding.rvSellType.visibility = View.VISIBLE
//                binding.progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<BuildingMaterialResponse>, t: Throwable) {
                    t.message?.let { Logger.d(TAG, it) }
                    //progressBar!!.visibility = View.GONE
                }
            })

    }

    @SuppressLint("SetTextI18n")
    fun uploadImageToFirebase() {
        val urlList: ArrayList<String> = ArrayList()
        val storageReference = FirebaseStorage.getInstance().reference
        val images = PostPrefsManager(this).loadImages()
        for (i in 0 until images.size) {
            storageReference.putFile(Uri.parse(images[i].image_url))
                .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                    storageReference.downloadUrl.addOnCompleteListener { task: Task<Uri?> ->
                        val url: String = Objects.requireNonNull(task.result).toString()
                        urlList.add(url)
                        //if same size so all image is uploaded, then sent list of url to to some method
                        if (urlList.size == images.size) {
                            uploadPost()
                        } else {
                            uploadImageToFirebase()
                        }
                    }
                }
                .addOnFailureListener { e: Exception ->
                    Objects.requireNonNull(e.message)?.let {
                        Log.e(
                            "OnFailureImageListener", it
                        )
                    }
                }
        }


    }


}