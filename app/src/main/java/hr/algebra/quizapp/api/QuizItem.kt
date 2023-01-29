package hr.algebra.quizapp.api

import com.google.gson.annotations.SerializedName

class QuizItem (
    @SerializedName("A") val A : String,
    @SerializedName("B") val B : String,
    @SerializedName("C") val C : String,
    @SerializedName("question") val question : String,
    @SerializedName("D") val D : String,
    @SerializedName("answer") val answer : String,
    @SerializedName("image") val image : String
)