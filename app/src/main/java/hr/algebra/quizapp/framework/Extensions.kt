package hr.algebra.quizapp.framework

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.preference.PreferenceManager
import hr.algebra.quizapp.PROVIDER_CONTENT_URI
import hr.algebra.quizapp.model.Item
    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"


fun Context.isOnline() : Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    if (network != null) {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        if (networkCapabilities != null) {
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
    }
    return false
}
fun Context.fetchItems() : MutableList<Item> {
    val items = mutableListOf<Item>()
    val cursor = contentResolver?.query(
        PROVIDER_CONTENT_URI,
        null, null, null, null)
    if (cursor != null) {
        while(cursor.moveToNext()) {
            items.add(
                Item(
                    cursor.getLong(cursor.getColumnIndex(Item::_id.name)),
                    cursor.getString(cursor.getColumnIndex(Item::A.name)),
                    cursor.getString(cursor.getColumnIndex(Item::B.name)),
                    cursor.getString(cursor.getColumnIndex(Item::C.name)),
                    cursor.getString(cursor.getColumnIndex(Item::question.name)),
                    cursor.getString(cursor.getColumnIndex(Item::D.name)),
                    cursor.getString(cursor.getColumnIndex(Item::image.name)),
                    cursor.getString(cursor.getColumnIndex(Item::answer.name)),
                )
            )
        }
    }

    return items
}

inline fun <reified T : Activity> Context.startActivity() =
    startActivity(Intent(this, T::class.java))

fun Context.getBooleanPreference(key: String) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .getBoolean(key, false)
