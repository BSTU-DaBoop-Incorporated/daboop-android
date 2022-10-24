package com.example.lab9.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab9.R
import com.example.lab9.UserContactApplication
import com.example.lab9.adapter.UserContactListAdapter
import com.example.lab9.databinding.FragmentListBinding
import com.example.lab9.model.UserContact
import com.example.lab9.viewModel.UserContactDetailsViewModel
import com.example.lab9.viewModel.UserContactListViewModel
import com.example.lab9.viewModel.UserContactViewModelFactory
import kotlinx.coroutines.*
import kotlin.random.Random



class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val userContactListViewModel: UserContactListViewModel by activityViewModels {
        UserContactViewModelFactory((requireActivity().application as UserContactApplication).database
            .userContactDao())
    }

    private val userContactDetailsViewModel: UserContactDetailsViewModel by activityViewModels {
        UserContactViewModelFactory((requireActivity().application as UserContactApplication).database.userContactDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.contactsRecyclerView
        binding.fragment = this
        context?.let {
            recyclerView.layoutManager = LinearLayoutManager(context);
            val adapter = UserContactListAdapter(viewLifecycleOwner, userContactListViewModel
                .userContacts, ::showPopupMenu) 
            recyclerView.adapter = adapter
        }
        
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_add -> {
                        goToDetails(null, true)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showPopupMenu(contact: UserContact, view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menu.add("Details").setOnMenuItemClickListener {
            goToDetails(contact, isEditMode = false)
            true
        }
        popupMenu.menu.add("Edit").setOnMenuItemClickListener {
            goToDetails(contact, isEditMode = true)
            true
        }
        popupMenu.menu.add("Dial").setOnMenuItemClickListener { 
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + contact.phone)
            startActivity(intent)            
            true
        }
        popupMenu.menu.add("Delete").setOnMenuItemClickListener {
            deleteContact(contact)
            true
        }
        popupMenu.show()
    }
    
    private fun goToDetails(contact: UserContact?, isEditMode: Boolean = false) {
        userContactDetailsViewModel.userContact.value = contact
        userContactDetailsViewModel.isEditMode.set(isEditMode)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }
    
    private fun deleteContact (contact: UserContact) {
        CoroutineScope(context = Dispatchers.IO).launch {
            userContactListViewModel.delete(contact)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

