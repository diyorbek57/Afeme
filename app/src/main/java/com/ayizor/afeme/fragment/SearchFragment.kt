package com.ayizor.afeme.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.FragmentSearchBinding
import com.ayizor.afeme.databinding.ItemBottomSheetSearchBinding
import com.ayizor.afeme.manager.PrefsManager
import com.ayizor.afeme.utils.Logger
import com.ayizor.afeme.utils.Utils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class SearchFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    lateinit var binding: FragmentSearchBinding
    val TAG: String = SearchFragment::class.java.simpleName
    var isDown = false

    private var myMarker: Marker? = null
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


    private fun showBottomSheet(marker: Marker) {
        val sheetDialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        val bottomSheetBinding: ItemBottomSheetSearchBinding =
            ItemBottomSheetSearchBinding.inflate(layoutInflater)
        sheetDialog.setContentView(bottomSheetBinding.root)
        val location = Utils.getCoordinateName(
            requireContext(),
            marker.position.latitude,
            marker.position.longitude
        )
        bottomSheetBinding.tvNamePostLargeSearch.text = "Post name"
        bottomSheetBinding.tvLocationPostLargeSearch.text = location.state + location.city
        sheetDialog.show()
        sheetDialog.window?.attributes?.windowAnimations =
            R.style.DialogAnimaton;


    }


    override fun onMapReady(googleMap: GoogleMap) {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetSearch)
        val latLngList: ArrayList<LatLng> = ArrayList()
        val latLng = LatLng(40.776570, 72.344192)
        val latLng2 = LatLng(40.778971, 72.360888)
        val latLng3 = LatLng(40.770889, 72.362367)
        val latLng4 = LatLng(40.763686, 72.359514)
        latLngList.add(latLng)
        latLngList.add(latLng2)
        latLngList.add(latLng3)
        latLngList.add(latLng4)
        // val postLocation = LatLng(latitude, longitude)
        googleMap.uiSettings.isZoomGesturesEnabled = true;
        googleMap.uiSettings.isScrollGesturesEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = false;
        googleMap.setOnMarkerClickListener(this);

        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                PrefsManager(requireContext()).loadUserCurrentLocation(),
                1F
            )
        )
        Logger.d(TAG, "Map is redy")
        for (i in 0 until latLngList.size) {

            // create marker
            myMarker = googleMap.addMarker(MarkerOptions().position(latLngList[i]));
        }
        // Changing marker icon
        // adding marker

        googleMap.setOnMapClickListener {
            bottomSheetBehavior.setPeekHeight(375)
            bottomSheetBehavior.isHideable = false
        }
        googleMap.setOnMapClickListener {
            if (isDown) {
                slideDown(binding.cvSearch);
            } else {

                slideUp(binding.cvSearch);

            }
            isDown = !isDown;
        }

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


    override fun onMarkerClick(marker: Marker): Boolean {

        Toast.makeText(requireContext(), marker.position.toString(), Toast.LENGTH_LONG).show()
        showBottomSheet(marker)
        return false

    }
}
