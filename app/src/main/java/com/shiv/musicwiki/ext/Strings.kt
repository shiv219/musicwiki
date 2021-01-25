package com.shiv.musicwiki.ext

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.*
import java.util.regex.Pattern

fun String.sortByEnumNameToDisplayValue(): String {
    if (this.isEmpty()) return ""

    val hyphenRemovedValue = this.replace("_", " ")
    val split = hyphenRemovedValue.split(" ")
    var spitedLowerCaseString = ""
    split.forEach {
        spitedLowerCaseString += it.toLowerCase(Locale.getDefault()).capitalize() + " "
    }

    return spitedLowerCaseString.trim()
}


fun String.isValidEmail() =
    Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+").matcher(trim()).matches()

fun String.isValidPassword() = length > 5
fun String.isValidMobile() = length >4


fun String.toRequestBody(): RequestBody =
    toRequestBody("multipart/form-data".toMediaTypeOrNull())

fun String.isNumeric(): Boolean =
    when (this.toIntOrNull()) {
        null -> false
        else -> true
    }

fun String.getFilePartBody(apiKey: String): MultipartBody.Part {
    val bannerFile = File(this)
    val requestFile =
        bannerFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(
        apiKey,
        bannerFile.name,
        requestFile
    )
}


