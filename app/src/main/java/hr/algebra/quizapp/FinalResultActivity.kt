package hr.algebra.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.quizapp.framework.CORRECT_ANSWERS
import hr.algebra.quizapp.framework.TOTAL_QUESTIONS
import hr.algebra.quizapp.framework.USER_NAME
import kotlinx.android.synthetic.main.activity_final_result.*

class FinalResultActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_result)

        val username = intent.getStringExtra(USER_NAME)
        tv_username.text = username

        val correctAnswers = intent.getIntExtra(CORRECT_ANSWERS, 0)

        val totalQuestions = intent.getIntExtra(TOTAL_QUESTIONS, 10)

        tv_score.text = "YOUR SCORE IS $correctAnswers / $totalQuestions"

        val preferences = getSharedPreferences("QUIZZ-ANDROID", MODE_PRIVATE)
        val highscore : Int = preferences.getInt("highscore", 0)
        if (highscore >= correctAnswers){
            tv_highscore.text= "Highscore: $highscore"
        }else{
            tv_highscore.text="Highscore : $correctAnswers"
            val editor:Editor = preferences.edit()
            editor.putInt("highscore", correctAnswers)
            editor.commit()
        }

        btn_finish.setOnClickListener{

            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    public override fun onBackPressed() {
        val intent = Intent(this,WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}