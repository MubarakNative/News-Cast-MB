package com.mubarak.newscastmb.ui.settings

import android.os.Bundle
import androidx.preference.DropDownPreference
import androidx.preference.PreferenceFragmentCompat
import com.mubarak.newscastmb.MyApplication
import com.mubarak.newscastmb.R
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    private val application: MyApplication = MyApplication()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_settings, rootKey)

        val themePreference: DropDownPreference? = findPreference("theme")

        themePreference?.setOnPreferenceChangeListener { _, theme ->
            application.updateTheme(enumValueOf(theme as  String))
            true
        }
    }
}