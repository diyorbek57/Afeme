package com.ayizor.afeme.activity.authentication

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.ayizor.afeme.R
import com.ayizor.afeme.activity.BaseActivity
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivityOtherInformationBinding
import com.ayizor.afeme.model.User
import com.ayizor.afeme.utils.Utils
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.sucho.placepicker.AddressData
import com.sucho.placepicker.Constants
import com.sucho.placepicker.Constants.GOOGLE_API_KEY
import com.sucho.placepicker.PlacePicker
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class OtherInformationActivity : BaseActivity() {
    lateinit var binding: ActivityOtherInformationBinding
    private val pERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    var geocoder: Geocoder? = null
    // Current location is set to Uzbekistan, this will be of no use
    var currentLocation: LatLng = LatLng(20.5, 78.9)
    lateinit var addresses: List<Address>;
    lateinit var user_latitude: String
    lateinit var user_longitude: String
    lateinit var user_region: String
    lateinit var user_passport_number: String
    lateinit var user_first_name: String
    lateinit var user_last_name: String
    var firstnameDone: Boolean = false
    var lastnameDone: Boolean = false
    var passportDone: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inits()
    }

    private fun inits() {
        passportNumberValid()
        firstNameValid()
        lastNameValid()

        // Initializing fused location client
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.tvSelectFromMap.setOnClickListener {
            val intent = PlacePicker.IntentBuilder().setLatLong(
                41.301140,
                69.262558
            )  // Initial Latitude and Longitude the Map will load into
                .showLatLong(false)  // Show Coordinates in the Activity
                .setMapZoom(12.0f)  // Map Zoom Level. Default: 14.0
                .setAddressRequired(true) // Set If return only Coordinates if cannot fetch Address for the coordinates. Default: True
                .hideMarkerShadow(true) // Hides the shadow under the map marker. Default: False
                .setFabColor(R.color.bright_blue)
                .setPlaceSearchBar(
                    false,
                    GOOGLE_API_KEY
                ) //Activate GooglePlace Search Bar. Default is false/not activated. SearchBar is a chargeable feature by Google
                .onlyCoordinates(true)  //Get only Coordinates from Place Picker
                .hideLocationButton(true)   //Hide Location Button (Default: false)
                .disableMarkerAnimation(false)   //Disable Marker Animation (Default: false)
                .build(this)
            startActivityForResult(intent, Constants.PLACE_PICKER_REQUEST)
        }
        binding.ivCurrentLocation.setOnClickListener {
            getLastLocation()
        }
        binding.btnNextOtherInfo.setOnClickListener {
            user_passport_number = binding.etPassportNumberInformations.editText?.text.toString()
            user_first_name = binding.etNameInformations.editText?.text.toString()
            user_last_name = binding.etLastNameInformations.editText?.text.toString()
            if (checkPassportNumber(user_passport_number))
                if (!user_latitude.isNullOrEmpty() && !user_longitude.isNullOrEmpty() && !user_region.isNullOrEmpty()) {
                    val i = Intent(this, CodeConfirmActivity::class.java)
                    i.putExtra("user_latitude", user_latitude)
                    i.putExtra("user_longitude", user_longitude)
                    i.putExtra("user_first_name", user_first_name)
                    i.putExtra("user_last_name", user_last_name)
                    i.putExtra("user_passport_number", user_passport_number)
                }
        }

    }


    // Get current location
    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        currentLocation = LatLng(location.latitude, location.longitude)
                        getCoordinateName(location.latitude, location.longitude)
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("SetTextI18n")
    fun getCoordinateName(latitude: Double, longitude: Double) {
        geocoder = Geocoder(this, Locale.getDefault());
        addresses = geocoder!!.getFromLocation(latitude, longitude, 1);
        val address =
            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        val city = addresses[0].locality
        val state = addresses[0].adminArea
        val country = addresses[0].countryName
        val postalCode = addresses[0].postalCode
        val knownName =
            addresses[0].featureName // Only if available else return NULL
        binding.tvSelectFromMap.text = "$state, $city"
        user_latitude = latitude.toString()
        user_longitude = longitude.toString()
        user_region = "$state, $city"
    }

    // Get current location, if shifted
    // from previous location
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // Check if location permissions are
    // granted to the application
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
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
            this,
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val addressData = data?.getParcelableExtra<AddressData>(Constants.ADDRESS_INTENT)
                if (addressData != null) {
                    getCoordinateName(addressData.latitude, addressData.longitude)
                    user_latitude = addressData.latitude.toString()
                    user_longitude = addressData.longitude.toString()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    private fun firstNameValid() {
        binding.etNameInformations.editText?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.isEmpty()) {
                    //set background and text color to button
                    firstnameDone = true
                    if (firstnameDone && passportDone && lastnameDone) {
                        binding.btnNextOtherInfo.isEnabled = true
                    }
                } else {
                    firstnameDone = false

                    binding.btnNextOtherInfo.isEnabled = false

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(
                s: Editable
            ) {
            }
        })
    }

    private fun lastNameValid() {
        binding.etNameInformations.editText?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.isEmpty()) {
                    //set background and text color to button
                    lastnameDone = true
                    if (lastnameDone && passportDone && firstnameDone) {
                        binding.btnNextOtherInfo.isEnabled = true
                    }
                } else {
                    lastnameDone = false
                    binding.btnNextOtherInfo.isEnabled = false

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(
                s: Editable
            ) {
            }
        })
    }

    private fun passportNumberValid() {
        binding.etNameInformations.editText?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.isEmpty() && s.length == 9) {
                    //set background and text color to button
                    passportDone = true
                    if (passportDone && firstnameDone && lastnameDone) {
                        binding.btnNextOtherInfo.isEnabled = true
                    }
                } else {
                    passportDone = false
                    binding.btnNextOtherInfo.isEnabled = false

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(
                s: Editable
            ) {
            }
        })
    }


    fun checkPassportNumber(number: String): Boolean {
        val pattern: Pattern = Pattern.compile("[A-Z]{2}[0-9]{7}");
        val matcher: Matcher = pattern.matcher(number);
        return matcher.find()
        // match
    }
}