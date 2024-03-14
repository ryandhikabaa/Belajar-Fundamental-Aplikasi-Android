package com.ryandhikaba.githubuserbyryandhikabaa.ui.MainAcivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.SearchView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ryandhikaba.githubuserbyryandhikabaa.R
import com.ryandhikaba.githubuserbyryandhikabaa.ui.adapter.UsersAdapter
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.ItemsItem
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ActivityMainBinding
import com.ryandhikaba.githubuserbyryandhikabaa.ui.DetailUserActivity.DetailUserActivity
import com.ryandhikaba.githubuserbyryandhikabaa.ui.FavoriteUsersActivity.FavoriteUsersActivity
import com.ryandhikaba.githubuserbyryandhikabaa.ui.SplashScreen.SplashScreenViewModel
import com.ryandhikaba.githubuserbyryandhikabaa.ui.ViewModelFactory.ViewModelFactory
import com.ryandhikaba.githubuserbyryandhikabaa.utils.SettingPreferences
import com.ryandhikaba.githubuserbyryandhikabaa.utils.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var username: String = "a"

    private lateinit var mainViewModel: MainViewModel


    companion object {
        private const val TAG = "RBA:MainActivity ||  "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = obtainViewModel(this@MainActivity)

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
                binding.switchTheme.setThumbIconResource(R.drawable.baseline_nightlight_24)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
                binding.switchTheme.setThumbIconResource(R.drawable.baseline_light_mode_24)
            }
        }
        mainViewModel.listItem.observe(this) { usersList ->
            setUsersData(usersList)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.snackbarText.observe(this) {

            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }


        with(binding) {


            val layoutManager = LinearLayoutManager(this@MainActivity)
            binding.rvusers.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this@MainActivity, layoutManager.orientation)
            binding.rvusers.addItemDecoration(itemDecoration)

            svUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()
                    svUsers.clearFocus()
                    query?.let {                             mainViewModel.updateUsername(it)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText.isNullOrEmpty()) {
                        hideKeyboard()
                        svUsers.clearFocus()
                        svUsers.isFocusable = false
                        svUsers.isFocusableInTouchMode = false
                        mainViewModel.updateUsername(username)

                    }
                    return true
                }
            })


            fabFav.imageTintList = ContextCompat.getColorStateList(this@MainActivity, R.color.colorPrimary)
            fabFav.backgroundTintList = ContextCompat.getColorStateList(this@MainActivity, R.color.greyMuda)
            fabFav.setOnClickListener(View.OnClickListener {
                startActivity(Intent(this@MainActivity, FavoriteUsersActivity::class.java))
            })

            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                mainViewModel.saveThemeSetting(isChecked)
            }

            var appVersion = applicationContext.packageManager.getPackageInfo(applicationContext.packageName, 0).versionName

            tvAppVersion.text = "Apps Ver. : $appVersion"

        }

    }

    private fun setUsersData(usersItem: List<ItemsItem>) {
        val adapter = UsersAdapter()
        adapter.submitList(usersItem)
        binding.rvusers.adapter = adapter
        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra("USERS_CLICKED", data)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(state: Boolean) { binding.divLoading.visibility = if (state) View.VISIBLE else View.GONE }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(application, SettingPreferences.getInstance(application.dataStore))
        return ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

}