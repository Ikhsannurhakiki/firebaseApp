//package com.example.firebaseapp.data.remote.services
//
//
//class ApiConfig {
//
//    private var retrofit: Retrofit? = null
//
//    private fun getApiService(): Retrofit {
//        if (retrofit == null) {
//            val loggingInterceptor =
//                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//            val client = OkHttpClient.Builder()
//                .addInterceptor(loggingInterceptor)
//                .build()
//
//            retrofit = Retrofit.Builder()
//                .baseUrl(BuildConfig.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build()
//            return retrofit as Retrofit
//        }
//        return retrofit as Retrofit
//    }
//
//    fun client() = getApiService().create(ApiService::class.java)
//}