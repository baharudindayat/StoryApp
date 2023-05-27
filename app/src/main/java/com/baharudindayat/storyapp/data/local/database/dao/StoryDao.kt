package com.baharudindayat.storyapp.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.baharudindayat.storyapp.data.remote.response.Story


@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<Story>)

    @Query("SELECT * FROM storyList")
    fun getAllStory(): PagingSource<Int, Story>

    @Query("DELETE FROM storyList")
    suspend fun deleteAll()
}