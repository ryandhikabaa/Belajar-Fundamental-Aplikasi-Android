package com.ryandhikaba.githubuserbyryandhikabaa.helper

import androidx.recyclerview.widget.DiffUtil
import com.ryandhikaba.githubuserbyryandhikabaa.database.UsersFavEntity

class FavoriteUsersDiffCallback (private val oldNoteList: List<UsersFavEntity>, private val newNoteList: List<UsersFavEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size
    override fun getNewListSize(): Int = newNoteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].username == newNoteList[newItemPosition].username
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.username == newNote.username && oldNote.avatar == newNote.avatar
    }
}