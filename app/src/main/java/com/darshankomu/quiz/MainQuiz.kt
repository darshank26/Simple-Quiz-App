package com.darshankomu.quiz

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

import java.util.Collections
import java.util.Collections.shuffle
import java.util.Locale

class MainQuiz : AppCompatActivity() {

    private var txtQuestion: TextView? = null
    private var txtScore: TextView? = null
    private var txtQuestionCount: TextView? = null
    private var txtCounter: TextView? = null
    private var radioGroup: RadioGroup? = null
    private var r1: RadioButton? = null
    private var r2: RadioButton? = null
    private var r3: RadioButton? = null
    private var mSubmit: Button? = null

    private var colorStateList: ColorStateList? = null
    private var colorStateListCountDown: ColorStateList? = null
    private var countDownTimer: CountDownTimer? = null

    private var timeLeft: Long = 0

    private var questionSetsList: List<Question>?=null

    private var qCounter: Int = 0
    private var currQuestion: Question? = null
    private var qCountTotal: Int = 0

    private var score: Int = 0
    private var ans: Boolean = false

    private var onBackPressedTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_quiz)

        txtQuestion = findViewById(R.id.Question)
        txtScore = findViewById(R.id.Score)
        txtQuestionCount = findViewById(R.id.questionCount)
        txtCounter = findViewById(R.id.timeCounter)
        radioGroup = findViewById(R.id.radioGroup)
        r1 = findViewById(R.id.radioButton1)
        r2 = findViewById(R.id.radioButton2)
        r3 = findViewById(R.id.radioButton3)
        mSubmit = findViewById(R.id.submitButton)


        colorStateList = r1!!.textColors

        colorStateListCountDown = txtCounter!!.textColors

        val questionDb = DatabaseHelper(this)
        questionSetsList = questionDb.questionSet

        qCountTotal = questionSetsList!!.size

        shuffle(questionSetsList!!)

        showQuestion()

        mSubmit!!.setOnClickListener {
            if (!ans) {
                if (r1!!.isChecked || r2!!.isChecked || r3!!.isChecked) {
                    check()
                } else {
                    Toast.makeText(this@MainQuiz, "Select Ans First", Toast.LENGTH_SHORT).show()
                }
            } else {
                showQuestion()
            }
        }

    }


    private fun showQuestion() {

        r1!!.setTextColor(colorStateList)
        r2!!.setTextColor(colorStateList)
        r3!!.setTextColor(colorStateList)

        radioGroup!!.clearCheck()


        if (qCounter < qCountTotal) {
            currQuestion = questionSetsList!![qCounter]
            txtQuestion!!.text = currQuestion!!.getmQuestion()

            r1!!.text = currQuestion!!.getmOption1()
            r2!!.text = currQuestion!!.getmOption2()
            r3!!.text = currQuestion!!.getmOption3()

            qCounter++

            txtQuestionCount!!.text = "Question: $qCounter / $qCountTotal"

            ans = false

            mSubmit!!.text = "Confirm"

            timeLeft = COUNTDOWN_TIMER
            startCountDown()
        } else {
            finishQuizActivity()
        }

    }

    private fun startCountDown() {
        countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                updateCountDown()
            }

            override fun onFinish() {
                timeLeft = 0
                updateCountDown()
                check()

            }
        }.start()
    }

    private fun updateCountDown() {
        val min = (timeLeft / 1000).toInt() / 60
        val sec = (timeLeft / 1000).toInt() % 60

        val timeFormat = String.format(Locale.getDefault(), "%02d:%02d", min, sec)
        txtCounter!!.setText(timeFormat)

        if (timeLeft < 10000) {
            txtCounter!!.setTextColor(Color.RED)
        } else {
            txtCounter!!.setTextColor(colorStateListCountDown)
        }

    }


    private fun check() {
        ans = true

        countDownTimer!!.cancel()

        val radioSelected = findViewById<View>(radioGroup!!.checkedRadioButtonId) as RadioButton
        val answer = radioGroup!!.indexOfChild(radioSelected) + 1

        if (answer == currQuestion!!.getmRightAns()) {
            score++
            txtScore!!.text = "Score: $score"
        }

        showRightAns()

    }

    private fun showRightAns() {

        r1!!.setTextColor(Color.RED)
        r2!!.setTextColor(Color.RED)
        r3!!.setTextColor(Color.RED)

        when (currQuestion!!.getmRightAns()) {
            1 -> {
                r1!!.setTextColor(Color.GREEN)
                txtQuestion!!.text = "Answer 1 was Correct"
            }
            2 -> {
                r2!!.setTextColor(Color.GREEN)
                txtQuestion!!.text = "Answer 2 was Correct"
            }
            3 -> {
                r3!!.setTextColor(Color.GREEN)
                txtQuestion!!.text = "Answer 3 was Correct"
            }
        }

        if (qCounter < qCountTotal) {
            mSubmit!!.text = "Next"
        } else {
            mSubmit!!.text = "Finish"
        }

    }


    private fun finishQuizActivity() {
        val rIntent = Intent()
        rIntent.putExtra(FINAL_SCORE, score)
        setResult(Activity.RESULT_OK, rIntent)
        finish()
    }

    override fun onBackPressed() {

        if (onBackPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuizActivity()
        } else {
            Toast.makeText(this@MainQuiz, "Press Back Again", Toast.LENGTH_SHORT).show()
        }
        onBackPressedTime = System.currentTimeMillis()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
    }

    companion object {

        val FINAL_SCORE = "FinalScore"
        private val COUNTDOWN_TIMER: Long = 20000
    }
}
