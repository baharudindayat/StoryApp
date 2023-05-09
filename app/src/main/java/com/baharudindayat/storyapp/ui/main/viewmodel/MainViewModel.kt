package com.baharudindayat.storyapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baharudindayat.storyapp.data.Repository
import com.baharudindayat.storyapp.data.StoryResult
import com.baharudindayat.storyapp.data.remote.response.GetStoriesResponse
import com.baharudindayat.storyapp.data.remote.response.Story
import kotlinx.coroutines.launch

class MainViewModel constructor(private val repository: Repository): ViewModel(){
    fun getStory(token: String) : LiveData<StoryResult<GetStoriesResponse>> = repository.getStories(token)
}