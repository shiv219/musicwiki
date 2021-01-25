package com.shiv.musicwiki.base

import android.app.Dialog
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.shiv.musicwiki.base.model.AppError
import com.shiv.musicwiki.ext.hideSoftInput
import com.shiv.musicwiki.ext.stringRes
import com.shiv.musicwiki.utils.CommonDialogs


abstract class BaseFragment(id: Int) : Fragment(id), View.OnClickListener {

    private var loadingDialog: Dialog? = null
    private var networkErrorDialog: Dialog? = null

    override fun onClick(v: View?) {
        //empty overridden method
    }

    fun showLoading(isLoading: Boolean = true) {
        if (isLoading) {
            if (loadingDialog == null)
                loadingDialog = CommonDialogs.createLoadingDialog(requireContext())
            if (loadingDialog?.isShowing?.not()!!)
                loadingDialog?.show()
        } else {
            hideLoading()
        }
    }

    private fun hideLoading() {
        if (loadingDialog?.isShowing == true)
            loadingDialog?.cancel()
    }

    open fun onBackPressed() {
        hideSoftInput()
        requireActivity().onBackPressed()
    }

    fun onError(stringRes: AppError) {
        when (stringRes) {
            is AppError.ApiException.InvalidDataException -> {
                Toast.makeText(requireActivity(), stringRes.message, Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(requireActivity(), stringRes.stringRes(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}