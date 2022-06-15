package com.ayizor.afeme.activity


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
import com.ayizor.afeme.model.*
import com.ayizor.afeme.model.response.BuildingMaterialResponse
import com.ayizor.afeme.model.response.GetPostResponse
import com.ayizor.afeme.model.response.PostResponse
import com.ayizor.afeme.utils.Logger
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.firebase.storage.FirebaseStorage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.POST
import java.io.File
import java.io.FileOutputStream


class PreviewCreatedPostActivity : AppCompatActivity() {

    lateinit var binding: ActivityPreviewCreatedPostBinding
    var dataService: ApiInterface? = null
    val TAG: String = PreviewCreatedPostActivity::class.java.simpleName
    val imagesUrls: ArrayList<Image> = ArrayList()
    private val storageReference = FirebaseStorage.getInstance().reference
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

            uploadPost(convertToFile())
        }

    }

    private fun convertToFile(): ArrayList<ImagePost> {
        val imagesList = PostPrefsManager(this).loadImages()
        val postImagesList: ArrayList<ImagePost> = ArrayList()
        for (i in 0 until imagesList.size) {
            postImagesList.add(ImagePost(null, null, getFile(Uri.parse(imagesList[i].image_url))))
        }
        return postImagesList
    }

    private fun uploadPost(imageUrls: ArrayList<ImagePost>) {
        val requestBodyList: ArrayList<RequestBody> = ArrayList()
        val builder: MultipartBody.Builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        for (i in 0 until imageUrls.size) {
            if (imageUrls[i].image_url?.length()!! > 0) {
                imageUrls[i].image_url?.asRequestBody("image/jpeg".toMediaTypeOrNull())?.let {
                    builder.addFormDataPart(
                        "file",
                        imageUrls[i].image_url?.name,
                        it
                    )
                }
                builder.addFormDataPart("sub_id", "something")

                val body = builder.build()
                Logger.d(TAG, "post:${body.parts}")
                requestBodyList.add(body)
            }
        }
        val price = binding.etPrice.editText?.text.toString()
        val type = PostPrefsManager(this).loadPostType()
        val building_type = PostPrefsManager(this).loadBuildingType()
        val built_year = binding.etBuiltYear.editText?.text.toString()
        val description = binding.tvDescriptionDetails.text.toString()
        val latitude = PostPrefsManager(this).loadLatitude()
        val longitude = PostPrefsManager(this).loadLongitude()
        val floor = PostPrefsManager(this).loadFloor().house_floor
        val flat = PostPrefsManager(this).loadFloor().apartment_floor
        val area = PostPrefsManager(this).loadArea()
        val rooms = binding.etRooms.editText?.text.toString()
        val material = Material(null, "Beton", null, null)
        val post: ArrayList<Post> =ArrayList()

       val post_values= Post(
            null,
            null,//r
            null,
            null,//r
            building_type.toString(),//r
            type,//r
            latitude,//r
            longitude,//r
            price,
            null,
            area,
            built_year,//r
            rooms,//r
            "1",//r
            "5",//r
            description,
            material,//r
            "12",//r
            "23",//r
            "1",//r
            "1",//r
            floor,
            flat,
            false,
            null,
            null
        )
        post.add(post_values)
    //    Logger.d(TAG, "data: "+dataService!!.createPost(post).request().body)

        dataService!!.createPost(post_values)
            .enqueue(object : Callback<PostResponse> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>
                ) {
                    Logger.d(TAG, "status:" + response.body()?.status.toString())
                    Logger.d(TAG, "message:" + response.body()?.message.toString())
                    Logger.d(TAG, "data:" + response.body()?.data.toString())
                    Logger.d(TAG, "response message:" + response.message())
                    Logger.d(TAG, "response isSuccessful:" + response.isSuccessful)
                    Logger.d(TAG, "response message:$response")
                    Logger.d(TAG, "response isSuccessful:" + response.errorBody().toString())
                    ///  response.body()?.status.toString()
//                binding.rvSellType.visibility = View.VISIBLE
//                binding.progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    "message:" + t.message?.let { Logger.d(TAG, it) }
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
        binding.tvDescriptionDetails.setText(
            PostPrefsManager(this).loadDescription().toString()
        )
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

    private fun getFile(uri: Uri): File {
        val ins = this.contentResolver.openInputStream(uri)
        val file = File.createTempFile(
            "file",
            ".jpg",
            this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        val fileOutputStream = FileOutputStream(file)
        ins?.copyTo(fileOutputStream)
        ins?.close()
        fileOutputStream.close()
        return file
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


}