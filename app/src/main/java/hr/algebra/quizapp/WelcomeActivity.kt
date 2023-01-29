package hr.algebra.quizapp

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.quizapp.framework.USER_NAME
import kotlinx.android.synthetic.main.welcome.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)

        btn_start.setOnClickListener {
            if (et_username.text.toString().trim().isEmpty()) {
                Toast.makeText(this, getString(R.string.please_enter_username), Toast.LENGTH_SHORT)
                    .show()
            } else {
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra(USER_NAME, et_username.text.toString())
                startActivity(intent)
                finish()
            }
        }
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.about -> {
                    val intent = Intent(this, AboutPage::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.highscore -> {
                    val intent = Intent(this, FinalResultActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
            true
        }


    }

    override fun onBackPressed() {
        val pid = Process.myPid()
        Process.killProcess(pid)
    }

}