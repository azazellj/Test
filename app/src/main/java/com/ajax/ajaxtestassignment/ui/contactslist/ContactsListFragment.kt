package com.ajax.ajaxtestassignment.ui.contactslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajax.ajaxtestassignment.databinding.FragmentContactsListBinding
import com.ajax.ajaxtestassignment.di.GlobalFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

open class ContactsListFragment : Fragment() {

    private val viewModel: ContactsListViewModel by viewModels { GlobalFactory }

    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(
            requireActivity(),
            this::onContactClicked
        )
    }

    private fun onContactClicked(id: String) {
        findNavController()
            .navigate(ContactsListFragmentDirections.actionContactListToDetails(id))
    }

    private var binding: FragmentContactsListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Creates a vertical Layout Manager
        return FragmentContactsListBinding.inflate(layoutInflater, container, false)
            .apply {
                contactList.layoutManager = LinearLayoutManager(context)
                contactList.adapter = contactAdapter

                refreshBtn.setOnClickListener {
                    contactAdapter.refresh()
                }

                contactAdapter.loadStateFlow.onEach {
                    progressBar.isVisible = it.refresh == LoadState.Loading || it.append == LoadState.Loading
                }.launchIn(lifecycleScope)
            }
            .also {
                binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel
            .contactsList
            .onEach {
                contactAdapter.submitData(it)
            }
            .launchIn(lifecycleScope)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}