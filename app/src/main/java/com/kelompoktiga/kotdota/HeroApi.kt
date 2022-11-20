package com.kelompoktiga.kotdota

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class HeroApi {
    private fun getRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.opendota.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getHeroStatsService(): HeroStats {
        return getRetrofit().create(HeroStats::class.java)
    }

    interface HeroStats {
        @GET("heroStats")
        fun getHeroStats(): Call<List<HeroStatsItem>>
    }
}
