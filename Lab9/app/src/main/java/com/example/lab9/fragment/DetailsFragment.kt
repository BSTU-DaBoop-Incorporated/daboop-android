package com.example.lab9.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lab9.R
import com.example.lab9.databinding.FragmentDetailsBinding
import com.example.lab9.viewModel.UserContactDetailsViewModel
import com.example.lab9.viewModel.UserContactListViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val userContactDetailsViewModel: UserContactDetailsViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.actionBar?.setDisplayHomeAsUpEnabled(true);
        binding.fragment = this

        userContactDetailsViewModel.userContact.observe(requireActivity()) {
            userContactDetailsViewModel.bindObservables()
        }
//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.actionBar?.setDisplayHomeAsUpEnabled(false);
        _binding = null
    }
}