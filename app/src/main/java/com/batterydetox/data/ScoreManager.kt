package com.batterydetox.data

import android.content.Context
import android.content.SharedPreferences

class ScoreManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("DetoxPrefs", Context.MODE_PRIVATE)

    // جلب النقاط الحالية
    fun getScore(): Int {
        return prefs.getInt("SCORE", 100)
    }

    // خصم النقاط (عند كسر القفل)
    fun deductPoints(points: Int) {
        val currentScore = getScore()
        val newScore = if (currentScore - points < 0) 0 else currentScore - points
        prefs.edit().putInt("SCORE", newScore).apply()
    }
}