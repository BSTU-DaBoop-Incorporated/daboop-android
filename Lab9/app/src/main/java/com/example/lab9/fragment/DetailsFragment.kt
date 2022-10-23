package com.example.lab9.fragment

import android.os.Bundle
import android.provider.Contacts.Intents.UI
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lab9.R
import com.example.lab9.databinding.FragmentDetailsBinding
import com.example.lab9.model.UserContact
import com.example.lab9.viewModel.UserContactDetailsViewModel
import com.example.lab9.viewModel.UserContactListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val userContactDetailsViewModel: UserContactDetailsViewModel by activityViewModels()


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
        binding.fragment = this
        binding.viewModel = userContactDetailsViewModel

        userContactDetailsViewModel.bindObservables()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    fun onSaveClick(view: View) {
        
        val newUserContact = UserContact(
            userContactDetailsViewModel.userContact.value?.id,
            userContactDetailsViewModel.name.get(),
            userContactDetailsViewModel.phone.get(),
            userContactDetailsViewModel.email.get()
        )

        binding.btnSave.isEnabled = false
        val coroutine = CoroutineScope(Dispatchers.IO).launch {
            userContactDetailsViewModel.upsert(newUserContact)
        }
        
        coroutine.invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                binding.btnSave.isEnabled = true
                if (it == null) {
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                }
            }
        }
        
    }
}
