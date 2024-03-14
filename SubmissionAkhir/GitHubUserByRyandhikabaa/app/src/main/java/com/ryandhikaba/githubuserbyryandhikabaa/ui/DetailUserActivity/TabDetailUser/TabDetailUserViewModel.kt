package com.ryandhikaba.githubuserbyryandhikabaa.ui.DetailUserActivity.TabDetailUser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.ItemsItem
import com.ryandhikaba.githubuserbyryandhikabaa.data.retrofit.ApiConfig
import com.ryandhikaba.githubuserbyryandhikabaa.utils.Config
import com.ryandhikaba.githubuserbyryandhikabaa.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TabDetailUserViewModel : ViewModel() {

    private val _listItem = MutableLiveData<List<ItemsItem>>()
    val listItem: LiveData<List<ItemsItem>> = _listItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showRecycler = MutableLiveData<Boolean>()
    val showRecycler: LiveData<Boolean> = _showRecycler

    private val _showKeterangan = MutableLiveData<Boolean>()
    val showKeterangan: LiveData<Boolean> = _showKeterangan

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    companion object{
        private const val TAG = "TabDetailUserViewModel"
    }

    fun fetchFollowers(username: String){
        _isLoading.value = true
        _showRecycler.value = false
        _showKeterangan.value = false
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.size == 0){
                            _showKeterangan.value = true
                            _snackbarText.value = Event("Tidak Memiliki Followers")

                        }else{
                            _listItem.value = response.body()
                            _showRecycler.value = true
                        }
                    }
                } else {
                    _snackbarText.value = Event(Config.Constants.OPPS + " ${response}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                _showRecycler.value = false
                _showKeterangan.value = false
                Log.e(TAG, "onFailure error: ${t.message}")
                _snackbarText.value = Event(Config.Constants.EROR_JARINGAN_ON_ERROR)

            }
        })
    }

    fun fetchFollowing(username: String){
        _isLoading.value = true
        _showRecycler.value = false
        _showKeterangan.value = false
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.size == 0){
                            _showKeterangan.value = true
                            _snackbarText.value = Event("Tidak Memiliki Following")

                        }else{
                            _listItem.value = response.body()
                            _showRecycler.value = true
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure respon: ${response}")
                    _snackbarText.value = Event(Config.Constants.OPPS + " ${response}")

                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                _showRecycler.value = false
                _showKeterangan.value = false
                Log.e(TAG, "onFailure error: ${t.message}")
                _snackbarText.value = Event(Config.Constants.EROR_JARINGAN_ON_ERROR)
            }
        })
    }
}