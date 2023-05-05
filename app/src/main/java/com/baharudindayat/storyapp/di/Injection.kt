package com.baharudindayat.storyapp.di

import android.content.Context
import com.baharudindayat.storyapp.data.Repository
import com.baharudindayat.storyapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context) : Repository {
        val apiService = ApiConfig.getApiService()
        return Repository(apiService)
    }
}