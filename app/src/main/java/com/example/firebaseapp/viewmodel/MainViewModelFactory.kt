package com.example.firebaseapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseapp.data.MainRepository
import com.example.firebaseapp.data.injection.Injection

class MainViewModelFactory(val mainRepository: MainRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: MainViewModelFactory? = null

        fun getInstance(context: Context): MainViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: MainViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(mainRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}