package com.rzgonz.nutechwallet.core.utils

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import java.text.SimpleDateFormat

/**
 * Created by rzgonz on 12/02/23.
 *
 */
object TimeUtils {
    const val DATE_FROM_BE = "yyyy-mm-dd"
    const val DATE_IN_ID = "dd MMM yyyy"

    private const val DEFAULT_VALUE_DATE = "-"

    @SuppressLint("SimpleDateFormat")
    fun formatStringDate(inputDateStr: String?, inputFormat: String, outputFormat: String): String {
        if (inputDateStr == null || " return null;" == inputDateStr) {
            Log.e(TAG, "formatStringDate: cannot reformat Date, inputDateStr is null or empty")
            return DEFAULT_VALUE_DATE
        }

        val inFormat = SimpleDateFormat(inputFormat)
        val outFormat = SimpleDateFormat(outputFormat)
        return safeLaunchWithResult(inputDateStr.orEmpty()) {
            outFormat.format(inFormat.parse(inputDateStr)!!)
        }
    }
}