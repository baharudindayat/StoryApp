package com.baharudindayat.storyapp.di

import android.content.Context
import com.baharudindayat.storyapp.data.Repository
import com.baharudindayat.storyapp.data.local.database.database.StoryDatabase
import com.baharudindayat.storyapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(Context: Context) : Repository {
        val apiService = ApiConfig.getApiService()
        val database = StoryDatabase.getDatabase(Context)
        return Repository(apiService, database)
    }
}