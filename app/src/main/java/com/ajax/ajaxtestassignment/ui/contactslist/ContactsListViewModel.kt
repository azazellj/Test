package com.ajax.ajaxtestassignment.ui.contactslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ajax.ajaxtestassignment.domain.interactors.ContactsInteractor

class ContactsListViewModel(
    private val contactsInteractor: ContactsInteractor,
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val contactsList = Pager(
        config = PagingConfig(pageSize = 30),
        remoteMediator = ContactsRemoteMediator(contactsInteractor)
    ) {
        contactsInteractor.contactsListFlow
    }
        .flow
        .cachedIn(viewModelScope)
}
