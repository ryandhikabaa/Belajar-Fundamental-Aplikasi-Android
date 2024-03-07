package com.ryandhikaba.githubuserbyryandhikabaa.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ryandhikaba.githubuserbyryandhikabaa.ui.adapter.UsersAdapter
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.ItemsItem
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.UsersResponse
import com.ryandhikaba.githubuserbyryandhikabaa.data.retrofit.ApiConfig
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.ActivityMainBinding
import com.ryandhikaba.githubuserbyryandhikabaa.ui.ViewModel.MainViewModel
import com.ryandhikaba.githubuserbyryandhikabaa.utils.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var username: String = "a"

    companion object {
        private const val TAG = "RBA:MainActivity ||  "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        mainViewModel.listItem.observe(this) { usersList ->
            setUsersData(usersList)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.snackbarText.observe(this) {
//            Snackbar.make(window.decorView.rootView, it, Snackbar.LENGTH_SHORT).show()

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

//            fetchUsers(username)

            svUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()
                    svUsers.clearFocus()
                    query?.let {                             mainViewModel.updateUsername(it)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // Panggil fungsi untuk menangani perubahan teks query hanya jika panjang teks lebih dari 3 karakter
                    if (newText?.length ?: 0 >= 3) {
                        newText?.let {
                            mainViewModel.updateUsername(newText)
                        }
                    }

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

        }

    }

//    private fun fetchUsers(username: String){
//        showLoading(true)
//        val client = ApiConfig.getApiService().getUsers(username)
//        client.enqueue(object : Callback<UsersResponse>{
//            override fun onResponse(
//                call: Call<UsersResponse>,
//                response: Response<UsersResponse>
//            ) {
//                showLoading(false)
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        setUsersData(responseBody.items)
//                    }
//                } else {
//                    Log.e(TAG, "onFailure respon: ${response.message()}")
//                    Snackbar.make(binding.root, Config.Constants.OPPS + " ${response.message()}", Snackbar.LENGTH_SHORT).show()
//                }
//            }
//            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
//                showLoading(false)
//                Log.e(TAG, "onFailure error: ${t.message}")
//                Snackbar.make(binding.root, Config.Constants.EROR_JARINGAN_ON_ERROR, Snackbar.LENGTH_SHORT).show()
//            }
//        })
//    }

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

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.divLoading.visibility = View.VISIBLE
        } else {
            binding.divLoading.visibility = View.GONE
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

}