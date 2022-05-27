package com.ayizor.afeme.fragment


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.FragmentSearchBinding
import com.ayizor.afeme.databinding.ItemBottomSheetSearchBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog


class SearchFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: FragmentSearchBinding
    val TAG: String = SearchFragment::class.java.simpleName
    var isDown = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        inits(savedInstanceState)
        return binding.root
    }

    private fun inits(savedInstanceState: Bundle?) {

        binding.mapViewSearch.onCreate(savedInstanceState)
        binding.mapViewSearch.getMapAsync(this)
        binding.mapViewSearch.setOnClickListener {
            if (isDown) {
                slideDown(binding.cvSearch);
            } else {

                slideUp(binding.cvSearch);

            }
            isDown = !isDown;
        }

    }


    private fun showBottomSheet() {
        val sheetDialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        val bottomSheetBinding: ItemBottomSheetSearchBinding =
            ItemBottomSheetSearchBinding.inflate(layoutInflater)
        sheetDialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.tvNamePostLargeSearch.text = "Post name"

        sheetDialog.show()
        sheetDialog.window?.attributes?.windowAnimations =
            R.style.DialogAnimaton;


    }


    override fun onMapReady(googleMap: GoogleMap) {
        val latitude = 40.747457
        val longitude = 72.359775
        val postLocation = LatLng(latitude, longitude)
        googleMap.uiSettings.isZoomGesturesEnabled = true;
        googleMap.uiSettings.isScrollGesturesEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(postLocation, 16f))
        // create marker
        val marker: MarkerOptions =
            MarkerOptions().position(LatLng(latitude, longitude))
        // Changing marker icon
        // adding marker
        googleMap.addMarker(marker);
        googleMap.addCircle(
            CircleOptions().center(LatLng(latitude, longitude)).radius(100.0)
                .strokeColor(Color.parseColor("#2972FE")).fillColor(Color.parseColor("#6499FF"))
        )
        googleMap.setOnMapClickListener {
            if (isDown) {
                slideDown(binding.cvSearch);
            } else {

                slideUp(binding.cvSearch);

            }
            isDown = !isDown;
        }
        googleMap.setOnMarkerClickListener(object :GoogleMap.)
    }

    // slide the view from below itself to the current position
    fun slideDown(view: View) {
        view.visibility = View.VISIBLE
        val slide_down: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.slide_down
        )
        view.startAnimation(slide_down)
    }

    // slide the view from its current position to below itself
    fun slideUp(view: View) {
        view.visibility = View.GONE
        val slide_up: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.slide_up
        )
        view.startAnimation(slide_up)
    }

    override fun onResume() {
        binding.mapViewSearch.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapViewSearch.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapViewSearch.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapViewSearch.onLowMemory()
    }

}
