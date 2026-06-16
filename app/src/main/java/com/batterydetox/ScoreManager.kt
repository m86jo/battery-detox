package com.batterydetox

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ScoreManager : Parcelable {
    companion object {
        fun calculateBatteryScore(level: Int, isCharging: Boolean): Int {
            return if (isCharging) level + 10 else level
        }
    }
}
