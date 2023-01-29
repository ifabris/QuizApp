package hr.algebra.quizapp

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import hr.algebra.quizapp.api.QuizAppFetcher

private const val JOB_ID = 1

class QuizAppService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        QuizAppFetcher(this).fetchItems()

    }
    companion object { // convenience
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, QuizAppService::class.java, JOB_ID, intent)
        }
    }
}