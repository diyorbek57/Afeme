package com.ayizor.afeme.activity.authentication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ayizor.afeme.R
import com.ayizor.afeme.activity.BaseActivity
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivityCodeConfirmBinding
import com.ayizor.afeme.model.User
import com.ayizor.afeme.model.response.MainResponse
import com.ayizor.afeme.utils.Logger
import com.ayizor.afeme.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CodeConfirmActivity : BaseActivity() {

    lateinit var binding: ActivityCodeConfirmBinding
    val TAG: String = CodeConfirmActivity::class.java.simpleName
    lateinit var user_phone_number: String
    lateinit var user_account_type: String
    lateinit var user_latitude: String
    lateinit var user_longitude: String
    lateinit var user_passport_number: String
    lateinit var user_first_name: String
    lateinit var user_last_name: String
    lateinit var user_confirmation_code: String
    var user_device_type: String = "Android"
    var user_device_id: String = Utils.getDeviceID(this)
    var dataService: ApiInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodeConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getEnteredDatas()
        inits()
    }

    @SuppressLint("SetTextI18n")
    private fun inits() {
        dataService = Client.getClient()?.create(ApiInterface::class.java)
        binding.tvCodeConfirmInfo.text =
            getString(R.string.enter_the_confirmation_code_we_send_to) + " " + user_phone_number

        binding.tvResendCode.setOnClickListener {

        }
        binding.btnNextCodeConfirmation.setOnClickListener {


            if (binding.pinView.text?.isNotEmpty() == true) {
                if (binding.pinView.text!!.length == 6) {
                    // progressBar.setVisibility(View.VISIBLE)

                    val user = User(
                        null,
                        user_first_name,
                        user_last_name,
                        null,
                        user_phone_number,
                        user_phone_number,
                        user_passport_number,
                        user_account_type,
                        user_device_id,
                        user_device_type,
                        null,
                        user_longitude,
                        user_latitude,
                        null,
                        null,
                        null,
                        null
                    )

                    createUser(user)
                }
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.enter_the_code_sent_via_sms),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createUser(user: User) {
        dataService?.register(user)
            ?.enqueue(object : Callback<MainResponse> {
                override fun onResponse(call: Call<MainResponse>, response: Response<MainResponse>) {


                }

                override fun onFailure(call: Call<MainResponse>, t: Throwable) {

                }

            })
    }


    private fun getEnteredDatas() {
        user_account_type = intent.getStringExtra("user_account_type").toString()
        user_phone_number = intent.getStringExtra("user_phone_number").toString()
        user_latitude = intent.getStringExtra("user_latitude").toString()
        user_longitude = intent.getStringExtra("user_longitude").toString()
        user_passport_number = intent.getStringExtra("user_passport_number").toString()
        user_first_name = intent.getStringExtra("user_first_name").toString()
        user_last_name = intent.getStringExtra("user_last_name").toString()

    }
}