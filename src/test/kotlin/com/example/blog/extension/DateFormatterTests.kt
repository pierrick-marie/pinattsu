package com.example.blog.extension

import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class DateFormatterTests {

	@Test
	fun formatTest() {
		val date = LocalDateTime.now()

		val year = date.year
		var month = "${date.monthValue}";
		if (date.monthValue < 10) {
			month = "0${month}"
		}
		val day = date.dayOfMonth

		assertThat(date.format()).isEqualTo("${year}-${month}-${day}")
	}
}