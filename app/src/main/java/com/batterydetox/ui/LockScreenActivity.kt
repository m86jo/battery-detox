package com.batterydetox.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.batterydetox.data.ScoreManager
import com.batterydetox.receiver.BatteryReceiver

class LockScreenActivity : AppCompatActivity() {

    private var isPenaltyMode = false
    private lateinit var tvBatteryPct: TextView
    private lateinit var tvInstruction: TextView

    // مستمع لانتظار أمر الفتح (عندما تصل البطارية لـ 80%)
    private val unlockReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == BatteryReceiver.ACTION_UNLOCK_SCREEN) {
                Toast.makeText(this@LockScreenActivity, "تم الوصول لـ 80%، يمكنك استخدام هاتفك!", Toast.LENGTH_LONG).show()
                finish() // إغلاق شاشة القفل
            }
        }
    }

    // مستمع لتحديث النسبة المئوية على الشاشة
    private val batteryUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_BATTERY_CHANGED) {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                val batteryPct = (level * 100 / scale.toFloat()).toInt()
                tvBatteryPct.text = "$batteryPct%"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // جعل الشاشة كاملة وإخفاء شريط المهام وأزرار التنقل السفلية (Kiosk UI)
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        )
        setContentView(R.layout.activity_lock_screen)

        isPenaltyMode = intent.getBooleanExtra("IS_PENALTY_MODE", false)

        // ربط عناصر الواجهة
        tvBatteryPct = findViewById(R.id.tvBatteryPct)
        tvInstruction = findViewById(R.id.tvInstruction)
        val btnEmergencyCall = findViewById<Button>(R.id.btnEmergencyCall)
        val btnEmergencyExit = findViewById<Button>(R.id.btnEmergencyExit)

        if (isPenaltyMode) {
            tvInstruction.text = "البطارية منخفضة جداً! اترك الهاتف يشحن"
            tvInstruction.setTextColor(android.graphics.Color.parseColor("#FF5252"))
        }

        // برمجة زر الاتصال للطوارئ
        btnEmergencyCall.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            startActivity(dialIntent)
        }

        // برمجة مخرج الطوارئ (كسر القفل)
        btnEmergencyExit.setOnClickListener {
            // خصم النقاط كعقوبة
            val scoreManager = ScoreManager(this)
            scoreManager.deductPoints(50)
            
            Toast.makeText(this, "تم كسر القفل! تم خصم 50 نقطة 💔. نقاطك المتبقية: ${scoreManager.getScore()}", Toast.LENGTH_LONG).show()
            finish()
        }

        // تسجيل المستمع
        registerReceiver(unlockReceiver, IntentFilter(BatteryReceiver.ACTION_UNLOCK_SCREEN))
        registerReceiver(batteryUpdateReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    // تعطيل زر العودة (Back Button) لمنع المستخدم من الهروب
    override fun onBackPressed() {
        Toast.makeText(this, "عذراً! اترك الهاتف يشحن أو استخدم مخرج الطوارئ", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(unlockReceiver)
        unregisterReceiver(batteryUpdateReceiver)
    }
}