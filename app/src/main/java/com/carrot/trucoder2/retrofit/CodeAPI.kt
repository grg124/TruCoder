package com.carrot.trucoder2.retrofit

import com.carrot.trucoder2.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CodeAPI {
    @GET("codeforces")
    suspend fun fetchCodeforcesUser(@Query("user") user: String): Response<ResponseCodforces>

    @GET("codechef")
    suspend fun fetchCodechefUser(@Query("user") user:String):Response<ResponseCodechef>

    @GET("codeforces/friends")
    suspend fun fetchFriendsListCF(@Query("handles") handles:String):Response<ResponseLeaderboard>

    @GET("codechef/friends")
    suspend fun fetchFriendListCC(@Query("handles") handles :String) :Response<ResponseLeaderboard>

    @GET("contests")
    suspend fun fetchContest():Response<ResponseContest>
}