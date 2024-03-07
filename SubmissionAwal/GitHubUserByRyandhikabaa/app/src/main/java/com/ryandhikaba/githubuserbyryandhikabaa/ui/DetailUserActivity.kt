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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import com.ryandhikaba.githubuserbyryandhikabaa.ui.ViewModel.DetailUserViewModel
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

        val detaiMainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailUserViewModel::class.java)

        detaiMainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detaiMainViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        with(binding){
            // Terima objek Service dari Intent
            val users: ItemsItem? = intent.getParcelableExtra("USERS_CLICKED")

            // Periksa apakah objek Service tidak null
            if (users != null) {

                detaiMainViewModel.fetchDetailUsers(users.login)

                detaiMainViewModel.detailUserRespon.observe(this@DetailUserActivity, Observer { detailUser ->
                    // Perbarui antarmuka pengguna (UI) dengan detail pengguna yang diterima
                    detailUser?.let {
                        binding.tvNameUsers.text = (it.name ?: it.login).toString()
                        Glide.with(binding.root)
                            .load(it.avatarUrl)
                            .into(binding.ivUsers)
                        binding.tvUsername.text = "@${it.login}"
                        binding.tvCountFollowers.text = it.followers?.toString() ?: "0"
                        binding.tvCountFollowing.text = it.following?.toString() ?: "0"
                        val originalDateTime = LocalDateTime.parse(it.createdAt, DateTimeFormatter.ISO_DATE_TIME)
                        val formattedDate = originalDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        binding.tvSince.text = "Since Github From : $formattedDate"
                    }
                })

                ivBack.setOnClickListener(View.OnClickListener { finish() })

                val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailUserActivity)
                sectionsPagerAdapter.username = users.login
                val viewPager: ViewPager2 = findViewById(R.id.view_pager)
                viewPager.adapter = sectionsPagerAdapter
                val tabs: TabLayout = findViewById(R.id.tabs)
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()

            } else {
                Toast.makeText(this@DetailUserActivity, "Opps!, Data Service Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                finish()
            }


        }
    }



    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.divLoading.visibility = View.VISIBLE
        } else {
            binding.divLoading.visibility = View.GONE
        }
    }

}