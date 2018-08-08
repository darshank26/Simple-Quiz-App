package com.darshankomu.quiz

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

import java.util.ArrayList

import com.darshankomu.quiz.QuizContainer.QuizTable.*
import com.darshankomu.quiz.QuizContainer.QuizTable.Companion.ANS_COLUMN
import com.darshankomu.quiz.QuizContainer.QuizTable.Companion.OPTION1_COLUMN
import com.darshankomu.quiz.QuizContainer.QuizTable.Companion.OPTION2_COLUMN
import com.darshankomu.quiz.QuizContainer.QuizTable.Companion.OPTION3_COLUMN
import com.darshankomu.quiz.QuizContainer.QuizTable.Companion.QUESTION_COLUMN
import com.darshankomu.quiz.QuizContainer.QuizTable.Companion.QUESTION_TABLE_NAME


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private var db: SQLiteDatabase? = null

    val questionSet: List<Question>
        get() {

            val questionSetsList = ArrayList<Question>()

            db = readableDatabase



            val c = db!!.rawQuery("SELECT * FROM $QUESTION_TABLE_NAME", null)

            if (c.moveToFirst()) {
                do {
                    val question = Question()
                    question.setmQuestion(c.getString(c.getColumnIndex(QUESTION_COLUMN)))
                    question.setmOption1(c.getString(c.getColumnIndex(OPTION1_COLUMN)))
                    question.setmOption2(c.getString(c.getColumnIndex(OPTION2_COLUMN)))
                    question.setmOption3(c.getString(c.getColumnIndex(OPTION3_COLUMN)))
                    question.setmRightAns(c.getInt(c.getColumnIndex(ANS_COLUMN)))
                    questionSetsList.add(question)
                } while (c.moveToNext())

            }
            c.close()
            return questionSetsList
        }

    override fun onCreate(db: SQLiteDatabase) {
        this.db = db

        val QB_TABLE = "CREATE TABLE " +
                QUESTION_TABLE_NAME + " ( " +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QUESTION_COLUMN + " TEXT, " +
                OPTION1_COLUMN + " TEXT, " +
                OPTION2_COLUMN + " TEXT, " +
                OPTION3_COLUMN + " TEXT, " +
                ANS_COLUMN + " INTEGER " +
                " )"

        db.execSQL(QB_TABLE)

        GenerateQuestionFunction()


    }

    private fun GenerateQuestionFunction() {
        val q1 = Question("2+2", "1", "5", "4", 3)
        addQuestion(q1)
        val q2 = Question("What is Capital of India", "Mumbai", "New Delhi", "Chennai", 2)
        addQuestion(q2)
        val q3 = Question("The author of Harry Potter book is", "J.K Rowling", "Stephen King", "Toni Morrison", 1)
        addQuestion(q3)
        val q4 = Question("Which is fourth planet from sun?", "Mars", "Earth", "Venus", 1)
        addQuestion(q4)
        val q5 = Question("How many gram are there in a kilogram", "100", "1000", "10000", 2)
        addQuestion(q5)
    }

    private fun addQuestion(qb: Question) {
        val contentValues = ContentValues()
        contentValues.put(QUESTION_COLUMN, qb.getmQuestion())
        contentValues.put(OPTION1_COLUMN, qb.getmOption1())
        contentValues.put(OPTION2_COLUMN, qb.getmOption2())
        contentValues.put(OPTION3_COLUMN, qb.getmOption3())
        contentValues.put(ANS_COLUMN, qb.getmRightAns())
        db!!.insert(QUESTION_TABLE_NAME, null, contentValues)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {

        private val DATABASE_NAME = "QuizApp.db"
        private val DATABASE_VERSION = 1
    }

}
