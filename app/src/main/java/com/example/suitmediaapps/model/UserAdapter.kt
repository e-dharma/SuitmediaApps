package com.example.suitmediaapps.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suitmediaapps.databinding.ActivityFirstScreenBinding.bind
import com.example.suitmediaapps.databinding.ItemListBinding
import com.example.suitmediaapps.util.loadImage

class UserAdapter(private val listener: OnUserClickListener) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private val mUsers = ArrayList<User>()

    fun addData(users: ArrayList<User>){
        mUsers.addAll(users)
        notifyDataSetChanged()
    }

    fun resetData(){
        mUsers.clear()
    }

    interface OnUserClickListener{
        fun onUserClick(data: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemListBinding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(itemListBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mUsers[position])
    }

    override fun getItemCount() = mUsers.size

    inner class ViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User){
            with(binding){
                with(user){
                    tvUserName.text = "$firstName $lastName"
                    tvUserEmail.text = email
                    imgUser.loadImage(avatar)
                    root.setOnClickListener{ listener.onUserClick(user) }
                }
            }
        }
    }
}