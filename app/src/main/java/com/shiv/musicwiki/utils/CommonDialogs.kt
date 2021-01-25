package com.shiv.musicwiki.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shiv.musicwiki.databinding.ProgressBarBinding

object CommonDialogs {
    fun createLoadingDialog(context: Context): Dialog {
        val mProgressAndErrorView =
            ProgressBarBinding.inflate(LayoutInflater.from(context))
        val dialog = Dialog(context)
        dialog.setContentView(mProgressAndErrorView.root)
        dialog.setCancelable(false)
        dialog.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
            ViewGroup.LayoutParams.MATCH_PARENT
            ViewGroup.LayoutParams.MATCH_PARENT
        }
        return dialog
    }
}
