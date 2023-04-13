package com.baharudindayat.storyapp.data.remote.retrofit


import com.baharudindayat.storyapp.data.remote.response.GetStoriesResponse
import com.baharudindayat.storyapp.data.remote.response.LoginResponse
import com.baharudindayat.storyapp.data.remote.response.PostStoriesResponse
import com.baharudindayat.storyapp.data.remote.response.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun postSignUp(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<SignUpResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @Multipart
    @POST("stories")
    fun uploadStories(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<PostStoriesResponse>

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ) : Call<GetStoriesResponse>
}
