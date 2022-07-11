package com.ayizor.afeme.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayizor.afeme.activity.DetailsActivity
import com.ayizor.afeme.activity.NotificationActivity
import com.ayizor.afeme.activity.ViewCategoryActivity
import com.ayizor.afeme.adapter.CategoryAdapter
import com.ayizor.afeme.adapter.LargePostsAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.FragmentHomeBinding
import com.ayizor.afeme.manager.PrefsManager
import com.ayizor.afeme.model.Category
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.model.response.CategoryResponse
import com.ayizor.afeme.model.response.GetPostResponse
import com.ayizor.afeme.utils.Extensions.toast
import com.ayizor.afeme.utils.Logger
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), LargePostsAdapter.OnLargePostItemClickListener,
    CategoryAdapter.OnCategoryItemClickListener {

    lateinit var binding: FragmentHomeBinding
    val TAG: String = HomeFragment::class.java.simpleName
    var dataService: ApiInterface? = null
    private val pERMISSION_ID = 45
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    var currentLocation: LatLng = LatLng(1.00, 1.00)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        PrefsManager(requireContext()).storeUserCurrentLocation(getLastLocation())
        binding.progressBar.visibility = View.VISIBLE
        dataService = Client.getClient(requireContext())?.create(ApiInterface::class.java)
        binding.ivNotificationsHome.setOnClickListener {
            callNotificationsActivity()
        }
        binding.rvHomeCategory.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvHomePopular.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.swipeRefresh.setOnRefreshListener {
            getPopularPosts()

        }
        getAllCategory()
        getPopularPosts()


//        refreshNearbyAdapter(getAllPosts())
//        refreshCheapAdapter(getAllPosts())


    }

    private fun callNotificationsActivity() {
        val intent = Intent(requireContext(), NotificationActivity::class.java)
        startActivity(intent)


    }


    private fun refreshCategoryAdapter(filters: ArrayList<Category>) {
        val adapter = CategoryAdapter(requireContext(), filters, this)
        binding.rvHomeCategory.adapter = adapter


    }

    private fun refreshPopularAdapter(filters: ArrayList<GetPost>) {
        val adapter = LargePostsAdapter(requireContext(), filters, this)
        binding.rvHomePopular.adapter = adapter
        binding.swipeRefresh.isRefreshing = false
    }


    private fun getAllCategory() {
        dataService!!.getAllCategory().enqueue(object : Callback<CategoryResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                Logger.d("Category", response.body().toString())
                response.body()?.data?.let { refreshCategoryAdapter(it) }
                binding.llMainHome.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE

            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                t.message?.let { Logger.d("Category", it) }
                //progressBar!!.visibility = View.GONE
            }
        })

    }

//    fun sortPosts(filters: ArrayList<GetPost>?) {
//        val feeds: ArrayList<GetPost> = ArrayList()
//        if (filters != null) {
//            for (i in 0 until filters.size) {
//                if (i <= 5) {
//                    feeds.add(filters[i])
//                }
//            }
//        }
//        refreshPopularAdapter(feeds)
//    }

    private fun getPopularPosts() {
        dataService?.getAllPosts()?.enqueue(object : Callback<GetPostResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<GetPostResponse>,
                response: Response<GetPostResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    Logger.d(TAG, "isSuccessful data: " + response.body()?.data.toString())
                    Logger.d(TAG, "isSuccessful  code: " + response.code())
                    response.body()?.data?.let { refreshPopularAdapter(it) }
//                binding.rvSellType.visibility = View.VISIBLE
//                binding.progressBar.visibility = View.GONE
                } else {
                    Logger.e(TAG, "error data: " + response.body()?.data.toString())
                    Logger.e(TAG, "error  code: " + response.code())
                    Logger.e(TAG, "error  errorBody: " + response.errorBody().toString())
                    Logger.e(TAG, "error  message: " + response.message().toString())
                    toast("onResponse else: " + response.message())

                }

            }

            override fun onFailure(call: Call<GetPostResponse>, t: Throwable) {
                t.message?.let { Logger.d(TAG, it) }
                toast("onFailure: " + t.message.toString())
                //progressBar!!.visibility = View.GONE
            }
        })

    }


    override fun onCategoryItemClickListener(id: Int, name: String) {
        val intent = Intent(requireContext(), ViewCategoryActivity::class.java)
        intent.putExtra("category_id", id)
        intent.putExtra("category_name", name)
        startActivity(intent)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.d(TAG, "onAttach")
    }

    override fun onResume() {
        super.onResume()
        Logger.d(TAG, "onResume")
    }

    override fun onStart() {
        super.onStart()
        Logger.d(TAG, "onStart")
    }


    // Get current location
    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLastLocation(): LatLng {

        if (checkPermissions()) {
            Logger.d(TAG, "CheckPermissions : true")
            if (isLocationEnabled()) {
                Logger.d(TAG, "IsLocationEnabled : true")
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        currentLocation = LatLng(location.latitude, location.longitude)
                        Logger.d(TAG, "CurrentLocation : $currentLocation")

                    }
                }
            } else {
                Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
        return currentLocation
    }

    // Get current location, if shifted
    // from previous location
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = com.google.android.gms.location.LocationRequest()
        mLocationRequest.priority =
            com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        Looper.myLooper()?.let {
            mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                it
            )
        }
    }

    // If current location could not be located, use last location
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location? = locationResult.lastLocation
            if (mLastLocation != null) {
                currentLocation = LatLng(mLastLocation.latitude, mLastLocation.longitude)
            }
        }
    }

    // function to check if GPS is on
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // Check if location permissions are
    // granted to the application
    private fun checkPermissions(): Boolean {
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

    // Request permissions if not granted before
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            pERMISSION_ID
        )
    }

    // What must happen when permission is granted
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == pERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    override fun onLargePostItemClickListener(id: Int, latitude: String, longitude: String) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra("POST_ID", id)
        intent.putExtra("POST_LATITUDE", latitude)
        intent.putExtra("POST_LONGITUDE", longitude)
        startActivity(intent)
    }

}