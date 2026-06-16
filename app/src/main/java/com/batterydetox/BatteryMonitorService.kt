package com.batterydetox

import android.app.Service
import android.content.Intent
import android.os.IBinder

class BatteryMonitorService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Monitor battery status
        return START_STICKY
    }
}
