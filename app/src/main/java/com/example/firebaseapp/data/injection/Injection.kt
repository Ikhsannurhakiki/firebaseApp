package com.example.firebaseapp.data.injection

import android.content.Context
import com.example.firebaseapp.data.MainRepository
import com.ikhsannurhakiki.aplikasiforum.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MainRepository {

        val appExecutors = AppExecutors()

        return MainRepository.getInstance(appExecutors)
    }
}