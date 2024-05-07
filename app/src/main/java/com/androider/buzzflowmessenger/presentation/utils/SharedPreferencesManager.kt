package com.androider.buzzflowmessenger.presentation.utils

import android.content.Context

class SharedPreferencesManager(context: Context) {

    private val prefs = context.getSharedPreferences(
        "com.androider.buzzflowmessenger.PREFS",
        Context.MODE_PRIVATE
    )

    fun saveCurrentUserID(value: String) {
        with(prefs.edit()) {
            putString(CURRENT_USER_ID_KEY, value)
            apply()
        }
    }

    fun getCurrentUserID(): String? {
        return prefs.getString(CURRENT_USER_ID_KEY, CURRENT_USER_ID_DEFAULT_VALUE)
    }

    fun saveFinishedViewPagerContainerState(key: String, value: Boolean) {
        with(prefs.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    fun getFinishedViewPagerContainerState(key: String, defaultValue: Boolean): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    companion object{
        private const val CURRENT_USER_ID_KEY = "CurrentUserID"
        private const val CURRENT_USER_ID_DEFAULT_VALUE = "defaultValue"
    }




}