package fr.hetic.app_map_amis.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.hetic.app_map_amis.R
import fr.hetic.app_map_amis.data.Contact
import kotlinx.android.synthetic.main.contact_layout.view.*

class ContactRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Contact> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contact_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is ContactViewHolder -> {
                holder.run { bind(items.get(position)) }
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(contactList: List<Contact>){
        items = contactList
    }

    class ContactViewHolder
    constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){

        val contact_image = itemView.rowContactImageView
        val contact_username = itemView.textAPI

        fun bind(Contact: Contact){

            Glide.with(itemView.context)
                .load(Contact.image)
                .into(contact_image)
            contact_username.text = Contact.username

        }

    }

}