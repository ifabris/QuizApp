package hr.algebra.quizapp.api

import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import hr.algebra.quizapp.PROVIDER_CONTENT_URI
import hr.algebra.quizapp.QuizReceiver
import hr.algebra.quizapp.model.Item
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class QuizAppFetcher(private val context: Context) {
    private var quizApi: QuizApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        quizApi = retrofit.create(QuizApi::class.java)
    }

    fun fetchItems() {
        val request = quizApi.fetchItems()
        request.enqueue(object : Callback<List<QuizItem>> {
            override fun onResponse(
                call: Call<List<QuizItem>>,
                response: Response<List<QuizItem>>
            ) {
                if (response.body() != null) {
                    populateItems(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<QuizItem>>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
            }

        })
    }

    private fun populateItems(quizItem: List<QuizItem>) {
        GlobalScope.launch {
            quizItem.forEach {
               //  val picturePath = downloadImageAndStore(context, it.image, it.A.toString() )
                val values = ContentValues().apply {
                    put(Item::A.name, it.A)
                    put(Item::B.name, it.B)
                    put(Item::C.name, it.C)
                    put(Item::question.name, it.question)
                    put(Item::D.name, it.D)
                    put(Item::answer.name, it.answer)
                    put(Item::image.name, it.image)
                }
                context.contentResolver.insert(PROVIDER_CONTENT_URI, values)
            }
            context.sendBroadcast<QuizReceiver>()
        }

    }

    inline fun <reified T : BroadcastReceiver> Context.sendBroadcast() =
        sendBroadcast(Intent(this, T::class.java))

}