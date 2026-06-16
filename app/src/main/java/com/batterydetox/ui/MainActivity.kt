package com.batterydetox.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.batterydetox.data.ScoreManager
import com.batterydetox.service.BatteryMonitorService

class MainActivity : AppCompatActivity() {

    private val OVERLAY_PERMISSION_REQ_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        checkOverlayPermissionAndStart()
    }

    private fun checkOverlayPermissionAndStart() {
        // التحقق من صلاحية "الظهور فوق التطبيقات" لأندرويد 6.0 فما فوق
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "يرجى منح صلاحية 'الظهور فوق التطبيقات' لكي يعمل القفل", Toast.LENGTH_LONG).show()
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE)
        } else {
            startMonitorService()
        }
    }

    private fun startMonitorService() {
        val scoreManager = ScoreManager(this)
        val currentScore = scoreManager.getScore()

        // تشغيل خدمة المراقبة الدائمة
        val serviceIntent = Intent(this, BatteryMonitorService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }

        Toast.makeText(this, "تم تفعيل الحماية! 🛡️ نقاطك الحالية: $currentScore", Toast.LENGTH_LONG).show()
        finish() // نغلق الشاشة فوراً ليبقى التطبيق يعمل كخدمة خفية فقط
    }
}