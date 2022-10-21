package com.example.lab9.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.lab9.R
import com.example.lab9.adapter.UserContactListAdapter
import com.example.lab9.databinding.FragmentListBinding
import com.example.lab9.model.UserContact
import com.example.lab9.viewModel.UserContactDetailsViewModel
import com.example.lab9.viewModel.UserContactListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.random.Random


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val userContactListViewModel: UserContactListViewModel by activityViewModels()
    private val userContactDetailsViewModel: UserContactDetailsViewModel by activityViewModels()
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
            val adapter = UserContactListAdapter(it, userContactListViewModel.userContacts)
            recyclerView.adapter = adapter
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_add -> {
                        CoroutineScope(context = Dispatchers.IO).async {
                            addContact()
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
    


    suspend fun addContact() {
        val userContact = UserContact(null, "test${Random.nextInt(100)}", "test${Random.nextInt(100)}", "test${Random.nextInt(100)}")
        return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            userContactListViewModel.insert(userContact)
        }
    }
    fun editContact(Id: Int) {
        userContactDetailsViewModel.userContact.value = userContactListViewModel.userContacts
            .value?.find { it.id == Id }
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }
    
    suspend fun deleteContact (Id: Int) {
       val model = userContactListViewModel.userContacts
            .value?.find { it.id == Id }
        
        if(model != null) {
            return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                userContactListViewModel.delete(model)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}