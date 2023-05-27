package com.baharudindayat.storyapp.ui.story.viewmodel

import androidx.lifecycle.ViewModel
import com.baharudindayat.storyapp.data.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody


class StoryViewModel constructor(private val repository: Repository): ViewModel(){
    fun uploadStory(auth: String, description: RequestBody, file: MultipartBody.Part, lat: Double?, lon: Double?) =
        repository.uploadStory(auth, description, file,lat,lon)
}