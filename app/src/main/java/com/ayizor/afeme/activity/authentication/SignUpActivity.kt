package com.ayizor.afeme.activity.authentication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.ayizor.afeme.activity.BaseActivity
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivitySignUpBinding
import com.ayizor.afeme.model.response.MainResponse
import com.ayizor.afeme.utils.Logger
import com.ayizor.afeme.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {
    lateinit var binding: ActivitySignUpBinding
    val TAG: String = SignUpActivity::class.java.simpleName
    var user_account_type: String ="Personal"
    lateinit var user_latitude: String
    lateinit var user_longitude: String
    lateinit var user_passport_number: String
    lateinit var user_first_name: String
    lateinit var user_last_name: String
    lateinit var user_phone_number: String
    var firstNameDone: Boolean = false
    var dataService: ApiInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
        getEnteredDatas()
    }

    private fun inits() {
        val user_device_id: String = Utils.getDeviceID(this)
        Logger.e(TAG, user_device_id)
        dataService = Client.getClient(this)?.create(ApiInterface::class.java)
        loadSpinner()
        loadCodePicker()
        phoneNumberValid()

        binding.btnSigup.isEnabled = false

        binding.btnSigup.setOnClickListener {
            if (Utils.validEditText(
                    binding.tilNumberSignup.editText,
                    "Please enter your Phone number"
                )
            ) {
                user_phone_number = binding.tilNumberSignup.editText?.text.toString()
                sendPhoneNumber(user_phone_number)

            }
        }
    }

    private fun sendPhoneNumber(userPhoneNumber: String) {
        dataService?.sendPhoneNumber(userPhoneNumber)
            ?.enqueue(object : Callback<MainResponse> {
                override fun onResponse(
                    call: Call<MainResponse>,
                    response: Response<MainResponse>
                ) {
                    Logger.d(TAG, response.body()?.message.toString())
                    Logger.d(TAG, response.body()?.status.toString())
                    Logger.d(TAG, response.body()?.data.toString())
                    Logger.d(TAG, response.code().toString())
                    Logger.d(TAG, response.body()?.data.toString())
//                    if (response.body()?.status == true) {
                        callConfirmCodeActivity()
                   // } else {
//                        showTopSnackBar(
//                            binding.mainLayoutSignup,
//                            response.body()?.message.toString()
//                        )
                  //  }

                }

                override fun onFailure(call: Call<MainResponse>, t: Throwable) {

                }

            })
    }

    fun callConfirmCodeActivity() {
        val i = Intent(this, CodeConfirmActivity::class.java)
        i.putExtra("user_phone_number", user_phone_number)
        i.putExtra("user_account_type", user_account_type)
        i.putExtra("user_longitude", user_longitude)
        i.putExtra("user_latitude", user_latitude)
        i.putExtra("user_first_name", user_first_name)
        i.putExtra("user_last_name", user_last_name)
        i.putExtra("user_passport_number", user_passport_number)
        Logger.d("User type", user_account_type)
        startActivity(i)
    }

    @SuppressLint("SetTextI18n")
    private fun loadCodePicker() {
        binding.ccpSignup.ccpDialogShowFlag = false

        binding.ccpSignup.setOnCountryChangeListener {
            val code = binding.ccpSignup.selectedCountryCode.toString()
            binding.etNumberSignup.setText("+$code")
        }
    }


    private fun loadSpinner() {

        binding.spinnerSignup.selectItemByIndex(0).toString()

        binding.spinnerSignup.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newText ->
            user_account_type = newText
        }


    }

    private fun phoneNumberValid() {
        binding.tilNumberSignup.editText?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.isEmpty() && s.length > 6) {
                    //set background and text color to button
                    firstNameDone = true
                    if (firstNameDone) {
                        binding.btnSigup.isEnabled = true
                    }
                } else {
                    firstNameDone = false
                    binding.btnSigup.isEnabled = false

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

    private fun getEnteredDatas() {
        user_latitude = intent.getStringExtra("user_latitude").toString()
        user_longitude = intent.getStringExtra("user_longitude").toString()
        user_passport_number = intent.getStringExtra("user_passport_number").toString()
        user_first_name = intent.getStringExtra("user_first_name").toString()
        user_last_name = intent.getStringExtra("user_last_name").toString()
    }
}

