package com.ayizor.afeme.fragment.creatpost

import android.Manifest
import android.R.attr
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.createpostadapters.ChoosePhotoAdapter
import com.ayizor.afeme.databinding.FragmentPhotosBinding
import com.ayizor.afeme.manager.PostPrefsManager
import com.ayizor.afeme.model.Image
import com.ayizor.afeme.model.ImageDetails
import com.ayizor.afeme.utils.Logger


class PhotosFragment : Fragment(), ChoosePhotoAdapter.OnChoosePhotoItemClickListener {


    lateinit var binding: FragmentPhotosBinding
    val TAG: String = BuildningTypeFragment::class.java.simpleName
    var fragmentNumber = 7
    lateinit var adapter: ChoosePhotoAdapter

    var mArrayUri: ArrayList<Image> = ArrayList();
    val REQUEST_CODE = 200
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotosBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        activity?.findViewById<ProgressBar>(R.id.progress_bar_main_creat_post)?.progress =
            fragmentNumber

        binding.rvChoosePhotosCreatPost.layoutManager = GridLayoutManager(
            requireContext(), 2, GridLayoutManager.VERTICAL, false
        )
        binding.btnNext.setOnClickListener {
            if (mArrayUri.isEmpty()) {

                checkReadExternalPermissions()

                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_CODE);
            } else {
                PostPrefsManager(requireContext()).storeImages(mArrayUri)
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    .replace(R.id.fragment_container_creat_post, PriceFragment())
                    .addToBackStack(PhotosFragment::class.java.name).commit()
            }


        }

    }

    private fun refreshCategoryAdapter(filters: ArrayList<Image>) {
        val imageDetails: ArrayList<ImageDetails> = ArrayList()
        adapter = ChoosePhotoAdapter(requireContext(), filters, this)
        binding.rvChoosePhotosCreatPost.adapter = adapter


    }

    override fun onChoosePhotoItemClickListener(id: Int) {

    }

    // Check if location permissions are
    // granted to the application
    private fun checkReadExternalPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {

            // if multiple images are selected
            if (data?.getClipData() != null) {
                val count = data.clipData?.itemCount

                for (i in 0 until count!!) {
                    val imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                    mArrayUri.add(Image(i, imageUri.toString()))
                }
                binding.btnNext.text = getString(R.string.next)
                refreshCategoryAdapter(mArrayUri)
            } else if (data?.getData() != null) {
                // if single image is selected

                val imageUri: Uri = data.data!!
                mArrayUri.add(Image(null, imageUri.toString()))
                refreshCategoryAdapter(mArrayUri)
            }
        }

    }


}