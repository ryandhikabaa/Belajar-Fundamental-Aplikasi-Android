package com.ryandhikaba.githubuserbyryandhikabaa.ui.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.ItemsItem
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.UsersResponse
import com.ryandhikaba.githubuserbyryandhikabaa.data.retrofit.ApiConfig
import com.ryandhikaba.githubuserbyryandhikabaa.ui.MainActivity
import com.ryandhikaba.githubuserbyryandhikabaa.utils.Config
import com.ryandhikaba.githubuserbyryandhikabaa.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _userRespon = MutableLiveData<UsersResponse>()
    val userResponse: LiveData<UsersResponse> = _userRespon

    private val _listItem = MutableLiveData<List<ItemsItem>>()
    val listItem: LiveData<List<ItemsItem>> = _listItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _username = MutableLiveData<String>("a")

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText



    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        fetchUsers(_username.value!!)
    }

    fun updateUsername(newUsername: String) {
        _username.value = newUsername
        fetchUsers(newUsername)
    }

    private fun fetchUsers(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(username)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(
                call: Call<UsersResponse>,
                response: Response<UsersResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listItem.value = responseBody?.items
                    }
                } else {
                    Log.e(TAG, "onFailure respon: ${response}")
                    _snackbarText.value = Event(Config.Constants.OPPS + " ${response}")
                }
            }
            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure error: ${t.message}")
                _snackbarText.value = Event(Config.Constants.EROR_JARINGAN_ON_ERROR)
            }
        })
    }

}