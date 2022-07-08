package com.ayizor.afeme.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.FragmentSearchBinding
import com.ayizor.afeme.manager.PrefsManager
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.model.response.GetPostResponse
import com.ayizor.afeme.utils.Extensions.toast
import com.ayizor.afeme.utils.Logger
import com.ayizor.afeme.utils.Utils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    lateinit var binding: FragmentSearchBinding
    val TAG: String = SearchFragment::class.java.simpleName
    var isDown = false
    lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>
    private var myMarker: Marker? = null
    private val postsMarkerMap: HashMap<Marker, GetPost>? = null
    var dataService: ApiInterface? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetSearch)
        inits(savedInstanceState)
        return binding.root
    }

    private fun inits(savedInstanceState: Bundle?) {
        dataService = Client.getClient(requireContext())?.create(ApiInterface::class.java)

        binding.mapViewSearch.onCreate(savedInstanceState)
        binding.mapViewSearch.getMapAsync(this)


    }


    private fun getPosts(): ArrayList<GetPost> {
        var postsList: ArrayList<GetPost> = ArrayList()
        dataService?.getAllPosts()?.enqueue(object : Callback<GetPostResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<GetPostResponse>,
                response: Response<GetPostResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    Logger.d(TAG, "isSuccessful data: " + response.body()?.data.toString())
                    Logger.d(TAG, "isSuccessful  code: " + response.code())
                    postsList = response.body()?.data!!
//                binding.rvSellType.visibility = View.VISIBLE
//                binding.progressBar.visibility = View.GONE
                } else {
                    Logger.e(TAG, "error data: " + response.body()?.data.toString())
                    Logger.e(TAG, "error  code: " + response.code())
                    Logger.e(TAG, "error  errorBody: " + response.errorBody().toString())
                    Logger.e(TAG, "error  message: " + response.message().toString())
                    toast(response.message())

                }

            }

            override fun onFailure(call: Call<GetPostResponse>, t: Throwable) {
                t.message?.let { Logger.d(TAG, it) }
                toast(t.message.toString())
                //progressBar!!.visibility = View.GONE
            }
        })
        return postsList
    }

    override fun onMapReady(googleMap: GoogleMap) {


        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    slideUp(binding.cvSearch);
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    slideDown(binding.cvSearch);
                }
            }

            override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {
                // React to dragging events
            }
        })
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
        Logger.d(TAG, "Map is ready")
        val posts = getPosts()
        Logger.e(TAG, "posts = getPosts")
        Logger.e(TAG, posts.toString())
        for (i in 0 until posts.size) {
            Logger.e(TAG, "marker loop started")
            val latlang = LatLng(
                posts[i].post_latitude!!.toDouble(),
                posts[i].post_longitude!!.toDouble()
            )
            myMarker = googleMap.addMarker(MarkerOptions().position(latlang))
            myMarker?.tag = posts[i]
            Logger.e(TAG, "marker position: $latlang")

            // create marker
            myMarker?.let { postsMarkerMap?.put(it, posts[i]) }
        }
        // Changing marker icon
        // adding marker


    }

    // slide the view from below itself to the current position
    private fun slideDown(view: View) {
        view.visibility = View.VISIBLE
        val slide_down: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.slide_down
        )
        view.startAnimation(slide_down)
    }

    // slide the view from its current position to below itself
    private fun slideUp(view: View) {
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
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        showBottomSheet(marker)
        return false

    }

    private fun showBottomSheet(marker: Marker) {

        val location = Utils.getCoordinateName(
            requireContext(),
            marker.position.latitude,
            marker.position.longitude
        )


    }
}
