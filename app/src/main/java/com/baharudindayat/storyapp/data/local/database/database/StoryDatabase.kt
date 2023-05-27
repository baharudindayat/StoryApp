package com.baharudindayat.storyapp.data.local.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.baharudindayat.storyapp.data.local.database.entity.RemoteKeys
import com.baharudindayat.storyapp.data.local.database.dao.RemoteKeysDao
import com.baharudindayat.storyapp.data.local.database.dao.StoryDao
import com.baharudindayat.storyapp.data.remote.response.Story


@Database(
    entities = [Story::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase(){
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object{
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StoryDatabase {
            return INSTANCE ?: synchronized(StoryDatabase::class.java){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryDatabase::class.java, "storyApp_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                .also {
                    INSTANCE = it }
            }
        }
    }


}