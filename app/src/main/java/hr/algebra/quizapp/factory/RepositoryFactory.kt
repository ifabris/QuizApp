package hr.algebra.quizapp.factory

import android.content.Context
import hr.algebra.quizapp.dao.QuizSqlHelper

fun getQuizRepository(context: Context?) = QuizSqlHelper(context)
