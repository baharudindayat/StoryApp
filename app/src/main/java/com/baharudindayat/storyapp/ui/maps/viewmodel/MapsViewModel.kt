package com.baharudindayat.storyapp.ui.maps.viewmodel

import androidx.lifecycle.ViewModel
import com.baharudindayat.storyapp.data.Repository

class MapsViewModel(private val repository: Repository): ViewModel() {
    fun getStoriesLocation(token: String) = repository.getStoriesMap(1,token)
}