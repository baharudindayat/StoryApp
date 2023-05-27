package com.baharudindayat.storyapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.baharudindayat.storyapp.data.local.database.database.StoryDatabase
import com.baharudindayat.storyapp.data.remote.response.*
import com.baharudindayat.storyapp.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository constructor(
    private val apiService: ApiService,
    private val storyDatabase: StoryDatabase
    ){
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
    fun uploadStory(
        token: String,
        description: RequestBody,
        file: MultipartBody.Part,
        lat: Double?,
        lon: Double?
    ): LiveData<StoryResult<PostStoriesResponse>> = liveData {
        emit(StoryResult.Loading)
        try {
            val generateToken = generateAuthorization(token)
            val response = apiService.uploadStories(generateToken, file, description,lat,lon)
            emit(StoryResult.Success(response))
        } catch (e : Exception) {
            Log.d("uploadStory", e.message.toString())
            emit(StoryResult.Error(e.message.toString()))
        }
    }

    fun getStoriesMap(location: Int, token: String) : LiveData<StoryResult<List<Story>>> =
        liveData {
            emit(StoryResult.Loading)
            try {
                val generateToken = generateAuthorization(token)
                val response = apiService.getStoriesLocation(location,generateToken)
                val listMap = response.listStory
                if (listMap.isNotEmpty()) {
                    emit(StoryResult.Success(listMap))
                }
            } catch (e : Exception) {
                Log.d("getStoryMap", e.message.toString())
                emit(StoryResult.Error(e.toString()))
            }
        }

    @OptIn(ExperimentalPagingApi::class)
    fun getStoriesPaging(token: String): LiveData<PagingData<Story>> {
        val generateToken = generateAuthorization(token)
        return Pager(
            config = PagingConfig(
                pageSize = 6
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, generateToken),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
    
    private fun generateAuthorization(token: String) : String {
        return "Bearer $token"
    }

}