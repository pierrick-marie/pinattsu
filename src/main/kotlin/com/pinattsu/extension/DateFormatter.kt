package com.pinattsu.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.util.*

fun LocalDateTime.format(): String = this.format(englishDateFormatter)

private val englishDateFormatter = DateTimeFormatterBuilder()
	.appendPattern("yyyy-MM-dd")
	.toFormatter(Locale.ENGLISH)