package com.ajax.ajaxtestassignment.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajax.ajaxtestassignment.domain.interactors.ContactsInteractor
import com.ajax.ajaxtestassignment.domain.model.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val contactsInteractor: ContactsInteractor,
) : ViewModel() {

    private val _contactFlow: MutableStateFlow<Contact?> = MutableStateFlow<Contact?>(null)
    val contactFlow: Flow<Contact?> = _contactFlow

    fun load(id: String) {
        viewModelScope.launch {
            val contact = contactsInteractor.loadContact(id)
            _contactFlow.value = contact
        }
    }
}
