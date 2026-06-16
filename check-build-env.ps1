#!/usr/bin/env powershell
# Check Battery Detox Build Environment

Write-Host "===== Build Environment Check =====" -ForegroundColor Cyan
Write-Host ""

# 1. فحص Java
Write-Host "1️⃣  فحص Java:" -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-Object -First 1
    if ($javaVersion -match "17|18|19|20|21") {
        Write-Host "   ✅ $javaVersion" -ForegroundColor Green
    } else {
        Write-Host "   ⚠️  الإصدار قديم: $javaVersion" -ForegroundColor Yellow
    }
} catch {
    Write-Host "   ❌ Java غير مثبت" -ForegroundColor Red
    Write-Host "      حمّل من: https://www.oracle.com/java/technologies/downloads/" -ForegroundColor Gray
}

Write-Host ""

# 2. فحص Android SDK
Write-Host "2️⃣  فحص Android SDK:" -ForegroundColor Yellow
$sdkPath = $env:ANDROID_SDK_ROOT
if ([string]::IsNullOrEmpty($sdkPath)) {
    $sdkPath = "C:\Users\Mohanad\AppData\Local\Android\Sdk"
}

if (Test-Path $sdkPath) {
    Write-Host "   ✅ مسار SDK: $sdkPath" -ForegroundColor Green
    
    # فحص Platform 34
    $platform34 = Test-Path "$sdkPath\platforms\android-34"
    if ($platform34) {
        Write-Host "   ✅ Android SDK Platform 34 موجود" -ForegroundColor Green
    } else {
        Write-Host "   ❌ Android SDK Platform 34 غير مثبت" -ForegroundColor Red
        Write-Host "      افتح Android Studio > Tools > SDK Manager > ثبّت Platform 34" -ForegroundColor Gray
    }
    
    # فحص Build Tools
    $buildTools = Get-ChildItem "$sdkPath\build-tools" -ErrorAction SilentlyContinue | Sort-Object { [version]$_.Name } -Descending | Select-Object -First 1
    if ($buildTools) {
        Write-Host "   ✅ Build Tools: $($buildTools.Name)" -ForegroundColor Green
    } else {
        Write-Host "   ❌ Build Tools غير مثبتة" -ForegroundColor Red
    }
} else {
    Write-Host "   ❌ Android SDK غير مثبت" -ForegroundColor Red
    Write-Host "      حمّل Android Studio من: https://developer.android.com/studio" -ForegroundColor Gray
}

Write-Host ""

# 3. فحص Gradle
Write-Host "3️⃣  فحص Gradle Wrapper:" -ForegroundColor Yellow
if (Test-Path "D:\tst\gradlew.bat") {
    Write-Host "   ✅ gradlew.bat موجود" -ForegroundColor Green
} else {
    Write-Host "   ❌ gradlew.bat غير موجود" -ForegroundColor Red
}

if (Test-Path "D:\tst\gradle\wrapper\gradle-wrapper.properties") {
    Write-Host "   ✅ gradle-wrapper.properties موجود" -ForegroundColor Green
} else {
    Write-Host "   ❌ gradle-wrapper.properties غير موجود" -ForegroundColor Red
}

Write-Host ""

# 4. فحص المشروع
Write-Host "4️⃣  فحص بنية المشروع:" -ForegroundColor Yellow
$projectFiles = @(
    @{name="settings.gradle.kts"; path="D:\tst\settings.gradle.kts"},
    @{name="build.gradle.kts (root)"; path="D:\tst\build.gradle.kts"},
    @{name="app/build.gradle.kts"; path="D:\tst\app\build.gradle.kts"},
    @{name="AndroidManifest.xml"; path="D:\tst\app\src\main\AndroidManifest.xml"},
    @{name="MainActivity.kt"; path="D:\tst\app\src\main\java\com\batterydetox\MainActivity.kt"}
)

foreach ($file in $projectFiles) {
    if (Test-Path $file.path) {
        Write-Host "   ✅ $($file.name)" -ForegroundColor Green
    } else {
        Write-Host "   ❌ $($file.name)" -ForegroundColor Red
    }
}

Write-Host ""

# ملخص
Write-Host "═════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host "📊 الملخص:" -ForegroundColor Yellow

$allChecks = @(
    (Test-Path $sdkPath),
    (Test-Path "$sdkPath\platforms\android-34"),
    (Test-Path "D:\tst\gradlew.bat"),
    (Test-Path "D:\tst\app\src\main\java\com\batterydetox\MainActivity.kt")
)

$passCount = ($allChecks | Where-Object { $_ -eq $true } | Measure-Object).Count
$totalCount = $allChecks.Count

Write-Host "النتيجة: $passCount / $totalCount متطلبات مكتملة" -ForegroundColor Cyan

if ($passCount -eq $totalCount) {
    Write-Host "✅ جميع المتطلبات موجودة! يمكنك البدء في البناء الآن!" -ForegroundColor Green
    Write-Host ""
    Write-Host "شغّل الأمر التالي:" -ForegroundColor Green
    Write-Host "   cd D:\tst" -ForegroundColor Yellow
    Write-Host "   .\gradlew.bat assembleDebug" -ForegroundColor Yellow
} else {
    Write-Host "⚠️  بعض المتطلبات غير مكتملة. اتبع التعليمات أعلاه." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "═════════════════════════════════════════════════════════" -ForegroundColor Cyan
