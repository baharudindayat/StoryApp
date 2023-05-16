package com.baharudindayat.storyapp.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baharudindayat.storyapp.data.Repository
import com.baharudindayat.storyapp.di.Injection
import com.baharudindayat.storyapp.ui.auth.viewmodel.LoginViewModel
import com.baharudindayat.storyapp.ui.auth.viewmodel.RegisterViewModel
import com.baharudindayat.storyapp.ui.main.viewmodel.MainViewModel
import com.baharudindayat.storyapp.ui.maps.viewmodel.MapsViewModel
import com.baharudindayat.storyapp.ui.story.viewmodel.StoryViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val Repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(Repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(Repository) as T
            }
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                StoryViewModel(Repository) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(Repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(Context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(Context))
            }.also { instance = it }
    }

}
