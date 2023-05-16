package com.baharudindayat.storyapp.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.baharudindayat.storyapp.R
import com.baharudindayat.storyapp.data.StoryResult
import com.baharudindayat.storyapp.data.local.preferences.User
import com.baharudindayat.storyapp.data.local.preferences.UserPreferences
import com.baharudindayat.storyapp.databinding.ActivityLoginBinding
import com.baharudindayat.storyapp.ui.auth.viewmodel.LoginViewModel
import com.baharudindayat.storyapp.ui.main.MainActivity
import com.baharudindayat.storyapp.utils.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreferences: UserPreferences
    private var userModel: User = User()
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        playAnimation()

        userPreferences = UserPreferences(this)

        binding.tvRegister.setOnClickListener {
            moveToRegister()
        }

        login()

    }
    private fun login() {
        binding.btnLogin.setOnClickListener {
            loginViewModel.login(
                binding.edtEmail.text.toString().trim(),
                binding.edtPassword.text.toString().trim()
            ).observe(this){result ->
                when(result) {
                    is StoryResult.Loading -> {
                        showLoading(true)
                    }
                    is StoryResult.Success -> {
                        showLoading(false)
                        val response = result.data
                        saveToken(response.loginResult.token)
                        Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                        intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is StoryResult.Error -> {
                        Toast.makeText(this, getString(R.string.login_failed) , Toast.LENGTH_SHORT).show()
                        showLoading(false)
                    }
                }
            }
        }
    }
    private fun showLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }
    private fun moveToRegister() {
        intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imgLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = DURATION.toLong()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val loginTitle = ObjectAnimator.ofFloat(binding.tvLogin,View.ALPHA,1f).setDuration(DURATION2.toLong())
        val editTextLogin = ObjectAnimator.ofFloat(binding.edtEmail,View.ALPHA,1f).setDuration(DURATION2.toLong())
        val editTextPassword = ObjectAnimator.ofFloat(binding.edtPassword,View.ALPHA,1f).setDuration(DURATION2.toLong())
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin,View.ALPHA,1f).setDuration(DURATION2.toLong())

        AnimatorSet().apply {
            playSequentially(loginTitle,editTextLogin,editTextPassword,btnLogin)
            startDelay = DURATION2.toLong()
        }.start()

    }
    private fun saveToken(token: String) {
        userModel.token = token
        userPreferences.saveUser(userModel)
    }
    companion object{
        const val DURATION = 4000
        const val DURATION2 = 400
    }
}


