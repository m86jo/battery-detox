package com.batterydetox.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.batterydetox.receiver.BatteryReceiver

class BatteryMonitorService : Service() {

    private val batteryReceiver = BatteryReceiver()
    private val CHANNEL_ID = "BatteryMonitorChannel"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        
        // تشغيل الخدمة كـ Foreground Service لكي لا يقتلها النظام
        startForeground(1, createNotification())
        
        // تسجيل المستمع برمجياً (ديناميكياً)
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_CHANGED)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(batteryReceiver, filter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY // إعادة تشغيل الخدمة تلقائياً إذا حاول النظام إغلاقها بسبب الضغط
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryReceiver) // تنظيف الذاكرة
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "خدمة ديتوكس البطارية",
                NotificationManager.IMPORTANCE_LOW // Low لكي لا يصدر صوتاً مزعجاً طوال الوقت
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("تطبيق ديتوكس البطارية فعال")
            .setContentText("جاري مراقبة شحن هاتفك لحمايته")
            .setSmallIcon(android.R.drawable.ic_lock_idle_charging) // أيقونة مؤقتة
            .setOngoing(true) // لا يمكن للمستخدم سحب الإشعار لإغلاقه
            .build()
    }
}