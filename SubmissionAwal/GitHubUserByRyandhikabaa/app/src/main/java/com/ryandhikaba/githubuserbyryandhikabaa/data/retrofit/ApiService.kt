package com.ryandhikaba.githubuserbyryandhikabaa.data.retrofit

import com.ryandhikaba.githubuserbyryandhikabaa.data.response.UsersResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q") username: String
    ): Call<UsersResponse>

}