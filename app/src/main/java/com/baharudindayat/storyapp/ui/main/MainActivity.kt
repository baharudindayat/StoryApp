package com.baharudindayat.storyapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baharudindayat.storyapp.R
import com.baharudindayat.storyapp.data.StoryResult
import com.baharudindayat.storyapp.data.local.preferences.User
import com.baharudindayat.storyapp.data.local.preferences.UserPreferences
import com.baharudindayat.storyapp.databinding.ActivityMainBinding
import com.baharudindayat.storyapp.ui.auth.LoginActivity
import com.baharudindayat.storyapp.ui.main.adapter.MainAdapter
import com.baharudindayat.storyapp.ui.main.viewmodel.MainViewModel
import com.baharudindayat.storyapp.ui.story.StoryActivity
import com.baharudindayat.storyapp.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreferences: UserPreferences
    private lateinit var mainAdapter: MainAdapter
    private lateinit var rvStory: RecyclerView
    private val factory = ViewModelFactory.getInstance(this)
    private val mainViewModel: MainViewModel by viewModels { factory }
    private var userModel: User = User()
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)
        userModel = userPreferences.getUser()
        token = userModel.token.toString()
        mainAdapter = MainAdapter()
        rvStory = binding.rvStory

        getStory(token)

        binding.fabAddStory.setOnClickListener{
            startActivity(Intent(this, StoryActivity::class.java))
        }
    }

    private fun getStory(auth: String) {
        rvStory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mainAdapter
        }
        mainViewModel.apply {
            showLoading(true)
            getStory(auth).observe(this@MainActivity){
                when(it){
                    is StoryResult.Success -> {
                        showLoading(false)
                        mainAdapter.setData(it.data.listStory)
                    }
                    is StoryResult.Error -> {
                        showLoading(false)
                    }
                    is StoryResult.Loading -> {
                        showLoading(true)
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
    private fun logout() {
        userModel.token = ""
        userPreferences.saveUser(userModel)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                logout()
                true
            }
            R.id.settings -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            else -> true
        }
    }

}