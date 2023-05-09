package com.baharudindayat.storyapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.baharudindayat.storyapp.R
import com.baharudindayat.storyapp.data.remote.response.Story
import com.baharudindayat.storyapp.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailStory()

    }
    private fun detailStory(){
        val story = intent.getParcelableExtra<Story>(EXTRA_STORY) as Story
        with(binding){
            tvUsername.text = story.name
            tvDescription.text = story.description
            Glide.with(this@DetailActivity)
                .load(story.photoUrl)
                .into(ivDetailImage)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}