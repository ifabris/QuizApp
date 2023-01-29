package hr.algebra.quizapp.api

import retrofit2.Call
import retrofit2.http.GET

const val API_URL = "https://pastebin.com/"

interface QuizApi {
  //  @GET("raw/wHeLNziU")
   @GET("raw/ywptyegM")
    fun fetchItems(): Call<List<QuizItem>>
}