package com.ayizor.afeme.viewmodel

import android.os.Parcelable
import androidx.lifecycle.ViewModel

class HomeFragmentViewModel: ViewModel() {
    var popularListState:Parcelable?=null
    var nearbyListState:Parcelable?=null
    var cheapListState:Parcelable?=null
    var categoryListState:Parcelable?=null
}