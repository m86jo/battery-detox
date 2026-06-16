package com.batterydetox.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log
import com.batterydetox.ui.LockScreenActivity

class BatteryReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "BatteryReceiver"
        const val ACTION_LOCK_SCREEN = "com.batterydetox.LOCK_SCREEN"
        const val ACTION_UNLOCK_SCREEN = "com.batterydetox.UNLOCK_SCREEN"
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BATTERY_CHANGED -> handleBatteryChanged(context, intent)
            Intent.ACTION_POWER_CONNECTED -> handlePowerConnected(context)
            Intent.ACTION_POWER_DISCONNECTED -> handlePowerDisconnected(context)
        }
    }

    private fun handleBatteryChanged(context: Context, intent: Intent) {
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val batteryPct = level * 100 / scale.toFloat()

        Log.d(TAG, "مستوى البطارية الحالي: $batteryPct%")

        // السيناريو الثاني: عقوبة الـ 30%
        if (batteryPct <= 30.0f) {
            launchLockScreen(context, isPenaltyMode = true)
        } 
        // الوصول للهدف (80%) وفتح القفل
        else if (batteryPct >= 80.0f) {
            unlockScreen(context)
        }
    }

    private fun handlePowerConnected(context: Context) {
        Log.d(TAG, "تم توصيل الشاحن - جاري قفل الشاشة للحماية")
        // السيناريو الأول: قفل الشاشة أثناء الشحن لحماية البطارية
        launchLockScreen(context, isPenaltyMode = false)
    }

    private fun handlePowerDisconnected(context: Context) {
        Log.d(TAG, "تم فصل الشاحن - جاري التحقق من السماح بالفتح")
        // سيتم إضافة منطق التحقق هنا لضمان عدم الفصل إذا كان في وضع العقوبة
    }

    private fun launchLockScreen(context: Context, isPenaltyMode: Boolean) {
        val lockIntent = Intent(context, LockScreenActivity::class.java).apply {
            // هذه الأعلام (Flags) مهمة جداً لجعل الشاشة تفتح فوق كل شيء
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            putExtra("IS_PENALTY_MODE", isPenaltyMode)
        }
        context.startActivity(lockIntent)
    }

    private fun unlockScreen(context: Context) {
        val unlockIntent = Intent(ACTION_UNLOCK_SCREEN)
        context.sendBroadcast(unlockIntent)
    }
}