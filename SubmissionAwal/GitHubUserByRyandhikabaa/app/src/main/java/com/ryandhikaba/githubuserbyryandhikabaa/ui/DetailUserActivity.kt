package com.ryandhikaba.githubuserbyryandhikabaa.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.ryandhikaba.githubuserbyryandhikabaa.R
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.DetailUserRespon
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.ItemsItem
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.UsersResponse
import com.ryandhikaba.githubuserbyryandhikabaa.data.retrofit.ApiConfig
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ActivityDetailUserBinding
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        private const val TAG = "RBA:DetailUsereActivity ||  "
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            // Terima objek Service dari Intent
            val users: ItemsItem? = intent.getParcelableExtra("USERS_CLICKED")

            // Periksa apakah objek Service tidak null
            if (users != null) {
                fetchDetailUsers(users.login)
            } else {
                Toast.makeText(this@DetailUserActivity, "Opps!, Data Service Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun fetchDetailUsers(username: String){
        showLoading(true)
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserRespon> {
            override fun onResponse(
                call: Call<DetailUserRespon>,
                response: Response<DetailUserRespon>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        binding.tvNameUsers.text = responseBody.name?.toString() ?: responseBody.login
                        Glide.with(binding.root)
                            .load(responseBody.avatarUrl)
                            .into(binding.ivUsers)
                        binding.tvUsername.text = "@${responseBody.login}"
                        binding.tvCountFollowers.text = responseBody.followers?.toString() ?: "0"
                        binding.tvCountFollowing.text = responseBody.following?.toString() ?: "0"
                        // Parsing tanggal dari string asal
                        val originalDateTime = LocalDateTime.parse(responseBody.createdAt, DateTimeFormatter.ISO_DATE_TIME)
                        // Mengubah format tanggal menjadi format yang diinginkan
                        val formattedDate = originalDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        binding.tvSince.text = "Since Github From : $formattedDate"

                        showLoading(false)
                    }
                } else {
                    Log.e(TAG, "onFailure respon: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserRespon>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure error: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.divLoading.visibility = View.VISIBLE
        } else {
            binding.divLoading.visibility = View.GONE
        }
    }
}