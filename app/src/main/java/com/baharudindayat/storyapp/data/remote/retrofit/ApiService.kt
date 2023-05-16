package com.baharudindayat.storyapp.data.remote.retrofit


import com.baharudindayat.storyapp.data.remote.response.GetStoriesResponse
import com.baharudindayat.storyapp.data.remote.response.LoginResponse
import com.baharudindayat.storyapp.data.remote.response.PostStoriesResponse
import com.baharudindayat.storyapp.data.remote.response.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SignUpResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStories(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Double?,
        @Part("lon") lon: Double?
    ): PostStoriesResponse

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") auth: String
    ) : GetStoriesResponse
    @GET("stories")
    suspend fun getStoriesPaging(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Authorization") auth: String
    ) : GetStoriesResponse
    @GET("stories")
    suspend fun getStoriesLocation(
        @Query("location") loc: Int,
        @Header("Authorization") auth: String
    ) : GetStoriesResponse

}
