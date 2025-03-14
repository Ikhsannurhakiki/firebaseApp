package com.example.firebaseapp.data

import com.ikhsannurhakiki.aplikasiforum.utils.AppExecutors

class MainRepository private constructor(
    private val appExecutors: AppExecutors
) : MainDataSource {

    companion object {
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance(
            appExecutors: AppExecutors
        ): MainRepository =
            instance ?: synchronized(this) {
                instance ?: MainRepository(
                    appExecutors
                ).apply { instance = this }
            }
    }
}