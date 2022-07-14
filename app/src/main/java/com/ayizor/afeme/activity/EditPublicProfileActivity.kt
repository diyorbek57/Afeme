package com.ayizor.afeme.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.exifinterface.media.ExifInterface
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivityEditPublicProfileBinding
import com.ayizor.afeme.manager.UserPrefsManager
import com.ayizor.afeme.model.User
import com.ayizor.afeme.model.response.MainResponse
import com.ayizor.afeme.model.response.UserResponse
import com.ayizor.afeme.utils.Logger
import com.ayizor.afeme.utils.Utils
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*


class EditPublicProfileActivity : BaseActivity() {

    lateinit var binding: ActivityEditPublicProfileBinding
    var dataService: ApiInterface? = null
    lateinit var profile_image: String
    lateinit var profile_image_url: String
    val TAG: String = EditPublicProfileActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPublicProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inits()
    }

    private fun inits() {
        dataService = Client.getClient(this)?.create(ApiInterface::class.java)
        getUserDatas()
        binding.tvEditProfileImages.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)
        }

        binding.tvDone.setOnClickListener {
            uploadImageUser()
        }
    }

    private fun getUserDatas() {
        dataService!!.getCurrentUser().enqueue(object : Callback<UserResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Logger.d("Profile", response.body()?.data.toString())
                response.body()?.data?.let { displayUserDatas(it) }
                //progressBar!!.visibility = View.GONE
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.message?.let { Logger.d("Profile", it) }
                //progressBar!!.visibility = View.GONE
            }
        })

    }

    private fun displayUserDatas(user: User) {
        binding.etFirstName.setText(user.user_name)
        binding.etLastName.setText(user.user_last_name)
        if (!user.user_description.isNullOrEmpty())
            binding.etDescription.setText(user.user_description)
        if (!user.user_photo.isNullOrEmpty())
            Glide.with(this).load(Utils.replaceWords(user.user_photo, "http", "https")).into(binding.ivUsersProfile)
    }

    private fun uploadImageUser() {
        saveBitmapToFile(getFile(Uri.parse(profile_image)))?.let { getFileUrl(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            try {
                val imageUri: Uri? = data?.data
                val imageStream: InputStream? =
                    imageUri?.let { contentResolver.openInputStream(it) }
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                profile_image = selectedImage.toString()
                Glide.with(this).load(selectedImage).into(binding.ivUsersProfile)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                // Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        } else {
            // Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }

    private fun getFileUrl(file: File) {
        dataService?.uploadFile(getMultipartBody("file", file), file.name, "Service For C Group")
            ?.enqueue(object : Callback<MainResponse> {
                override fun onResponse(
                    call: Call<MainResponse>,
                    response: Response<MainResponse>
                ) {
                    profile_image_url = response.body()?.data.toString()
                    Logger.d(TAG, response.body()?.data.toString())
                    if (!profile_image_url.isNullOrEmpty()) {
                        Logger.d(TAG, "uploadPost started")
                        updateUser(profile_image_url)
                    }
                }

                override fun onFailure(call: Call<MainResponse>, t: Throwable) {

                }

            })
    }

    private fun updateUser(profileImageUrl: String) {
        showLoading(this@EditPublicProfileActivity)
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val phoneNumber = binding.etPhoneNumber.text.toString()
        val email = binding.etEmail.text.toString()
        val profileType = UserPrefsManager(this).loadUserProfileType()
        val deviceId = Utils.getDeviceID(this)
        val description = binding.etDescription.text.toString()
        val id = UserPrefsManager(this).loadUserId()

        val user = User(
            null,
            id,
            firstName,
            lastName,
            profileImageUrl,
            email,
            phoneNumber,
            null,
            profileType,
            deviceId,
            "Android",
            "password",
            "22",
            null,
            description,
            null,
            null,
            null,
            null
        )

        if (id != null) {
            dataService!!.updateSingleUser(id, user).enqueue(object : Callback<MainResponse> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<MainResponse>,
                    response: Response<MainResponse>
                ) {
                    if (response.isSuccessful) {
                        Logger.d(TAG, "success message: " + response.body()?.message)
                        Logger.d(TAG, "success status: " + response.body()?.status)
                        Logger.d(TAG, "success data: " + response.body()?.data)
                        Logger.d(TAG, "success data: " + response.errorBody().toString())
                        Logger.d(TAG, "success data: " + response.body()?.data)
                        dismissLoading()
                        finish()

                    } else {
                        Logger.d(TAG, "error message: " + response.body()?.message)
                        Logger.d(TAG, "error status: " + response.body()?.status)
                        Logger.d(TAG, "error data: " + response.body()?.data)
                        Logger.d(TAG, "error data: " + response.errorBody().toString())
                        Logger.d(TAG, "error code: " + response.code())
                        dismissLoading()
                    }
                }

                override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                    Logger.d(TAG, "message: " + call.isExecuted)
                }

            })
        }
    }

    fun getMultipartBody(key: String, file: File): MultipartBody.Part {
        val reqFile: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(key, file.name, reqFile)
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

    fun saveBitmapToFile(file: File): File? {
        return try {

            // BitmapFactory options to downsize the image
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image
            var inputStream = FileInputStream(file)
            val oldExif = ExifInterface(file.path)
            val exifOrientation = oldExif.getAttribute(ExifInterface.TAG_ORIENTATION)
            if (exifOrientation != null) {
                val newExif = ExifInterface(file.path)
                newExif.setAttribute(ExifInterface.TAG_ORIENTATION, exifOrientation)
                newExif.saveAttributes()
            }
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()

            // The new size we want to scale to
            val REQUIRED_SIZE = 75

            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)
            val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()

            // here i override the original image file
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            file
        } catch (e: Exception) {
            null
        }
    }

}