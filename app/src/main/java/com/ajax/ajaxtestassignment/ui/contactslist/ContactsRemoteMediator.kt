package com.ajax.ajaxtestassignment.ui.contactslist

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ajax.ajaxtestassignment.domain.interactors.ContactsInteractor
import com.ajax.ajaxtestassignment.domain.model.Contact

@OptIn(ExperimentalPagingApi::class)
class ContactsRemoteMediator(
    private val contactsInteractor: ContactsInteractor
) : RemoteMediator<Int, Contact>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Contact>
    ): MediatorResult {
        return try {
//            val loadKey = when (loadType) {
//                LoadType.REFRESH -> null
//                LoadType.PREPEND -> return MediatorResult.Success(
//                    endOfPaginationReached = true
//                )
//                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull()
//
//                    if (lastItem == null) {
//                        return MediatorResult.Success(
//                            endOfPaginationReached = true
//                        )
//                    }
//
//                    lastItem.id
//                }
//            }

            //todo: check correct logic
            val contacts =
                contactsInteractor.loadContacts(state.pages.size + 1, state.config.pageSize)

            contactsInteractor.saveContacts(
                contacts = contacts,
                isRefresh = loadType == LoadType.REFRESH
            )

            // End of pagination has been reached if no users are returned from the
            // service
            MediatorResult.Success(
                endOfPaginationReached = contacts.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}