package com.ryandhikaba.githubuserbyryandhikabaa.ui.FavoriteUsersActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryandhikaba.githubuserbyryandhikabaa.R
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.ItemsItem
import com.ryandhikaba.githubuserbyryandhikabaa.database.UsersFavEntity
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ActivityFavoriteUsersBinding
import com.ryandhikaba.githubuserbyryandhikabaa.ui.DetailUserActivity.DetailUserActivity
import com.ryandhikaba.githubuserbyryandhikabaa.ui.ViewModelFactory.ViewModelFactory
import com.ryandhikaba.githubuserbyryandhikabaa.ui.adapter.UsersAdapter
import com.ryandhikaba.githubuserbyryandhikabaa.utils.SettingPreferences
import com.ryandhikaba.githubuserbyryandhikabaa.utils.dataStore

class FavoriteUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUsersBinding

    private lateinit var adapter: FavoriteUsersAdapter

    private lateinit var favoriteUsersViewModel: FavoriteUsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            toolbar.navigationIcon?.setTint(resources.getColor(android.R.color.white))

            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }

            adapter = FavoriteUsersAdapter()
            rvusers.layoutManager = LinearLayoutManager(this@FavoriteUsersActivity)
            rvusers.setHasFixedSize(true)
            rvusers.adapter = adapter

            favoriteUsersViewModel = obtainViewModel(this@FavoriteUsersActivity)

            favoriteUsersViewModel.getAllUsersFav().observe(this@FavoriteUsersActivity) { usersFavList ->
                if (usersFavList != null) {
                    adapter.setListUsersFavorite(usersFavList)
                }
            }

            adapter.setOnItemClickCallback(object : FavoriteUsersAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UsersFavEntity) {
                    val userFavClick = ItemsItem(
                        login = data.username,
                        avatarUrl = data.avatar!!,
                        url = "",
                        htmlUrl = ""
                    )
                    val intent = Intent(this@FavoriteUsersActivity, DetailUserActivity::class.java)
                    intent.putExtra("USERS_CLICKED", userFavClick)
                    startActivity(intent)
                }
            })
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUsersViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, SettingPreferences.getInstance(application.dataStore))
        return ViewModelProvider(activity, factory).get(FavoriteUsersViewModel::class.java)
    }
}