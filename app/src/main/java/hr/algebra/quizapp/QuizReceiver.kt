package hr.algebra.quizapp

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager

class QuizReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED, true)
        context.startActivity<WelcomeActivity>()
    }

    fun Context.setBooleanPreference(key: String, value: Boolean) =
        PreferenceManager.getDefaultSharedPreferences(this)
            .edit()
            .putBoolean(key, value)
            .apply()

    inline fun<reified T: Activity> Context.startActivity() = startActivity(Intent(this, T::class.java))

}
