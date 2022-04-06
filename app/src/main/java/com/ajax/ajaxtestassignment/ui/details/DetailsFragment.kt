package com.ajax.ajaxtestassignment.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ajax.ajaxtestassignment.databinding.FragmentDetailsBinding
import com.ajax.ajaxtestassignment.di.GlobalFactory
import com.ajax.ajaxtestassignment.domain.model.Contact
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


open class DetailsFragment : Fragment() {
    var binding: FragmentDetailsBinding? = null

    private val detailsViewModel by viewModels<DetailsViewModel> { GlobalFactory }

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDetailsBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
            }
            .root
    }

    // todo: editing can be implemented with dialogs/other screens
    // cancel editing - just not save changes to DB, save on button click

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsViewModel.load(args.id)

        detailsViewModel.contactFlow
            .onEach { fillData(it) }
            .launchIn(lifecycleScope)
    }

    private fun fillData(contact: Contact?) {
        binding?.email?.text = contact?.email
        binding?.fullName?.text = contact?.fullName
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}