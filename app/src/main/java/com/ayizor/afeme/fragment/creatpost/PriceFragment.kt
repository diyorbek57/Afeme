package com.ayizor.afeme.fragment.creatpost

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.api.converter.ConverterApiInterface
import com.ayizor.afeme.api.converter.ConverterClient
import com.ayizor.afeme.databinding.FragmentPriceBinding
import com.ayizor.afeme.manager.PostPrefsManager
import com.ayizor.afeme.model.convertermodels.Currency
import com.ayizor.afeme.utils.Logger
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PriceFragment : Fragment() {

    lateinit var binding: FragmentPriceBinding
    val TAG: String = PriceFragment::class.java.simpleName
    var fragmentNumber = 8
    lateinit var post_currency_type: String
    var dataService: ConverterApiInterface? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentPriceBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        activity?.findViewById<ProgressBar>(R.id.progress_bar_main_creat_post)?.progress =
            fragmentNumber
        loadSpinner()
    }

    @SuppressLint("SetTextI18n")
    private fun loadSpinner() {

        dataService = ConverterClient.getClient()?.create(ConverterApiInterface::class.java)
        setupPriceEditText()
        binding.btnNext.setOnClickListener {
            if (validPrice()) {
                PostPrefsManager(requireContext()).storePrice(binding.etPrice.editText?.text.toString())
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    .replace(R.id.fragment_container_creat_post, DescriptionFragment())
                    .addToBackStack(PriceFragment::class.java.name).commit()
            }
        }
        binding.spinnerSignup.selectItemByIndex(0)

        binding.spinnerSignup.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newText ->
            post_currency_type = newText
            if (newText.contentEquals("USD")) {
                if (!binding.etPrice.editText?.text.toString().isEmpty()) {
                    binding.tvCurrency.text = binding.etPrice.editText?.text.toString() + " UZS "
                } else {
                    binding.tvCurrency.text = "0 UZS "
                }
            } else {
                if (!binding.etPrice.editText?.text.toString().isEmpty()) {
                    binding.tvCurrency.text = binding.etPrice.editText?.text.toString() + " USD "
                } else {
                    binding.tvCurrency.text = "0 USD "
                }
            }


        }


    }

    private fun getConvertedCurrency() {
        val jsonArray = JSONArray()
        dataService!!.getCurrency().enqueue(object : Callback<Currency> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<Currency>,
                response: Response<Currency>
            ) {
                Logger.d(TAG, response.body().toString())
//                response.body()?.data?.let { refreshCategoryAdapter(it) }
//                binding.rvBuildingType.visibility = View.VISIBLE
//                binding.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<Currency>, t: Throwable) {
                t.message?.let { Logger.d(TAG, it) }
                //progressBar!!.visibility = View.GONE
            }
        })

    }

    private fun setupPriceEditText() {
        binding.etPrice.editText?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.isEmpty()) {
                    getConvertedCurrency()
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

    private fun validPrice(): Boolean {
        val nomer: String = binding.etPrice.editText?.text.toString()
        return if (nomer.isEmpty()) {
            binding.etPrice.error = "Please enter Price"
            false
        } else {
            binding.etPrice.error = null
            true
        }
    }
}