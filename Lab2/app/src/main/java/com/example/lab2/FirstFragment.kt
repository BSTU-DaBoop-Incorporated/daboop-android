package com.example.lab2

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
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
        val viewModel: AppViewModel by activityViewModels()
        binding.viewModel = viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureInputLoanSumFilters()
        configureSelectDwellingType()
        configureSelectLoanTerm()
        configureButtonTogglePayType()
        configureCalculateButton()
    }

    private fun configureCalculateButton() {
        binding.buttonCalculate.setOnClickListener {
            onCalculateClick()
        }
    }

    private fun configureSelectDwellingType() {
        binding.selectDwellingType.setText(reverseDwellingTypeMap[binding.viewModel?.dwellingType], false)
        binding.selectDwellingType.setSimpleItems(dwellingTypeMap.keys.toTypedArray())
        binding.selectDwellingType.freezesText = false
//        binding.selectDwellingType.filters = arrayOf()
        binding.selectDwellingType.addTextChangedListener {
            binding.viewModel?.dwellingType = dwellingTypeMap[it.toString()] ?: DwellingType.New
        }
    }

    private fun configureInputLoanSumFilters() {
        val minMaxLoanSumFilter: InputFilter = InputFilterMinMax(0f, 10000f)
        val digitsFloatInputFilter: InputFilter = DecimalDigitsInputFilter(5, 2)
        binding.inputLoanSum.filters = arrayOf(minMaxLoanSumFilter, digitsFloatInputFilter)
    }

    @SuppressLint("ResourceType")
    private fun configureButtonTogglePayType() {
        binding.buttonDifPayType.tag = PayType.Differential
        binding.buttonAnPayType.tag = PayType.Annual
        
        binding.buttonTogglePayType.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if(!isChecked)
                return@addOnButtonCheckedListener
            group.children.forEach { button ->
                if(button !is Button) return@forEach
                if (button.id == checkedId) {
                    button.setBackgroundColor(resources.getColor(R.color.purple_500))
                    binding.viewModel?.payType = button.tag as PayType
                } else {
                    button.setBackgroundColor(Color.parseColor("#FFA500"))
                }
            }
            
            
        }
    }

    private fun configureSelectLoanTerm() {
        val itemsMap = mapOf("10 лет" to 10, "15 лет" to 15, "20 лет" to 20)
        val itemsMapReversed = itemsMap.entries.associate { (k, v) -> v to k }
        binding.selectLoanTerm.setText(itemsMapReversed[binding.viewModel?.loanTerm])
        binding.selectLoanTerm.setSimpleItems(itemsMap.keys.toTypedArray())
        binding.selectLoanTerm.freezesText = false
        binding.selectLoanTerm.addTextChangedListener {
            binding.viewModel?.loanTerm = itemsMap[it.toString()] ?: 10
        }
    }
    
    fun onCalculateClick() {
        binding.viewModel?.calculate()
        view?.let { Navigation.findNavController(it).navigate(R.id.action_FirstFragment_to_SecondFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}