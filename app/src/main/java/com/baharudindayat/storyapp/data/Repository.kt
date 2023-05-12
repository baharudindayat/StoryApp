package com.baharudindayat.storyapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.baharudindayat.storyapp.data.remote.response.GetStoriesResponse
import com.baharudindayat.storyapp.data.remote.response.LoginResponse
import com.baharudindayat.storyapp.data.remote.response.PostStoriesResponse
import com.baharudindayat.storyapp.data.remote.response.SignUpResponse
import com.baharudindayat.storyapp.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository constructor(
    private val apiService: ApiService){
    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<StoryResult<SignUpResponse>> = liveData {
        emit(StoryResult.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(StoryResult.Success(response))
        } catch(e : Exception) {
            Log.d("register", e.message.toString())
            emit(StoryResult.Error(e.toString()))
        }
    }

    fun login(
        email: String,
        password: String
    ): LiveData<StoryResult<LoginResponse>> = liveData {
        emit(StoryResult.Loading)
        try {
            val response = apiService.login(email, password)
            emit(StoryResult.Success(response))
        } catch(e : Exception) {
            Log.d("login", e.message.toString())
            emit(StoryResult.Error(e.toString()))
        }
    }

    fun getStories(auth: String) : LiveData<StoryResult<GetStoriesResponse>> =
        liveData {
            emit(StoryResult.Loading)
            try {
                val generateToken = generateAuthorization(auth)
                val response = apiService.getStories(generateToken)
                emit(StoryResult.Success(response))
            } catch (e : Exception) {
                Log.d("getStory", e.message.toString())
                emit(StoryResult.Error(e.toString()))
            }
        }

    fun uploadStory(
        token: String,
        description: RequestBody,
        file: MultipartBody.Part
    ): LiveData<StoryResult<PostStoriesResponse>> = liveData {
        emit(StoryResult.Loading)
        try {
            val generateToken = generateAuthorization(token)
            val response = apiService.uploadStories(generateToken, file, description)
            emit(StoryResult.Success(response))
        } catch (e : Exception) {
            Log.d("uploadStory", e.message.toString())
            emit(StoryResult.Error(e.message.toString()))
        }
    }

    private fun generateAuthorization(token: String) : String {
        return "Bearer $token"
    }


}