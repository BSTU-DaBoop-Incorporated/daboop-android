package com.example.lab9.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lab9.R
import com.example.lab9.databinding.FragmentDetailsBinding
import com.example.lab9.model.UserContact
import com.example.lab9.viewModel.UserContactDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val userContactDetailsViewModel: UserContactDetailsViewModel by activityViewModels()


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }
    
    fun imageClick(view: View) {
        if(userContactDetailsViewModel.isEditMode.get() == true) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_OPEN_DOCUMENT
            startActivityForResult(Intent.createChooser(intent, "select a picture"),
                1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val selectedImageUri: Uri? = intent?.data
                selectedImageUri?.let {
                    requireContext().contentResolver.takePersistableUriPermission(it, Intent
                        .FLAG_GRANT_READ_URI_PERMISSION)
                    userContactDetailsViewModel.imageUri.set(it)
                }
                

            }
        }
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
            userContactDetailsViewModel.email.get(),
            userContactDetailsViewModel.imageUri.get().toString()
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
