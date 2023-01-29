package hr.algebra.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import hr.algebra.quizapp.framework.CORRECT_ANSWERS
import hr.algebra.quizapp.framework.TOTAL_QUESTIONS
import hr.algebra.quizapp.framework.USER_NAME
import hr.algebra.quizapp.framework.fetchItems
import hr.algebra.quizapp.model.Item
import kotlinx.android.synthetic.main.activity_question.*


class QuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionList: MutableList<Item>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null
    private var mNumberOfQuestions = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        mUserName = intent.getStringExtra(USER_NAME)

        mQuestionList = fetchItems()
        setQuestion()

        tv_answer_a.setOnClickListener(this)
        tv_answer_b.setOnClickListener(this)
        tv_answer_c.setOnClickListener(this)
        tv_answer_d.setOnClickListener(this)

        btn_submit.setOnClickListener(this)
    }


    private fun setQuestion() {
        val question = mQuestionList!![mCurrentPosition - 1]

        defaultOptionsView()

        if (mCurrentPosition == mNumberOfQuestions) {
            btn_submit.text = getString(R.string.finish)
        } else {
            btn_submit.text = getString(R.string.submit)
        }

        tv_question.text = question.question
        tv_answer_a.text = question.A
        tv_answer_b.text = question.B
        tv_answer_c.text = question.C
        tv_answer_d.text = question.D

        Picasso.get().load(question.answer).error(R.drawable.background1).into(iv_image)

        progressBar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.max
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, tv_answer_a)
        options.add(1, tv_answer_b)
        options.add(2, tv_answer_c)
        options.add(3, tv_answer_d)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_answer_a -> {
                selectedOptionView(tv_answer_a, 1)
            }
            R.id.tv_answer_b -> {
                selectedOptionView(tv_answer_b, 2)
            }
            R.id.tv_answer_c -> {
                selectedOptionView(tv_answer_c, 3)
            }
            R.id.tv_answer_d -> {
                selectedOptionView(tv_answer_d, 4)
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition < 11 -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, FinalResultActivity::class.java)
                            intent.putExtra(USER_NAME, mUserName)
                            intent.putExtra(CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(TOTAL_QUESTIONS, mNumberOfQuestions)
                            startActivity(intent)
                        }
                    }
                } else {
                    val question = mQuestionList?.get(mCurrentPosition - 1)
                    val numbOfAnswer = getNumberOfAnswer(question!!.image)
                    if (numbOfAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(getNumber(question), R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionList!!.size) {
                        btn_submit.text = getString(R.string.finish)
                    } else {
                        btn_submit.text = getString(R.string.go_to_next_question)
                    }
                    mSelectedOptionPosition = 0

                }
            }
        }
    }

    private fun getNumberOfAnswer(image: String): Int {
        return when (image) {
            "A" -> {
                1
            }
            "B" -> {
                2
            }
            "C" -> {
                3
            }
            else -> {
                4
            }
        }
    }

    private fun getNumber(question: Item): Int {
        if (question.image == "A") {
            return 1
        } else if (question.image == "B") {
            return 2
        } else if (question.image == "C") {
            return 3
        } else {
            return 4
        }
    }


    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                tv_answer_a.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                tv_answer_b.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                tv_answer_c.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                tv_answer_d.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    public override fun onBackPressed() {
        val intent = Intent(this,WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}