package com.baharudindayat.storyapp.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.baharudindayat.storyapp.data.Repository

class RegisterViewModel constructor(private  val repository: Repository) : ViewModel(){
    fun register(name: String, email: String, password: String) = repository.register(name, email, password)
}