package com.ajax.ajaxtestassignment.domain.mappers

import com.ajax.ajaxtestassignment.api.contacts.ApiContact
import com.ajax.ajaxtestassignment.db.contacts.DbContact
import com.ajax.ajaxtestassignment.domain.model.Contact
import java.util.*

fun ApiContact.toDomain(): Contact {
    return Contact(
        id = (name?.firstName.orEmpty() + name?.lastName.orEmpty()).ifEmpty {
            UUID.randomUUID().toString()
        },
        firstName = name?.firstName,
        lastName = name?.lastName,
        email = email,
        picture = picture?.thumbnail,
    )
}

fun DbContact.toDomain(): Contact {
    return Contact(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        picture = photo,
    )
}

fun Contact.toDbModel(): DbContact {
    return DbContact(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        photo = picture,
    )
}

