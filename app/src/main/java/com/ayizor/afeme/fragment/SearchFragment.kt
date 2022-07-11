package com.ayizor.afeme.fragment


import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Pair.create
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
import com.ayizor.afeme.activity.SearchActivity
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.FragmentSearchBinding
import com.ayizor.afeme.manager.PrefsManager
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.model.response.GetPostResponse
import com.ayizor.afeme.utils.Extensions.toast
import com.ayizor.afeme.utils.Logger
import com.ayizor.afeme.utils.Utils
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
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
    var postsList: ArrayList<GetPost> = ArrayList()
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
        getPosts()
        binding.mapViewSearch.onCreate(savedInstanceState)
        binding.mapViewSearch.getMapAsync(this)

        binding.rlSearch.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)

            val searchFieldPair = create<View, String>(binding.rlSearch, "search_field")
            val filterPair = create<View, String>(binding.ivSearchFilter, "filter_field")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options =
                    ActivityOptions.makeSceneTransitionAnimation(
                        requireActivity(),
                        searchFieldPair,
                        filterPair
                    )
                startActivity(intent, options.toBundle())
            }
        }

    }


    private fun getPosts() {


    }

    override fun onMapReady(googleMap: GoogleMap) {


        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
                if (newState != BottomSheetBehavior.STATE_DRAGGING && newState == BottomSheetBehavior.STATE_EXPANDED) {
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

        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(41.303322, 69.256782)).zoom(6f).build()

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        Logger.d(TAG, "Map is ready")

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
                    for (i in 0 until postsList.size) {
                        Logger.e(TAG, "marker loop started")
                        val latlang = LatLng(
                            postsList[i].post_latitude!!.toDouble(),
                            postsList[i].post_longitude!!.toDouble()
                        )
                        myMarker = googleMap.addMarker(MarkerOptions().position(latlang))
                        myMarker?.tag = postsList[i]
                        Logger.e(TAG, "marker position: $latlang")

                        // create marker
                        myMarker?.let { postsMarkerMap?.put(it, postsList[i]) }
                    }
                    // Changing marker icon
                    // adding marker
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
        val post: GetPost = marker.tag as GetPost
        val locationName = Utils.getCoordinateName(
            requireContext(),
            marker.position.latitude,
            marker.position.longitude
        )
        if (locationName.state.isNullOrEmpty() && locationName.city.isNullOrEmpty()) {
            binding.tvLocationPostLargeSearch.visibility = View.GONE

        }
        binding.tvLocationPostLargeSearch.text = locationName.state + locationName.city
        try {
            Glide.with(requireActivity()).load(
                Utils.replaceWords(
                    post.post_images?.get(0)?.image_url.toString(),
                    "http",
                    "https"
                )
            )
                .into(binding.ivImagePostLargeSearch)
        } catch (e: ArrayIndexOutOfBoundsException) {
            Logger.e(TAG, e.message.toString())
            Glide.with(requireActivity()).load(
                Utils.replaceWords(
                    post.post_images?.get(1)?.image_url.toString(),
                    "http",
                    "https"
                )
            )
                .into(binding.ivImagePostLargeSearch)
        } catch (e: Exception) {
            Logger.e(TAG, e.message.toString())
            Glide.with(requireActivity()).load(R.drawable.house_1)
                .into(binding.ivImagePostLargeSearch)
        }


        binding.tvPricePostLargeSearch.text = post.post_price_usd?.let { Utils.formatUsd(it) }
        binding.tvNamePostLargeSearch.text =
            locationName.state + ", " + post.post_rooms + getString(R.string.rooms)


    }
}
