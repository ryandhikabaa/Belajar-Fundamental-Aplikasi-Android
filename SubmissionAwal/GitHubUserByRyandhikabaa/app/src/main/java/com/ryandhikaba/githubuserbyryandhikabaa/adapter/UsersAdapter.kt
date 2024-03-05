package com.ryandhikaba.githubuserbyryandhikabaa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.ItemsItem
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ItemRowUsersBinding

class UsersAdapter  : ListAdapter<ItemsItem, UsersAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }
    class MyViewHolder(val binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: ItemsItem){
            binding.tvNameUsers.text = users.login
            Glide.with(binding.root)
                .load(users.avatarUrl)
                .into(binding.ivUsers)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}