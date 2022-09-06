package com.example.lab2

import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.test.espresso.remote.Converter
import com.example.lab2.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        // Obtain the ViewModel component.
        val userModel: AppViewModel by viewModels()
        // Assign the component to a property in the binding class.
        binding.viewModel = userModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureInputLoanSumFilters()
//        configureButtonTogglePayType()
//        configureButtonCalculate()
//        configureSelectLoanTerm()
//        configureSliderAndInputLoanSum()
    }

    private fun configureInputLoanSumFilters() {
        val minMaxLoanSumFilter: InputFilter = InputFilterMinMax(0f, 10000f)
        val digitsFloatInputFilter: InputFilter = DecimalDigitsInputFilter(5, 2)
        binding.inputLoanSum.filters = arrayOf(minMaxLoanSumFilter, digitsFloatInputFilter)
    }

    private fun configureSliderAndInputLoanSum() {
        binding.inputLoanSum.addTextChangedListener { text ->
            if (!areLoanSumInputAndSliderValuesEqual()) {
                binding.sliderLoanSum.value = text.toString().toFloat()
            }
        }
        binding.sliderLoanSum.addOnChangeListener { slider, value, fromUser ->
            if (!areLoanSumInputAndSliderValuesEqual()) {
                binding.inputLoanSum.setText(value.toString())
            }
        }
    }

    private fun areLoanSumInputAndSliderValuesEqual(): Boolean {
        return binding.inputLoanSum.text.toString().toFloatOrNull() == binding.sliderLoanSum.value
    }

    private fun configureButtonCalculate() {
        binding.buttonCalculate.setOnClickListener {
        }
    }

    private fun configureButtonTogglePayType() {
        binding.buttonTogglePayType.addOnButtonCheckedListener { group, checkedId, isChecked ->
        }
    }

    private fun configureSelectLoanTerm() {
        val itemsMap = mapOf("10 лет" to 10, "15 лет" to 15, "20 лет" to 20)

        binding.selectLoanTerm.setSimpleItems(itemsMap.keys.toTypedArray())
        binding.selectLoanTerm.addTextChangedListener { editable ->
            Log.d(
                "select_loanTerm",
                itemsMap[editable.toString()].toString()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}