package com.baharudindayat.storyapp

import com.baharudindayat.storyapp.data.remote.response.PostStoriesResponse
import com.baharudindayat.storyapp.data.remote.response.Story

object DummyData {
    fun generateDummyDataResponse():List<Story>{
        val items: MutableList<Story> = arrayListOf()
        for (i in 0..10){
            val story = Story(
                id = "$i",
                name = "hanuman",
                description = "hanuman $i",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1684247289450_9X9l92So.jpg",
                createdAt = "2023-05-16T14:28:09.451Z",
                lat = -7.8097133,
                lon = 110.3740317
            )
            items.add(story)
        }
        return items
    }
    fun getTokenDummy():String {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUZaSmlvQ1c3NE0tNm5zSUwiLCJpYXQiOjE2ODQyNDc2MjZ9.1sV_YLqOZyNBgdtKiIoQrFyHM20ImWOoNz9azchpOxQ"
    }
    fun generateDummyDataStory(): PostStoriesResponse {
        return PostStoriesResponse(
            false,
            "success"
        )
    }
}