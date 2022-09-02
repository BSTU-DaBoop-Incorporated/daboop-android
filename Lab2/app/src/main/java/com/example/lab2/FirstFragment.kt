package com.example.lab2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
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
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonTogglePayType.addOnButtonCheckedListener{group, checkedId, isChecked -> 
        }
        binding.buttonCalculate.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val itemsMap = mapOf("10 лет" to 10, "15 лет" to 15, "20 лет" to 20)
       
        binding.selectLoanTerm.setSimpleItems(itemsMap.keys.toTypedArray());
        binding.selectLoanTerm.addTextChangedListener { editable -> Log.d("select_loanTerm",
            itemsMap[editable.toString()].toString()
        ) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}