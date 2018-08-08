package com.darshankomu.quiz

import android.provider.BaseColumns


object QuizContainer {

    class QuizTable : BaseColumns {
        companion object {

            val QUESTION_TABLE_NAME = "quiz_question"
            val QUESTION_COLUMN = "question"
            val OPTION1_COLUMN = "option1"
            val OPTION2_COLUMN = "option2"
            val OPTION3_COLUMN = "option3"
            val ANS_COLUMN = "ans"
        }


    }


}
