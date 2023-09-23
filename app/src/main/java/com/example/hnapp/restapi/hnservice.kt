package com.example.hnapp.restapi

import com.example.hnapp.data.Story
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

val HNBaseURL = "https://hacker-news.firebaseio.com/v0/"

interface HNService {
    @GET("topstories.json")
    suspend fun getTopStories(): List<Int>


    @GET("item/{id}.json")
    suspend fun getStory(@Path("id") id: Int): Story
}

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(HNBaseURL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val hnService: HNService = retrofit.create(HNService::class.java);

suspend fun fetchTopStories(): List<Int> {
    return hnService.getTopStories()
}

suspend fun fetchStory(id: Int): Story {
    return hnService.getStory(id)
}

