package com.ryandhikaba.githubuserbyryandhikabaa.ui.DetailUserActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ryandhikaba.githubuserbyryandhikabaa.R
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.ItemsItem
import com.ryandhikaba.githubuserbyryandhikabaa.database.UsersFavEntity
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ActivityDetailUserBinding
import com.ryandhikaba.githubuserbyryandhikabaa.ui.ViewModelFactory.ViewModelFactory
import com.ryandhikaba.githubuserbyryandhikabaa.ui.DetailUserActivity.TabDetailUser.SectionsPagerAdapter
import com.ryandhikaba.githubuserbyryandhikabaa.utils.SettingPreferences
import com.ryandhikaba.githubuserbyryandhikabaa.utils.dataStore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailUserActivity : AppCompatActivity() {


    private lateinit var detailUserViewModel: DetailUserViewModel

    private lateinit var binding: ActivityDetailUserBinding

    private var _isFavorite: Boolean = false


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

        detailUserViewModel = obtainViewModel(this@DetailUserActivity)

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailUserViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        with(binding){
            val users: ItemsItem? = intent.getParcelableExtra("USERS_CLICKED")
            val usersname= intent.getStringExtra("UERNAME_USERS_CLICKED")

            if (users != null) {

                detailUserViewModel.fetchDetailUsers(users.login)

                detailUserViewModel.detailUserRespon.observe(this@DetailUserActivity, Observer { detailUser ->
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

                        ivShare.setOnClickListener(View.OnClickListener {
                            val shareIntent = Intent()
                            shareIntent.action = Intent.ACTION_SEND
                            shareIntent.type = "text/plain"
                            shareIntent.putExtra(Intent.EXTRA_TEXT, detailUser.htmlUrl)
                            startActivity(Intent.createChooser(shareIntent, "Bagikan link melalui"))
                        })
                    }
                })


                detailUserViewModel.getFavoriteUserByUsername(users.login).observe(this@DetailUserActivity, Observer { userFav ->
                    if (userFav != null) {
                        ivFav.setImageResource(R.drawable.baseline_favorite_24)
                        tvFav.text = "Unfavorite"
                        divFav.setBackgroundResource(R.drawable.rectangle_grey_tua)
                        _isFavorite = false
                    } else {
                        ivFav.setImageResource(R.drawable.baseline_favorite_border_24)
                        tvFav.text = "Favorite"
                        divFav.setBackgroundResource(R.drawable.rectangle_grey_muda)
                        _isFavorite = true
                    }
                })

                divFav.setOnClickListener(View.OnClickListener {
                    val userFavClick = UsersFavEntity(
                        username = users.login,
                        avatar = users.avatarUrl
                    )
                    if (_isFavorite){
                        detailUserViewModel.insert(userFavClick)
                    }else{
                        detailUserViewModel.delete(userFavClick)
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


    private fun showLoading(state: Boolean) { binding.divLoading.visibility = if (state) View.VISIBLE else View.GONE }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, SettingPreferences.getInstance(application.dataStore))
        return ViewModelProvider(activity, factory).get(DetailUserViewModel::class.java)
    }

}