# 🔨 دليل بناء تطبيق Battery Detox APK

## ✅ المتطلبات المسبقة

### 1. Java Development Kit (JDK)
- ✅ OpenJDK 17 موجود على النظام
- التحقق: `java -version`

### 2. Android SDK
يجب تثبيت Android SDK مع:
- Android SDK Platform 34 (أندرويد 14)
- Android SDK Build-Tools 34.x
- Android Emulator (اختياري)

**التثبيت من Android Studio:**
```bash
# 1. حمّل Android Studio من: https://developer.android.com/studio
# 2. أثناء التثبيت، اختر "Custom" واختر:
#    - Android SDK
#    - Android SDK Platform 34
#    - Android SDK Build-Tools 34.x
```

### 3. متغيرات البيئة
تأكد من إضافة SDK path إلى متغيرات البيئة:
```
ANDROID_SDK_ROOT=C:\Users\Mohanad\AppData\Local\Android\Sdk
```

**الإضافة في Windows:**
1. اضغط `Win + X` واختر "System"
2. اختر "Advanced system settings"
3. اضغط "Environment Variables"
4. أضف متغير جديد:
   - اسم: `ANDROID_SDK_ROOT`
   - قيمة: `C:\Users\Mohanad\AppData\Local\Android\Sdk`

## 🚀 خطوات البناء

### الطريقة 1: استخدام PowerShell Script (موصى به)
```powershell
cd D:\tst
.\build-apk.ps1 -BuildType Debug
```

### الطريقة 2: استخدام Gradle Wrapper مباشرة
```bash
cd D:\tst
.\gradlew.bat assembleDebug
```

### الطريقة 3: من Android Studio
1. افتح المشروع من `D:\tst`
2. اختر `Build > Build Bundle(s) / APK(s) > Build APK(s)`
3. سيتم حفظ الملف في `app/build/outputs/apk/debug/`

## 📦 موقع ملف APK

بعد البناء الناجح، سيكون الملف في:
```
D:\tst\app\build\outputs\apk\debug\app-debug.apk
```

## 🔧 استكشاف الأخطاء

### خطأ: "ANDROID_SDK_ROOT not found"
- **الحل:** تأكد من إضافة متغير البيئة وأعد تشغيل Terminal

### خطأ: "Platform 34 not found"
- **الحل:** حمّل Android SDK Platform 34 من Android Studio:
  1. Tools > SDK Manager
  2. اختر "Android 14 (API 34)"
  3. اضغط "Apply"

### خطأ: "Java not found"
- **الحل:** تأكد من تثبيت JDK وإضافته إلى PATH
- التحقق: `java -version`

## 📱 تشغيل APK

بعد البناء، يمكنك:

### على جهاز فعلي:
```bash
adb install D:\tst\app\build\outputs\apk\debug\app-debug.apk
adb shell am start -n com.batterydetox/.MainActivity
```

### على محاكاة:
```bash
# ابدأ المحاكاة أولاً
emulator -avd Pixel_4_API_34

# ثم ثبّت:
adb install D:\tst\app\build\outputs\apk\debug\app-debug.apk
```

## 📋 معلومات المشروع

- **Package Name:** `com.batterydetox`
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Version:** 1.0
- **Build Tools:** 34.0.0

## 🎯 التطبيق يشمل:

1. **MainActivity** - الشاشة الرئيسية لعرض حالة البطارية
2. **BatteryMonitorService** - خدمة مراقبة البطارية
3. **BatteryReceiver** - استقبال تحديثات البطارية
4. **BootReceiver** - بدء الخدمة عند تشغيل الجهاز
5. **LockScreenActivity** - شاشة قفل مخصصة (اختياري)

## 💡 نصائح مفيدة

- للبناء الأسرع: `./gradlew assembleDebug --offline`
- لحذف البناء السابق: `./gradlew clean`
- لعرض معلومات التصحيح: `./gradlew assembleDebug --info`
- لبناء Release: `./gradlew assembleRelease` (يحتاج keystore)

---

**آخر تحديث:** يونيو 2026  
**الحالة:** ✅ جاهز للبناء بعد تثبيت Android SDK
