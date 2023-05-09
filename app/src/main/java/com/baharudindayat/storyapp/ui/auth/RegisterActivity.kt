package com.baharudindayat.storyapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.baharudindayat.storyapp.R
import com.baharudindayat.storyapp.data.StoryResult
import com.baharudindayat.storyapp.databinding.ActivityRegisterBinding
import com.baharudindayat.storyapp.ui.auth.viewmodel.RegisterViewModel
import com.baharudindayat.storyapp.utils.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val registerViewModel: RegisterViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        binding.tvLoginHere.setOnClickListener {
            moveToLogin()
        }

        register()


    }

    private fun register(){
        binding.btnRegister.setOnClickListener {
            registerViewModel.register(
                binding.edtName.text.toString().trim(),
                binding.edtEmail.text.toString().trim(),
                binding.edtPassword.text.toString().trim()
            ).observe(this){ result ->
                when(result) {
                    is StoryResult.Success -> {
                        showLoading(false)
                        startActivity(Intent(this, LoginActivity::class.java))
                        Toast.makeText(this, getString(R.string.success_register), Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    is StoryResult.Loading -> {
                        showLoading(true)
                    }
                    is StoryResult.Error -> {
                        Toast.makeText(this, getString(R.string.error_register), Toast.LENGTH_SHORT).show()
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

    private fun moveToLogin() {
        intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}