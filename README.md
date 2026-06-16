# 📱 Battery Detox | ديتوكس البطارية

**"اشحن هاتفك، واشحن تركيزك!"** 🧘‍♂️🔋

**Battery Detox** ليس مجرد تطبيق تقليدي لمراقبة البطارية، بل هو أداة مبتكرة ومزدوجة الفائدة تهدف إلى **حماية صحة بطارية هاتفك** من التلف، وفي نفس الوقت توفير **فترات راحة إجبارية (ديتوكس رقمي)** لتعزيز صحتك النفسية وتقليل إدمان الشاشات.

## 💡 فكرة المشروع وكيف يعمل؟

يعتمد التطبيق على فلسفة "التقييد الإيجابي". استخدام الهاتف أثناء الشحن يرفع حرارة البطارية ويدمر عمرها الافتراضي. لذا، يتدخل التطبيق في الوقت المناسب:
1. **عقوبة الاستنزاف (عند 30%):** إذا انخفضت بطاريتك إلى 30%، يقوم التطبيق **بقفل شاشة الهاتف** بالكامل بواجهة مخصصة تمنعك من الاستخدام.
2. **التعافي والشحن (حتى 80%):** لن يُفتح القفل حتى تضع الهاتف على الشاحن وتصل النسبة إلى **80%** (وهي النسبة المثالية لصحة البطاريات).
3. **الاحتيال ممنوع:** حتى لو قمت بتوصيل الشاحن والهاتف فوق 30%، سيتم قفل الشاشة لمنعك من "العبث بالهاتف أثناء الشحن".
4. **نظام التلعيب ومخرج الطوارئ (Gamification):** في حالات الضرورة القصوى، يتوفر زر "مخرج طوارئ" لكسر القفل، ولكن مقابل **عقوبة خصم 50 نقطة** من رصيدك الرقمي! كما يوجد زر لإجراء مكالمات الطوارئ الفعلية مجاناً لضمان سلامتك.

## 🎯 الفوائد (لماذا هذا التطبيق؟)

- 🛡️ **إطالة عمر العتاد:** يمنع استنزاف البطارية للصفر، ويمنع الاستخدام أثناء الشحن (أكبر مسبب لانتفاخ وتلف البطاريات).
- 🧠 **الرفاهية الرقمية (Digital Wellbeing):** يجبرك على أخذ استراحة من التصفح (Scrolling) والعودة للعالم الحقيقي.
- 🚨 **تصميم يراعي السلامة:** رغم القفل الصارم، التطبيق لا يمنعك أبداً من إجراء مكالمات الطوارئ.

## ✨ المميزات التقنية

- 🔒 **واجهة قفل مخصصة (Kiosk-like UI):** تغطية الشاشة بالكامل بأمان تام باستخدام صلاحية `SYSTEM_ALERT_WINDOW`.
- 🚀 **خدمة خلفية مستقرة (Foreground Service):** مراقبة دقيقة ومستمرة مدعومة بنوع `specialUse` لتتوافق مع أحدث إصدارات أندرويد (Android 14+).
- ⚡ **عمل تلقائي عند الإقلاع:** تشغيل مستمع النظام `BootReceiver` ليتم تفعيل الحماية بمجرد تشغيل الهاتف.
- 💾 **قاعدة بيانات محلية:** إدارة وتخزين نقاط المستخدم ونظام العقوبات باستخدام `SharedPreferences`.

---

## 🛠️ المتطلبات

- **Java:** OpenJDK 17+
- **Android SDK:** Platform 34 (Android 14)
- **Gradle:** 8.1.4+

## 🏗️ بنية المشروع

```
BatteryDetox/
├── app/
│   ├── src/main/
│   │   ├── java/com/batterydetox/
│   │   │   ├── MainActivity.kt
│   │   │   ├── BatteryReceiver.kt
│   │   │   ├── BatteryMonitorService.kt
│   │   │   ├── BootReceiver.kt
│   │   │   └── LockScreenActivity.kt
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── values/
│   │   │   └── mipmap/
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── settings.gradle.kts
├── build.gradle.kts
└── gradle/wrapper/
```

## 🚀 البناء المحلي

### الخطوة 1: استنساخ المشروع
```bash
git clone https://github.com/yourusername/BatteryDetox.git
cd BatteryDetox
```

### الخطوة 2: تثبيت Android SDK
- حمّل [Android Studio](https://developer.android.com/studio)
- ثبّت `Android SDK Platform 34`
- ثبّت `Android SDK Build-Tools 34.x`

### الخطوة 3: البناء
```bash
./gradlew assembleDebug
```

سيتم إنشاء الملف في: `app/build/outputs/apk/debug/app-debug.apk`

## 🤖 البناء التلقائي مع GitHub Actions

**لا تحتاج لتثبيت Android SDK محلياً!**

عند كل push إلى المشروع:
1. ✅ يتم البناء تلقائياً
2. ✅ يتم اختبار التطبيق
3. ✅ يتم إنشاء ملف APK
4. ✅ يتم تحميل الملف كـ Release

### تحميل APK من GitHub:
```
الرابط: https://github.com/yourusername/BatteryDetox/releases
اختر Release الأخير → اضغط app-debug.apk
```

## 📱 التثبيت على الجهاز

### عبر ADB (Android Debug Bridge):
```bash
# تأكد من توصيل جهازك
adb devices

# ثبّت التطبيق
adb install app/build/outputs/apk/debug/app-debug.apk

# شغّل التطبيق
adb shell am start -n com.batterydetox/.MainActivity
```

### أو من GitHub Releases:
1. حمّل ملف APK من [الإصدارات](../../releases)
2. نقل الملف إلى جهازك
3. افتح الملف وثبّت

## 📋 معلومات التطبيق

| المعلومة | القيمة |
|---------|--------|
| **اسم الحزمة** | com.batterydetox |
| **الإصدار** | 1.0 |
| **رقم البناء** | 1 |
| **Min SDK** | 24 (Android 7.0) |
| **Target SDK** | 34 (Android 14) |
| **لغة البرمجة** | Kotlin |
| **أداة البناء** | Gradle 8.1.4 |

## 🔧 التطويـر

### فتح المشروع في Android Studio:
1. افتح Android Studio
2. اختر `File > Open`
3. اختر مجلد `BatteryDetox`
4. انتظر اكتمال التنزيل والمزامقة

### البناء والتشغيل:
```bash
# تطوير
./gradlew assembleDebug

# الإصدار النهائي (يحتاج keystore)
./gradlew assembleRelease

# الاختبار
./gradlew test

# التنظيف
./gradlew clean
```

## 🐛 استكشاف الأخطاء

### خطأ: Build failed
```bash
./gradlew clean assembleDebug
```

### خطأ: SDK not found
- تأكد من تثبيت Android SDK
- اضبط متغير `ANDROID_SDK_ROOT`

### خطأ: Java not found
- حمّل OpenJDK 17 أو أحدث

## 📝 الترخيص

MIT License - انظر ملف [LICENSE](LICENSE)

## 👤 المؤلف

تم تطويره بواسطة **Ahmed**

## 🙏 المساهمة

نرحب بمساهماتك! يرجى:
1. Fork المشروع
2. انشئ فرع جديد (`git checkout -b feature/MyFeature`)
3. commit التغييرات (`git commit -m 'Add MyFeature'`)
4. Push إلى الفرع (`git push origin feature/MyFeature`)
5. فتح Pull Request

## 📞 التواصل

- 📧 البريد: your.email@example.com
- 🐙 GitHub: [@yourusername](https://github.com/yourusername)

## 📚 المراجع

- [Android Developers](https://developer.android.com/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Gradle Build System](https://gradle.org/)

---

**تم آخر تحديث:** يونيو 2026 ✅
