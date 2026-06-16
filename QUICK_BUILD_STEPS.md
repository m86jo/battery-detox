# 🎯 الخطوات السريعة لبناء APK

## ✅ تم بالفعل إنجازه:
- ✅ تم إعادة تنظيم المشروع بهيكل صحيح
- ✅ تم إنشاء `settings.gradle.kts` و `build.gradle.kts`
- ✅ تم إنشاء `AndroidManifest.xml` بشكل صحيح
- ✅ تم إنشاء جميع ملفات Kotlin الضرورية
- ✅ تم إنشاء ملفات الموارد (resources)
- ✅ تم إنشاء gradle wrapper scripts

## ⚙️ ما يلزم الآن:

### الخطوة 1️⃣ : تثبيت Android SDK
1. حمّل **Android Studio** من: https://developer.android.com/studio
2. أثناء التثبيت، اختر المكونات التالية:
   ```
   ✓ Android SDK
   ✓ Android SDK Platform 34
   ✓ Android SDK Build-Tools 34.x
   ✓ Android Emulator (اختياري)
   ```
3. أكمل التثبيت وأعد تشغيل الكمبيوتر

### الخطوة 2️⃣ : التحقق من التثبيت
```powershell
# افتح PowerShell واكتب:
java -version
echo $env:ANDROID_SDK_ROOT
```

يجب أن تري:
- ✅ Java 17+
- ✅ SDK path يشير إلى `C:\Users\Mohanad\AppData\Local\Android\Sdk`

### الخطوة 3️⃣ : بناء APK

انتظر قليلاً حتى ينتهي التحميل الأول (3-5 دقائق):

```powershell
cd D:\tst
.\gradlew.bat assembleDebug
```

أو استخدم البرنامج النصي:
```powershell
cd D:\tst
.\build-apk.ps1
```

## 📱 بعد البناء الناجح:

سيكون الملف هنا:
```
D:\tst\app\build\outputs\apk\debug\app-debug.apk
```

لتثبيت التطبيق على جهازك:
```powershell
# تأكد من توصيل جهازك
adb devices

# ثبّت APK
adb install "D:\tst\app\build\outputs\apk\debug\app-debug.apk"

# شغّل التطبيق
adb shell am start -n com.batterydetox/.MainActivity
```

## 🆘 إذا حدثت مشاكل:

| المشكلة | الحل |
|--------|------|
| `ANDROID_SDK_ROOT not set` | أعد تشغيل Terminal بعد تثبيت Android Studio |
| `Platform 34 not found` | افتح Android Studio > Tools > SDK Manager > ثبّت Platform 34 |
| `Build failed` | جرّب: `.\gradlew.bat clean assembleDebug` |
| `Java not found` | حمّل JDK من https://www.oracle.com/java/technologies/downloads/ |

## 📚 ملفات الدليل:
- 📖 `BUILD_GUIDE.md` - دليل شامل
- 📄 `build-apk.ps1` - برنامج بناء مباشر
- ⚙️ `gradlew.bat` - gradle wrapper لـ Windows

---

**الآن أنت جاهز! 🚀**

اتبع الخطوات أعلاه وسيكون لديك APK جاهز في غضون دقائق!
