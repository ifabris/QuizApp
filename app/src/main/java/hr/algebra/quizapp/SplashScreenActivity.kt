package hr.algebra.quizapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.quizapp.framework.getBooleanPreference
import hr.algebra.quizapp.framework.isOnline
import hr.algebra.quizapp.framework.startActivity
import kotlinx.android.synthetic.main.activity_main.*


const val DATA_IMPORTED = "hr.algebra.quizapp.data_imported"
private const val DELAY: Long = 3000

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startAnimacija()
        redirect()
    }



    private fun redirect() {
        if (getBooleanPreference(DATA_IMPORTED)) {
            Handler(Looper.getMainLooper()).postDelayed(
                { startActivity<WelcomeActivity>() },
                DELAY
            )
        } else {
            if (isOnline()) {
                // start service
                Intent(this, QuizAppService::class.java).apply {
                    QuizAppService.enqueueWork(this@SplashScreenActivity, this)
                }
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.please_connect_to_the_internet),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun startAnimacija() {
        question_mark.animate().apply {
            duration = 10000
            rotationYBy(360f)
        }.start()
        please_wait.animate().apply {
            duration = 5000
            scaleXBy(0.5f)

        }.start()


    }


}