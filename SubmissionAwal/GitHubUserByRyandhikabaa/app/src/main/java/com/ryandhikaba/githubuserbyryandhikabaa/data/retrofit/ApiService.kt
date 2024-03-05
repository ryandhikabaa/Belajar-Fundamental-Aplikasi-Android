package com.ryandhikaba.githubuserbyryandhikabaa.data.retrofit

import com.ryandhikaba.githubuserbyryandhikabaa.data.response.UsersResponse
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.UsersResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<UsersResponseItem>>

}