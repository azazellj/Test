package com.ajax.ajaxtestassignment.ui.contactslist

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ajax.ajaxtestassignment.databinding.ItemContactListBinding
import com.ajax.ajaxtestassignment.domain.model.Contact

class ContactAdapter(
    private val context: Activity,
    private val onItemClicked: ItemClick
) : PagingDataAdapter<Contact, ViewHolder>(CONTACT_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            ItemContactListBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // todo: swipe to delete can be implemented with ItemTouchHelper.SimpleCallback
        // or another way (long press or something like that)
        val item = getItem(position)
        with(holder.binding) {
            fullName.text = item?.fullName
            email.text = item?.email
            root.setOnClickListener {
                onItemClicked(item?.id.toString())
            }
        }
    }
}

class ViewHolder(val binding: ItemContactListBinding) : RecyclerView.ViewHolder(binding.root)

typealias ItemClick = (id: String) -> Unit

private val CONTACT_DIFF = object : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}