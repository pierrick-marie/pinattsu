package com.example.blog.extension

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import org.assertj.core.api.Assertions.assertThat


@SpringBootTest
class DateFormatterTests {

	private val logger: Logger = LogManager.getLogger(DateFormatterTests::class.java)

	@Test
	fun formatTest() {
		val date = LocalDateTime.now()

		val year = date.year
		val month = date.monthValue
		val day = date.dayOfMonth

		assertThat(date.format()).isEqualTo("${year}-${month}-${day}")
	}
}