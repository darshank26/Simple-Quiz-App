package com.darshankomu.quiz


class Question {

    private var mQuestion: String? = null
    private var mOption1: String? = null
    private var mOption2: String? = null
    private var mOption3: String? = null
    private var mRightAns: Int = 0

    constructor() {}

    constructor(mQuestion: String, mOption1: String, mOption2: String, mOption3: String, mRightAns: Int) {
        this.mQuestion = mQuestion
        this.mOption1 = mOption1
        this.mOption2 = mOption2
        this.mOption3 = mOption3
        this.mRightAns = mRightAns
    }

    fun getmQuestion(): String? {
        return mQuestion
    }

    fun setmQuestion(mQuestion: String) {
        this.mQuestion = mQuestion
    }

    fun getmOption1(): String? {
        return mOption1
    }

    fun setmOption1(mOption1: String) {
        this.mOption1 = mOption1
    }

    fun getmOption2(): String? {
        return mOption2
    }

    fun setmOption2(mOption2: String) {
        this.mOption2 = mOption2
    }

    fun getmOption3(): String? {
        return mOption3
    }

    fun setmOption3(mOption3: String) {
        this.mOption3 = mOption3
    }

    fun getmRightAns(): Int {
        return mRightAns
    }

    fun setmRightAns(mRightAns: Int) {
        this.mRightAns = mRightAns
    }
}
