package com.ryandhikaba.githubuserbyryandhikabaa.ui.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.DetailUserRespon
import com.ryandhikaba.githubuserbyryandhikabaa.data.retrofit.ApiConfig
import com.ryandhikaba.githubuserbyryandhikabaa.ui.DetailUserActivity
import com.ryandhikaba.githubuserbyryandhikabaa.utils.Config
import com.ryandhikaba.githubuserbyryandhikabaa.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailUserViewModel : ViewModel() {

    private val _detailUserRespon = MutableLiveData<DetailUserRespon>()
    val detailUserRespon: LiveData<DetailUserRespon> = _detailUserRespon

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    companion object{
        private const val TAG = "DetailUserViewModel"
    }

    fun fetchDetailUsers(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserRespon> {
            override fun onResponse(
                call: Call<DetailUserRespon>,
                response: Response<DetailUserRespon>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        responseBody?.let {
                            _detailUserRespon.value = it
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure respon: ${response}")
                    _snackbarText.value = Event(Config.Constants.OPPS + " ${response}")
                }
            }
            override fun onFailure(call: Call<DetailUserRespon>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure error: ${t.message}")
                _snackbarText.value = Event(Config.Constants.EROR_JARINGAN_ON_ERROR)
            }
        })
    }
}