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