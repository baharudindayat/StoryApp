package com.baharudindayat.storyapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val loginViewModel: LoginViewModel by viewModels {
            factory
        }

        userPreferences = UserPreferences(this)
        binding.tvRegister.setOnClickListener {
            moveToRegister()
        }

        binding.btnLogin.setOnClickListener {
            loginViewModel.login(
                binding.edtEmail.text.toString().trim(),
                binding.edtPassword.text.toString().trim()
            ).observe(this){result ->
                when(result) {
                    is StoryResult.Success -> {
                        val response = result.data
                        saveToken(response.loginResult.token)
                        Toast.makeText(this, "Login ${result.data.message}", Toast.LENGTH_SHORT).show()
                        intent = Intent(this, MainActivity::class.java)
                        intent.putExtra(EXTRA_KEY, response.loginResult.token)
                        startActivity(intent)
                        finish()
                    }
                    is StoryResult.Loading -> {
                        showLoading(true)
                    }
                    is StoryResult.Error -> {
                        Toast.makeText(this, "Login ${result.error}" , Toast.LENGTH_SHORT).show()
                        showLoading(false)
                    }
                }
            }
        }
    }
    private fun showLoading(loading: Boolean) {
        when(loading) {
            true -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            false -> {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    private fun moveToRegister() {
        intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
    private fun saveToken(token: String) {
        userModel.token = token
        userPreferences.setUser(userModel)
    }
    companion object {
        const val EXTRA_KEY = "extra_key"
    }
}

