package com.baharudindayat.storyapp.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.baharudindayat.storyapp.data.Repository

class LoginViewModel(private val Repository: Repository): ViewModel() {
    fun login(email: String, password: String) = Repository.login(email, password)
}