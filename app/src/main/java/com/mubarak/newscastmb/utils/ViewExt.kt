package com.mubarak.newscastmb.utils

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.material.appbar.MaterialToolbar

fun View.showSoftKeyboard(viewToFocus: View) {
    if (viewToFocus.requestFocus()) {
        val context = context ?: return
        val imm = ContextCompat.getSystemService(context, InputMethodManager::class.java)
        imm?.showSoftInput(viewToFocus, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun MaterialToolbar.onUpButtonClick() {
    setNavigationOnClickListener {
        findNavController().popBackStack()
    }
}
