package com.example.lab12.fragment.currency

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab12.databinding.FragmentCurrencyListBinding
import com.example.lab12.tools.retrofit.RetrofitClient
import com.example.lab12.tools.retrofit.request.CurrencyListRequestResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyListFragment : Fragment() {

    lateinit var binding: FragmentCurrencyListBinding
    var listRequest: Call<CurrencyListRequestResult>? = null

    companion object {
        fun newInstance() = CurrencyListFragment()
    }

    private lateinit var viewModel: CurrencyListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CurrencyListViewModel::class.java]
        binding.currenciesRecycler.adapter = CurrencyListAdapter(viewLifecycleOwner, viewModel
            .currencies)
        binding.currenciesRecycler.layoutManager = LinearLayoutManager(context)

        listRequest = RetrofitClient.service.getCurrencyList()
        listRequest!!.enqueue(object :
            Callback<CurrencyListRequestResult> {
            override fun onFailure(call: Call<CurrencyListRequestResult>, t: Throwable) {
                Toast.makeText(context, "Fetch error", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<CurrencyListRequestResult>,
                response: Response<CurrencyListRequestResult>,
            ) {
                if (response.isSuccessful) {
                    val currencies = response.body()!!.data
                    
                    viewModel.currencies.value = currencies.map { currency ->
                        currency.priceUsd = currency.priceUsd?.let { "%.2f".format(it.toDouble())
                        } + "$"
                        currency
                    }
                }
                binding.progressBar.visibility = View.GONE

            }

        })
        
        binding.progressBar.visibility = View.VISIBLE
    }


    override fun onDestroy() {
        super.onDestroy()
        listRequest?.cancel()
    }


}