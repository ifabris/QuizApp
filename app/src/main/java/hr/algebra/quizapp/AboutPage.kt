package hr.algebra.quizapp

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about_page.*


class AboutPage : AppCompatActivity() {

    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_page)

        stop_music.setOnClickListener{
            mediaPlayer.stop()
        }

        start_music.setOnClickListener{
            mediaPlayer = MediaPlayer.create(this, R.raw.beartooth_point)
            mediaPlayer.start()
        }


    }

    public override fun onBackPressed() {
        mediaPlayer.stop()
        val intent = Intent(this,WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}