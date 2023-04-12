package com.noor.charginganimation.core.utils

import android.content.Context
import androidx.core.content.edit
import com.noor.charginganimation.App
import com.noor.charginganimation.core.Constants.APP_PREFS
import com.noor.charginganimation.core.Constants.IS_ALWAYS_ON
import com.noor.charginganimation.core.Constants.IS_FIRST_TIME
import com.noor.charginganimation.core.Constants.IS_SETUP_COMPLETE

object PrefUtils {
    private val sharedPreferences =
        App.getContext().getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)

    var isAlwaysOn
        get() = sharedPreferences.getBoolean(IS_ALWAYS_ON, false)
        set(value) = sharedPreferences.edit { putBoolean(IS_ALWAYS_ON, value) }

    var isSetupComplete
        get() = sharedPreferences.getBoolean(IS_SETUP_COMPLETE, false)
        set(value) = sharedPreferences.edit { putBoolean(IS_SETUP_COMPLETE, value) }

    var isFirstTime
        get() = sharedPreferences.getBoolean(IS_FIRST_TIME, false)
        set(value) = sharedPreferences.edit { putBoolean(IS_FIRST_TIME, value) }
}