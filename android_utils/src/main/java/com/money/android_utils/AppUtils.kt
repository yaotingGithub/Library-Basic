package com.money.android_utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import java.util.*

@SuppressLint("HardwareIds")
public fun getUUID(context: Context): String {
    var uuid = ""
    if (uuid.isBlank()) {
        try {
            // 使用ANDROID_ID獲取的識別碼能透過恢復出廠設置、取得Root權限而修改
            uuid = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            if (uuid.isBlank()) {
                uuid = UUID.randomUUID().toString()
            }
        } catch (exception: Exception) {
            // 建立GUID
            uuid = UUID.randomUUID().toString()
        }
    }
    return uuid
}

public fun getVersion(context: Context): String {
    val manager = context.packageManager
    return try {
        val info = manager.getPackageInfo(context.packageName, 0)
        info.versionName
    } catch (exception: Exception) {
        ""
    }
}