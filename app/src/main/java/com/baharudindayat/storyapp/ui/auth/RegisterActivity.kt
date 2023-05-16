package com.baharudindayat.storyapp.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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

        playAnimation()


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
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun moveToLogin() {
        intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imgLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = DURATION.toLong()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val registerTitle = ObjectAnimator.ofFloat(binding.tvRegister,View.ALPHA,1f).setDuration(DURATION2.toLong())
        val registerEmail = ObjectAnimator.ofFloat(binding.edtEmail,View.ALPHA,1f).setDuration(DURATION2.toLong())
        val registerPassword = ObjectAnimator.ofFloat(binding.edtPassword,View.ALPHA,1f).setDuration(DURATION2.toLong())
        val registerName = ObjectAnimator.ofFloat(binding.edtName,View.ALPHA,1f).setDuration(DURATION2.toLong())
        val registerButton = ObjectAnimator.ofFloat(binding.btnRegister,View.ALPHA,1f).setDuration(DURATION2.toLong())

        AnimatorSet().apply {
            playSequentially(registerTitle,registerEmail,registerPassword,registerName,registerButton)
            startDelay = DURATION2.toLong()
            start()
        }
    }
    companion object{
        const val DURATION = 4000
        const val DURATION2 = 400
    }
}