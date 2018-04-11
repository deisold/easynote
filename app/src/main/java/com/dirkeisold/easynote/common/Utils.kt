package com.dirkeisold.easynote.common

import android.content.Context
import android.support.annotation.StringRes
import android.text.TextUtils
import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import java.util.*

/**
 * Created by dirkeisold on 12.03.16.
 *
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 *
 * All rights reserved.
 */
object Utils {

    fun getFormattedDate(context: Context, @StringRes stringResId: Int, date: Date?): String =
            if (date == null) ""
            else context.resources
                    .getString(stringResId, DateUtils.formatDateTime(context, date.time,
                            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_NUMERIC_DATE or DateUtils.FORMAT_SHOW_TIME))


    fun equals(s1: String?, s2: String?): Boolean {
        if (s1 == null && s2 == null) {
            return true
        }

        if (s1 != null && s2 == null || s1 == null) {
            return false
        } else {
            return s1 == s2
        }
    }
}

/**
 * High level utility extension functions
 */

/**
 * Adds null safe a value to a given list
 */
fun <T> MutableList<T>.addToList(value: T?): MutableList<T> {
    value?.let { add(value) }
    return this
}

inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: (A, B) -> R) {
    if (a != null && b != null) {
        code(a, b)
    }
}

inline fun <A, B, C, R> ifNotNull(a: A?, b: B?, c: C?, code: (A, B, C) -> R) {
    if (a != null && b != null && c != null) {
        code(a, b, c)
    }
}

fun <T> T?.or(default: T): T = if (this == null) default else this

fun <T> T?.or(compute: () -> T): T = this ?: compute()

fun String?.isEmptyNS(): Boolean = if (this == null) true else this.isEmpty()
fun Map<*, *>?.isEmptyNS(): Boolean = if (this == null) true else this.isEmpty()
fun Collection<*>?.isEmptyNS(): Boolean = if (this == null) true else this.isEmpty()

fun Collection<*>?.isNotEmptyNS(): Boolean = if (this == null) false else this.isNotEmpty()

fun TextView.setTextOrGoneIfNull(value: String?) {
    text = value
    if (value == null) visibility = View.GONE else visibility = View.VISIBLE
}

fun List<*>.joinNullSafe(delimiter: CharSequence): String? {
    return TextUtils.join(delimiter, this.asIterable())
}

