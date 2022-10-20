package com.example.lab9.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lab9.R
import com.example.lab9.UserContactApplication
import com.example.lab9.adapter.UserContactListAdapter
import com.example.lab9.databinding.FragmentListBinding
import com.example.lab9.viewModel.UserContactListViewModel
import com.example.lab9.viewModel.UserContactListViewModelFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    
    private val userContactListViewModel: UserContactListViewModel by viewModels {
    UserContactListViewModelFactory((activity?.application as UserContactApplication).database.userContactDao())
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.contactsRecyclerView
        val adapter = UserContactListAdapter(userContactListViewModel.userContacts)
        recyclerView.adapter = adapter
        
//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}