package com.example.brainrealm.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brainrealm.Models.UserModel
import com.example.brainrealm.R
import com.example.brainrealm.databinding.UserCardBinding

class LeaderListAdapter(private val context: Context, private var userList: List<UserModel>) :
    RecyclerView.Adapter<LeaderListAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val sortedUserList = userList.sortedByDescending { it.xal }
        val user = sortedUserList[position]
        holder.bind(user, position + 1)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateUserList(newList: List<UserModel>) {
        userList = newList
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: UserCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserModel, position: Int) {
            binding.userCardNumber.text = position.toString()
            binding.userCardFullName.text = user.fullName.toString()
            binding.userCardXal.text = user.xal.toString()

        }
    }
}

