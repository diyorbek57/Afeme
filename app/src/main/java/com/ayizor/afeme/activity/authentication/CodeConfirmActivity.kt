package com.ayizor.afeme.activity.authentication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.ayizor.afeme.R
import com.ayizor.afeme.activity.BaseActivity
import com.ayizor.afeme.databinding.ActivityCodeConfirmBinding
import com.ayizor.afeme.utils.Logger
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class CodeConfirmActivity : AppCompatActivity() {

    lateinit var binding: ActivityCodeConfirmBinding
    val TAG: String = CodeConfirmActivity::class.java.simpleName
    lateinit var user_phone_number: String
    lateinit var user_account_type: String
    lateinit var user_confirmation_code: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodeConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getEnteredDatas()
        inits()
    }

    @SuppressLint("SetTextI18n")
    private fun inits() {
        binding.tvCodeConfirmInfo.text =
            getString(R.string.enter_the_confirmation_code_we_send_to) + " " + user_phone_number
        sendNumberForCodeConfirmation(user_phone_number)
        binding.tvResendCode.setOnClickListener {
            reSendVerificationCode(user_phone_number)
        }
        binding.btnNextCodeConfirmation.setOnClickListener {


            if (binding.pinView.text?.isNotEmpty() == true) {
                if (binding.pinView.text!!.length == 6) {
                    // progressBar.setVisibility(View.VISIBLE)
                    user_confirmation_code = binding.pinView.text.toString()
                    confirmationCode(user_confirmation_code)
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
    private fun reSendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this@CodeConfirmActivity)
            .setForceResendingToken(resendToken)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private fun sendNumberForCodeConfirmation(phoneNumber: String) {
        PhoneAuthProvider.verifyPhoneNumber(
            PhoneAuthOptions
                .newBuilder(FirebaseAuth.getInstance())
                .setActivity(this@CodeConfirmActivity)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callbacks)
                .build());
    }

    private val callbacks: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(@NonNull s: String, @NonNull forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                user_confirmation_code = s
                resendToken = forceResendingToken
                Logger.e(TAG,user_confirmation_code)
                Logger.e(TAG,s)
            }

            override fun onVerificationCompleted(@NonNull phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                Logger.e(TAG, code.toString())
                if (code != null) {
                    //save confirmation code there if required
                    binding.pinView.setText(code)
                    confirmationCode(code)
                }
            }

            override fun onVerificationFailed(@NonNull e: FirebaseException) {
                Logger.d(TAG, e.message.toString())
                Toast.makeText(
                    this@CodeConfirmActivity,
                    getString(R.string.registration_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        val mAuth = FirebaseAuth.getInstance()
        Logger.e(TAG,credential.smsCode.toString())
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    //  val code: String = binding.etConfirmationCode.editText?.text.toString()
                    val intent = Intent(this@CodeConfirmActivity, OtherInformationActivity::class.java)
                    intent.putExtra("user_phone_number", user_phone_number)
                    intent.putExtra("user_account_type", user_account_type)
                    intent.putExtra("user_confirmation_code", user_confirmation_code)
                    startActivity(intent)
                    finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Logger.e(TAG,task.exception.toString())
                        Toast.makeText(
                            this@CodeConfirmActivity,
                            task.exception.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        //progressBar.setVisibility(View.GONE)
                    }
                }
            }
    }

    private fun confirmationCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(code,user_confirmation_code)
        Logger.e(TAG,user_confirmation_code)
        Logger.e(TAG,code)
        signInWithPhoneAuthCredential(credential)
    }





    private fun getEnteredDatas() {
        user_account_type = intent.getStringExtra("user_account_type").toString()
        user_phone_number = intent.getStringExtra("user_phone_number").toString()
    }
}