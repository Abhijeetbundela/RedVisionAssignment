package com.example.redvisionassignment.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redvisionassignment.R
import com.example.redvisionassignment.activities.WebViewActivity
import com.example.redvisionassignment.model.Data
import de.hdodenhof.circleimageview.CircleImageView


class DataAdapter(
    private var items: List<Data>,
    private val callback: AdapterCallback,
    var context: Context
) :
    RecyclerView.Adapter<DataAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.data_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val data = items[position]

        Glide.with(context)
            .load(Uri.parse(data.profileImage))
            .placeholder(R.drawable.default_user)
            .into(holder.profileImage)

        holder.contact.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            val number = data.contact
            callIntent.data = Uri.parse("tel: $number")
            context.startActivity(callIntent)
        }

        holder.email.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:${data.email}")

            intent.putExtra(Intent.EXTRA_EMAIL, data.email)
            context.startActivity(intent)
        }

        holder.name.text = "Name: ${data.name}"
        holder.contact.text = "Contact: ${data.contact}"
        holder.address.text = "Address: ${data.address}"
        holder.email.text = "Email: ${data.email}"
        holder.profileLink.text = "ProfileLink: ${data.profileLink}"

        holder.profileLink.setOnClickListener {
            val webIntent = Intent(context, WebViewActivity::class.java)
            webIntent.putExtra("profileLink", data.profileLink)
            context.startActivity(webIntent)

        }

        if (data.like) {
            holder.like.setImageResource(R.drawable.ic_flash_yellow)
        } else {
            holder.like.setImageResource(R.drawable.ic_baseline_thumb_up_24)
        }

        holder.like.setOnClickListener {
            callback.like(data)
        }

        holder.updateButton.setOnClickListener {
            callback.update(data)
        }

        holder.deleteButton.setOnClickListener {
            callback.delete(data)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun addItem(dataList: List<Data>) {
        this.items = dataList
        notifyDataSetChanged()
    }

    interface AdapterCallback {
        fun update(data: Data)
        fun like(data: Data)
        fun delete(data: Data)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: CircleImageView = itemView.findViewById(R.id.profile_image)
        val name: TextView = itemView.findViewById(R.id.name)
        val contact: TextView = itemView.findViewById(R.id.contact)
        val email: TextView = itemView.findViewById(R.id.email)
        val profileLink: TextView = itemView.findViewById(R.id.profile_link)
        val address: TextView = itemView.findViewById(R.id.address)
        val updateButton: ImageView = itemView.findViewById(R.id.button_update)
        val deleteButton: ImageView = itemView.findViewById(R.id.button_delete)
        val like: ImageView = itemView.findViewById(R.id.like)

    }
}