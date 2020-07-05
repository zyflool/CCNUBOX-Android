package com.muxixyz.android.iokit.preference

import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KProperty

// Android SharedPreferences helper class for Kotlin.
// Easy-to-use delegated properties, automatic database creation, and listening for property changes
// @Author davidwhitman
// https://gist.github.com/davidwhitman/b83e1744e8435a2c8cba262c1179f1a8
// Usage
// Wrapper class for SharedPreferences, leveraging the awesome delegate syntax in Kotlin.
//
// Example preferences definition:
//
// class UserPreferences : Preferences() {
//     var emailAccount by stringPref()
//     var allowAnonymousLogging by booleanPref()
// }
//
// Example usage:
//
// preferences.allowAnonymousLogging = false
// Example listener (not exactly the cleanest-looking but it works):
//
// preferences.addListener(object : Preferences.SharedPrefsListener {
//     override fun onSharedPrefChanged(property: KProperty<*>) {
//         if (UserPreferences::allowAnonymousLogging.name == property.name) {
//             initLogging()
//         }
//     }
// })
//
//
// Modified by @2BAB
//
// - Added forceCommit
//
// Ignore unused warning. This class needs to handle all data types, regardless of whether the method is used.
// Allow unchecked casts - we can blindly trust that data we read is the same type we saved it as..
@Suppress("UNCHECKED_CAST", "unused")
abstract class Preferences(private var context: Context, private val name: String? = null) {

    private val baseName = "ccnubox"

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(name ?: baseName + javaClass.canonicalName!!.replace(".", "_"), Context.MODE_PRIVATE)
    }

    private val listeners = mutableListOf<SharedPrefsListener>()

    abstract class PrefDelegate<T>(val prefKey: String?) {
        abstract operator fun getValue(thisRef: Any?, property: KProperty<*>): T
        abstract operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
    }

    interface SharedPrefsListener {
        fun onSharedPrefChanged(property: KProperty<*>)
    }

    fun addListener(sharedPrefsListener: SharedPrefsListener) {
        listeners.add(sharedPrefsListener)
    }

    fun removeListener(sharedPrefsListener: SharedPrefsListener) {
        listeners.remove(sharedPrefsListener)
    }

    fun clearListeners() = listeners.clear()

    enum class StorableType {
        String,
        Int,
        Float,
        Boolean,
        Long,
        StringSet
    }

    inner class GenericPrefDelegate<T>(
        prefKey: String? = null,
        private val defaultValue: T?,
        val type: StorableType,
        var forceCommit: Boolean? = false
    ) :
        PrefDelegate<T?>(prefKey) {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T? =
            when (type) {
                StorableType.String ->
                    prefs.getString(prefKey ?: property.name, defaultValue as String?) as T?
                StorableType.Int ->
                    prefs.getInt(prefKey ?: property.name, defaultValue as Int) as T?
                StorableType.Float ->
                    prefs.getFloat(prefKey ?: property.name, defaultValue as Float) as T?
                StorableType.Boolean ->
                    prefs.getBoolean(prefKey ?: property.name, defaultValue as Boolean) as T?
                StorableType.Long ->
                    prefs.getLong(prefKey ?: property.name, defaultValue as Long) as T?
                StorableType.StringSet ->
                    prefs.getStringSet(prefKey ?: property.name, defaultValue as Set<String>) as T?
            }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
            val forceCommitSafe = forceCommit ?: false
            when (type) {
                StorableType.String -> {
                    prefs.edit().putString(prefKey ?: property.name, value as String?).apply {
                        if (forceCommitSafe) commit() else apply()
                    }
                    onPrefChanged(property)
                }
                StorableType.Int -> {
                    prefs.edit().putInt(prefKey ?: property.name, value as Int).apply {
                        if (forceCommitSafe) commit() else apply()
                    }
                    onPrefChanged(property)
                }
                StorableType.Float -> {
                    prefs.edit().putFloat(prefKey ?: property.name, value as Float).apply {
                        if (forceCommitSafe) commit() else apply()
                    }
                    onPrefChanged(property)
                }
                StorableType.Boolean -> {
                    prefs.edit().putBoolean(prefKey ?: property.name, value as Boolean).apply {
                        if (forceCommitSafe) commit() else apply()
                    }
                    onPrefChanged(property)
                }
                StorableType.Long -> {
                    prefs.edit().putLong(prefKey ?: property.name, value as Long).apply {
                        if (forceCommitSafe) commit() else apply()
                    }
                    onPrefChanged(property)
                }
                StorableType.StringSet -> {
                    prefs.edit().putStringSet(prefKey ?: property.name, value as Set<String>)
                        .apply()
                    onPrefChanged(property)
                }
            }
        }

    }

    fun stringPref(prefKey: String? = null, defaultValue: String? = null, forceCommit: Boolean? = false) =
        GenericPrefDelegate(prefKey, defaultValue, StorableType.String, forceCommit)

    fun intPref(prefKey: String? = null, defaultValue: Int = 0, forceCommit: Boolean? = false) =
        GenericPrefDelegate(prefKey, defaultValue, StorableType.Int, forceCommit)

    fun floatPref(prefKey: String? = null, defaultValue: Float = 0f, forceCommit: Boolean? = false) =
        GenericPrefDelegate(prefKey, defaultValue, StorableType.Float, forceCommit)

    fun booleanPref(prefKey: String? = null, defaultValue: Boolean = false, forceCommit: Boolean? = false) =
        GenericPrefDelegate(prefKey, defaultValue, StorableType.Boolean, forceCommit)

    fun longPref(prefKey: String? = null, defaultValue: Long = 0L, forceCommit: Boolean? = false) =
        GenericPrefDelegate(prefKey, defaultValue, StorableType.Long, forceCommit)

    fun stringSetPref(prefKey: String? = null, defaultValue: Set<String> = HashSet(), forceCommit: Boolean? = false) =
        GenericPrefDelegate(prefKey, defaultValue, StorableType.StringSet, forceCommit)

    private fun onPrefChanged(property: KProperty<*>) {
        listeners.forEach { it.onSharedPrefChanged(property) }
    }
}