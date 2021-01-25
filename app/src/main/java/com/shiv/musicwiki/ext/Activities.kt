package com.shiv.musicwiki.ext

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> ComponentActivity.assistedActivityViewModels(
    crossinline body: () -> T
): Lazy<T> {
    return viewModels {
        object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return body() as T
            }
        }
    }
}


inline fun <reified VM : ViewModel> AppCompatActivity.injectViewModels(mViewModelFactory: ViewModelProvider.Factory): Lazy<VM> =
    viewModels { mViewModelFactory }
