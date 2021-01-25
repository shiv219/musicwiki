package com.shiv.musicwiki.ext

import androidx.annotation.StringRes
import com.shiv.musicwiki.R
import com.shiv.musicwiki.base.model.AppError
import kotlinx.coroutines.TimeoutCancellationException
import org.json.JSONObject
import retrofit2.HttpException

fun Throwable?.toAppError(): AppError? {
    return when (this) {
        null -> null
        is AppError -> this
        is HttpException->{
            response()?.body()
            val errorMessage = ""
          AppError.ApiException.InvalidDataException(NetworkInvalidDataException(JSONObject(response()?.errorBody()?.string()?:"").getString("error")))
        }
        is TimeoutCancellationException -> AppError.ApiException.NetworkException(this)
        else -> AppError.UnknownException(this)
    }
}
@StringRes
fun AppError.stringRes() = when (this) {
    is AppError.ApiException.NetworkException -> R.string.check_internet_connection
//    is AppError.ApiException.ServerException -> R.string.error_server
//    is AppError.ApiException.SessionNotFoundException -> R.string.error_unknown
//    is AppError.ApiException.UnknownException -> R.string.error_unknown
//    is AppError.UnknownException -> R.string.error_unknown
    else -> R.string.something_went_wrong
}

class NetworkInvalidDataException(private val errorMessage:String):Throwable(errorMessage)