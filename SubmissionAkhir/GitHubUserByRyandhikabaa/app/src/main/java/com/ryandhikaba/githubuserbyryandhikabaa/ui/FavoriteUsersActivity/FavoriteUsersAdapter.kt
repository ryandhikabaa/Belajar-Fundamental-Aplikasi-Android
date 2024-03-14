package com.ryandhikaba.githubuserbyryandhikabaa.ui.FavoriteUsersActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.ItemsItem
import com.ryandhikaba.githubuserbyryandhikabaa.database.UsersFavEntity
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ItemRowUsersBinding
import com.ryandhikaba.githubuserbyryandhikabaa.helper.FavoriteUsersDiffCallback

class FavoriteUsersAdapter : RecyclerView.Adapter<FavoriteUsersAdapter.FavoriteUsersViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback


    private val listFavoriteUsers = ArrayList<UsersFavEntity>()
    fun setListUsersFavorite(listFavoriteUsers: List<UsersFavEntity>) {
        val diffCallback = FavoriteUsersDiffCallback(this.listFavoriteUsers, listFavoriteUsers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteUsers.clear()
        this.listFavoriteUsers.addAll(listFavoriteUsers)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUsersViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUsersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFavoriteUsers.size
    }

    override fun onBindViewHolder(holder: FavoriteUsersViewHolder, position: Int) {
        holder.bind(listFavoriteUsers[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listFavoriteUsers[position])
        }
    }

    class FavoriteUsersViewHolder(private val binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(usersFavEntity: UsersFavEntity) {
            with(binding){
                tvNameUsers.text = usersFavEntity.username
                Glide.with(root)
                    .load(usersFavEntity.avatar)
                    .into(ivUsers)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UsersFavEntity)
    }
}