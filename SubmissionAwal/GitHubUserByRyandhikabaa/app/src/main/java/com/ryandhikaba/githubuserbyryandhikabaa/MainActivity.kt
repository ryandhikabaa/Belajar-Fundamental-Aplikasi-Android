package com.ryandhikaba.githubuserbyryandhikabaa

import android.app.usage.UsageEvents
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryandhikaba.githubuserbyryandhikabaa.adapter.UsersAdapter
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.UsersResponse
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.UsersResponseItem
import com.ryandhikaba.githubuserbyryandhikabaa.data.retrofit.ApiConfig
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity ||  "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        with(binding) {
            val layoutManager = LinearLayoutManager(this@MainActivity)
            binding.rvusers.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this@MainActivity, layoutManager.orientation)
            binding.rvusers.addItemDecoration(itemDecoration)

            fetchUsers()
        }

    }

    private fun fetchUsers(){
        showLoading(true)
        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<List<UsersResponseItem>> {
            override fun onResponse(
                call: Call<List<UsersResponseItem>>,
                response: Response<List<UsersResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d(TAG, "onRespon: ${responseBody}")
                    if (responseBody != null) {
                        setUsersData(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure respon: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<UsersResponseItem>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure error: ${t.message}")
            }
        })
    }

    private fun setUsersData(usersItem: List<UsersResponseItem>) {
        val adapter = UsersAdapter()
        adapter.submitList(usersItem)
        binding.rvusers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.divLoading.visibility = View.VISIBLE
        } else {
            binding.divLoading.visibility = View.GONE
        }
    }

}