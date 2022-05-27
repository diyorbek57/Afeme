package com.ayizor.afeme.activity


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import android.widget.TextView.BufferType
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.DetailsViewPagerAdapter
import com.ayizor.afeme.databinding.ActivityDetailsBinding
import com.ayizor.afeme.databinding.ItemCallBottomSheetDetailsBinding
import com.ayizor.afeme.fragment.AreaAndLotFragment
import com.ayizor.afeme.fragment.InteriorFragment
import com.ayizor.afeme.utils.CustomSpannable
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout


class DetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var binding: ActivityDetailsBinding
    val TAG: String = DetailsActivity::class.java.simpleName
    lateinit var supportMapFragment: SupportMapFragment
    lateinit var adapter: DetailsViewPagerAdapter
    private val REQUEST_CALL = 1
     var phoneNumber: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
        setSupportActionBar(binding.toolbar)
    }

    private fun inits() {
        phoneNumber = "+998948461770"
        setupViewPager()
        setupFeaturesViewPager()
        //Toolbar back button
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        //Assign variable
        supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_map_details) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
        //Check location permission
        makeTextViewResizable(binding.tvDescriptionDetails, 3, "View More", true);
        binding.flCallDetails.setOnClickListener {
            showCallBottomSheet()

        }
    }

    private fun setupFeaturesViewPager() {
        adapter = DetailsViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(AreaAndLotFragment(), getString(R.string.area_amp_lot))
        adapter.addFragment(InteriorFragment(), getString(R.string.interior))
        binding.vpDetails.adapter = adapter
        binding.tlDetails.setupWithViewPager(binding.vpDetails)
        binding.tlDetails.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        //set margin between tabs
        for (i in 0 until binding.tlDetails.tabCount) {
            val tab = (binding.tlDetails.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as MarginLayoutParams
            p.setMargins(0, 0, 50, 0)
            tab.requestLayout()
        }

    }


    private fun setupViewPager() {
        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel("https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?ixlib=rb-1.2.1&raw_url=true&q=80&fm=jpg&crop=entropy&cs=tinysrgb&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2075"))
        imageList.add(SlideModel("https://images.unsplash.com/photo-1569420742472-06233f00cbf7?ixlib=rb-1.2.1&raw_url=true&q=80&fm=jpg&crop=entropy&cs=tinysrgb&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687"))
        imageList.add(SlideModel("https://images.unsplash.com/photo-1580587771525-78b9dba3b914?ixlib=rb-1.2.1&raw_url=true&q=80&fm=jpg&crop=entropy&cs=tinysrgb&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074"))
        binding.isDetails.setImageList(imageList, scaleType = ScaleTypes.CENTER_CROP)

        binding.isDetails.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                // Toast.makeText(this@DetailsActivity, imageList[position].imageUrl, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun makeTextViewResizable(
        tv: TextView,
        maxLine: Int,
        expandText: String,
        viewMore: Boolean
    ) {
        if (tv.tag == null) {
            tv.tag = tv.text
        }
        val vto: ViewTreeObserver = tv.getViewTreeObserver()
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val text: String
                val lineEndIndex: Int
                val obs: ViewTreeObserver = tv.getViewTreeObserver()
                obs.removeOnGlobalLayoutListener(this)
                if (maxLine == 0) {
                    lineEndIndex = tv.layout.getLineEnd(0)
                    text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1)
                        .toString() + " " + expandText
                } else if (maxLine > 0 && tv.lineCount >= maxLine) {
                    lineEndIndex = tv.layout.getLineEnd(maxLine - 1)
                    text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1)
                        .toString() + " " + expandText
                } else {
                    lineEndIndex = tv.layout.getLineEnd(tv.layout.lineCount - 1)
                    text = tv.text.subSequence(0, lineEndIndex).toString() + " " + expandText
                }
                tv.text = text
                tv.movementMethod = LinkMovementMethod.getInstance()
                tv.setText(
                    addClickablePartTextViewResizable(
                        SpannableString(tv.text.toString()), tv, lineEndIndex, expandText,
                        viewMore
                    ), BufferType.SPANNABLE
                )
            }
        })
    }

    private fun addClickablePartTextViewResizable(
        strSpanned: Spanned,
        tv: TextView,
        maxLine: Int,
        spanableText: String,
        viewMore: Boolean
    ): SpannableStringBuilder? {
        val str = strSpanned.toString()
        val ssb = SpannableStringBuilder(strSpanned)

        if (str.contains(spanableText)) {
            ssb.setSpan(object : CustomSpannable(false) {
                override fun onClick(widget: View) {
                    super.onClick(widget)
                    tv.layoutParams = tv.layoutParams
                    tv.setText(tv.tag.toString(), BufferType.SPANNABLE)
                    tv.invalidate()
                    if (viewMore) {
                        makeTextViewResizable(tv, -1, "View less", false)
                    } else {
                        makeTextViewResizable(tv, 3, "View more", true)
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length, 0)
        }
        return ssb
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latitude = 40.747457
        val longitude = 72.359775
        val postLocation = LatLng(latitude, longitude)
        googleMap.uiSettings.isZoomGesturesEnabled = false;
        googleMap.uiSettings.isScrollGesturesEnabled = false
        googleMap.uiSettings.isMapToolbarEnabled = true;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(postLocation, 16f))
        // create marker
        val marker: MarkerOptions =
            MarkerOptions().position(LatLng(latitude, longitude))
        // Changing marker icon
        marker.icon(bitmapDescriptorFromVector(this, R.drawable.ic_home_marker))
        // adding marker
        googleMap.addMarker(marker);
        googleMap.addCircle(
            CircleOptions().center(LatLng(latitude, longitude)).radius(100.0)
                .strokeColor(Color.parseColor("#2972FE")).fillColor(Color.parseColor("#6499FF"))
        )
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    fun callToPostOwner(number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }

    fun sendSmsToPostOwner(number: String) {
        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.data = Uri.parse("sms:$number")
        startActivity(sendIntent)
    }

    fun showCallBottomSheet() {
        val sheetDialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        val bottomSheetBinding: ItemCallBottomSheetDetailsBinding = ItemCallBottomSheetDetailsBinding.inflate(layoutInflater)
        sheetDialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.tvPhoneNumber.text = phoneNumber
        bottomSheetBinding.btnToCallBottomSheet.setOnClickListener {
            callToPostOwner(phoneNumber)
        }
        bottomSheetBinding.btnSendSmsBottomSheet.setOnClickListener {

            sendSmsToPostOwner(phoneNumber)
        }

        sheetDialog.show();
        sheetDialog.window?.attributes?.windowAnimations = R.style.DialogAnimaton;
    }


}