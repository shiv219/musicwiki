package com.shiv.musicwiki.ext

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.getSystemService
import androidx.core.content.res.use
import androidx.databinding.adapters.Converters
import androidx.fragment.app.Fragment

@ColorInt
fun Context.getThemeColor(
    @AttrRes themeAttrId: Int
): Int {
    return obtainStyledAttributes(intArrayOf(themeAttrId))
        .use {
            it.getColor(0, Color.MAGENTA)
        }
}

fun Context.getThemeColorDrawable(
    @AttrRes themeAttrId: Int
): Drawable {
    return obtainStyledAttributes(intArrayOf(themeAttrId))
        .use {
            it.getColor(0, Color.MAGENTA)
        }.let {
            Converters.convertColorToDrawable(it)
        }
}

fun Context.isNightMode(): Boolean {
    return resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}

fun Activity.hideSoftInput() {
    val imm: InputMethodManager? = getSystemService()
    val currentFocus = currentFocus
    if (currentFocus != null && imm != null) {
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun Fragment.hideSoftInput() = requireActivity().hideSoftInput()


fun View.toggleKeyboard(isShow: Boolean) {
    if (isShow) {
        if (this.requestFocus()) {
            val imm =
                this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    } else {
        val imm =
            this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

fun Context.showToast(message: Any) {
    when (message) {
        is String -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        is Int -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Fragment.showToast(message: Any?) {
    when (message) {
        is String -> {
            Toast.makeText(this.requireActivity(), message, Toast.LENGTH_SHORT).show()
        }
        is Int -> {
            Toast.makeText(this.requireActivity(), message, Toast.LENGTH_SHORT).show()
        }
    }
}
