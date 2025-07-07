package com.iptvplayer.streaming.security

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.security.MessageDigest
import java.util.*

class SecurityManager(private val context: Context) {
    
    companion object {
        private const val PREFS_NAME = "secure_prefs"
        private const val KEY_APP_PIN = "app_pin"
        private const val KEY_STEALTH_MODE = "stealth_mode"
        private const val KEY_FAKE_CRASH = "fake_crash_enabled"
        private const val KEY_ADULT_FILTER = "adult_filter_enabled"
        private const val FAKE_PACKAGE_NAME = "com.calculator.basic"
        private const val FAKE_APP_NAME = "Calculator"
    }
    
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    
    private val encryptedPrefs = EncryptedSharedPreferences.create(
        PREFS_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    // PIN Protection
    fun setPIN(pin: String) {
        val hashedPin = hashSHA256(pin)
        encryptedPrefs.edit().putString(KEY_APP_PIN, hashedPin).apply()
    }
    
    fun validatePIN(pin: String): Boolean {
        val storedHash = encryptedPrefs.getString(KEY_APP_PIN, null) ?: return false
        return hashSHA256(pin) == storedHash
    }
    
    fun hasPINSet(): Boolean {
        return encryptedPrefs.getString(KEY_APP_PIN, null) != null
    }
    
    fun removePIN() {
        encryptedPrefs.edit().remove(KEY_APP_PIN).apply()
    }
    
    // Stealth Mode
    fun enableStealthMode(enabled: Boolean) {
        encryptedPrefs.edit().putBoolean(KEY_STEALTH_MODE, enabled).apply()
    }
    
    fun isStealthModeEnabled(): Boolean {
        return encryptedPrefs.getBoolean(KEY_STEALTH_MODE, false)
    }
    
    // Fake Crash Protection
    fun enableFakeCrash(enabled: Boolean) {
        encryptedPrefs.edit().putBoolean(KEY_FAKE_CRASH, enabled).apply()
    }
    
    fun isFakeCrashEnabled(): Boolean {
        return encryptedPrefs.getBoolean(KEY_FAKE_CRASH, false)
    }
    
    // Adult Content Filter
    fun enableAdultFilter(enabled: Boolean) {
        encryptedPrefs.edit().putBoolean(KEY_ADULT_FILTER, enabled).apply()
    }
    
    fun isAdultFilterEnabled(): Boolean {
        return encryptedPrefs.getBoolean(KEY_ADULT_FILTER, true) // Default enabled
    }
    
    // Device Security Checks
    fun isDeviceSecure(): SecurityCheckResult {
        val checks = mutableListOf<String>()
        
        // Check if device is rooted
        if (isDeviceRooted()) {
            checks.add("Device is rooted")
        }
        
        // Check if USB debugging is enabled
        if (isUSBDebuggingEnabled()) {
            checks.add("USB debugging is enabled")
        }
        
        // Check if developer options are enabled
        if (isDeveloperOptionsEnabled()) {
            checks.add("Developer options are enabled")
        }
        
        // Check if running on emulator
        if (isRunningOnEmulator()) {
            checks.add("Running on emulator")
        }
        
        return SecurityCheckResult(
            isSecure = checks.isEmpty(),
            warnings = checks
        )
    }
    
    private fun isDeviceRooted(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("su")
            process.destroy()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    private fun isUSBDebuggingEnabled(): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.ADB_ENABLED,
            0
        ) == 1
    }
    
    private fun isDeveloperOptionsEnabled(): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
            0
        ) == 1
    }
    
    private fun isRunningOnEmulator(): Boolean {
        return (Build.FINGERPRINT.startsWith("generic") ||
                Build.FINGERPRINT.startsWith("unknown") ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK built for x86") ||
                Build.MANUFACTURER.contains("Genymotion") ||
                (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) ||
                "google_sdk" == Build.PRODUCT)
    }
    
    // App Disguise Functions
    fun getFakeAppInfo(): AppDisguiseInfo {
        return AppDisguiseInfo(
            packageName = FAKE_PACKAGE_NAME,
            appName = FAKE_APP_NAME,
            fakeIcon = android.R.drawable.ic_menu_agenda // Calculator icon
        )
    }
    
    fun shouldShowRealApp(): Boolean {
        return !isStealthModeEnabled()
    }
    
    // Utility Functions
    private fun hashSHA256(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
    
    // Anti-Tampering
    fun verifyAppIntegrity(): Boolean {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
            // Add your expected signature hash here
            true
        } catch (e: Exception) {
            false
        }
    }
    
    // Emergency Functions
    fun triggerEmergencyWipe() {
        // Clear all sensitive data
        encryptedPrefs.edit().clear().apply()
        
        // Clear app data if possible
        try {
            val packageManager = context.packageManager
            packageManager.clearApplicationUserData()
        } catch (e: Exception) {
            // Fallback: just clear preferences
        }
    }
    
    fun generatePanicCode(): String {
        return "IPTV_${System.currentTimeMillis().toString().takeLast(6)}"
    }
}

data class SecurityCheckResult(
    val isSecure: Boolean,
    val warnings: List<String>
)

data class AppDisguiseInfo(
    val packageName: String,
    val appName: String,
    val fakeIcon: Int
)
