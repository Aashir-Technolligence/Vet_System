package com.aash.vetsystem.Preferences

import android.content.Context
import android.content.SharedPreferences


class VetPreferences(context: Context) {
    private val MAOSharePreference: SharedPreferences
    private val prefsEditor: SharedPreferences.Editor
    private val PREFS = "Vet_PREF"

    //SharedPreferences name
    var phone: String?
        get() = MAOSharePreference.getString("phone", "")
        set(phone) {
            prefsEditor.putString("phone", phone).commit()
        }

    var password: String?
        get() = MAOSharePreference.getString("password", "")
        set(password) {
            prefsEditor.putString("password", password).commit()
        }

    var company: String?
        get() = MAOSharePreference.getString("company", "App name here")
        set(company) {
            prefsEditor.putString("company", company).commit()
        }

    var description: String?
        get() = MAOSharePreference.getString("description", "")
        set(description) {
            prefsEditor.putString("description", description).commit()
        }
    var image: String?
        get() = MAOSharePreference.getString("logo", "")
        set(image) {
            prefsEditor.putString("logo", image).commit()
        }

    var finger: Boolean?
        get() = MAOSharePreference.getBoolean("finger", false)
        set(finger) {
            if (finger != null) {
                prefsEditor.putBoolean("finger", finger).commit()
            }
        }

    init {
        MAOSharePreference = context.getSharedPreferences(
            PREFS, Context.MODE_PRIVATE
        )
        prefsEditor = MAOSharePreference.edit()
    }
}