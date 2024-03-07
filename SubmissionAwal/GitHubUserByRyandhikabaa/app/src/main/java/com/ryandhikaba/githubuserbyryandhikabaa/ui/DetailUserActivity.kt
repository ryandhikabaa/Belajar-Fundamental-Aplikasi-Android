package com.ryandhikaba.githubuserbyryandhikabaa.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ryandhikaba.githubuserbyryandhikabaa.R
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.DetailUserRespon
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.ItemsItem
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.UsersResponse
import com.ryandhikaba.githubuserbyryandhikabaa.data.retrofit.ApiConfig
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ActivityDetailUserBinding
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ActivityMainBinding
import com.ryandhikaba.githubuserbyryandhikabaa.ui.adapter.SectionsPagerAdapter
import com.ryandhikaba.githubuserbyryandhikabaa.utils.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        private const val TAG = "RBA:DetailUsereActivity ||  "

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_followers,
            R.string.tab_text_following
        )
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
                ivBack.setOnClickListener(View.OnClickListener { finish() })
                fetchDetailUsers(users.login)
                val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailUserActivity)
                sectionsPagerAdapter.username = users.login
                val viewPager: ViewPager2 = findViewById(R.id.view_pager)
                viewPager.adapter = sectionsPagerAdapter
                val tabs: TabLayout = findViewById(R.id.tabs)
//                tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(this@DetailUserActivity, R.color.colorAccent))
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
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
                    Log.e(TAG, "onFailure respon: ${response}")
                    Snackbar.make(binding.root, Config.Constants.OPPS + " ${response.message()}", Snackbar.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<DetailUserRespon>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure error: ${t.message}")
                Snackbar.make(binding.root, Config.Constants.EROR_JARINGAN_ON_ERROR, Snackbar.LENGTH_SHORT).show()
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