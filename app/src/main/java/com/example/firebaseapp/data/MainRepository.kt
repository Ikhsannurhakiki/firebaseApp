package com.example.firebaseapp.data

//class MainRepository private constructor(
//    private val appExecutors: AppExecutors,
//    private val remoteDataSource: RemoteDataSource
//) : MainDataSource {
//
//    companion object {
//        @Volatile
//        private var instance: MainRepository? = null
//
//        fun getInstance(
//            appExecutors: AppExecutors,
//            remoteDataSource: RemoteDataSource
//        ): MainRepository =
//            instance ?: synchronized(this) {
//                instance ?: MainRepository(
//                    appExecutors,
//                    remoteDataSource
//                ).apply { instance = this }
//            }
//    }
//}