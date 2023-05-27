package com.baharudindayat.storyapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.baharudindayat.storyapp.data.Repository
import com.baharudindayat.storyapp.data.remote.response.Story

class MainViewModel constructor(private val repository: Repository): ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getStory(auth: String) : LiveData<PagingData<Story>> =
        repository.getStoriesPaging(auth).cachedIn(viewModelScope)

}