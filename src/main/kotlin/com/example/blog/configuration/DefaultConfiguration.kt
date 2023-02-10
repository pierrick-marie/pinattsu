package com.example.blog.configuration

import com.example.blog.entity.Article
import com.example.blog.entity.Author
import com.example.blog.repository.ArticleRepository
import com.example.blog.repository.AuthorRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher


@Configuration
@EnableWebSecurity
class DefaultConfiguration {

	@Bean
	fun init(authorRepository: AuthorRepository, articleRepository: ArticleRepository) = ApplicationRunner {

		val pm1 =
			Author(
				login = "pm1",
				firstName = "pierrick",
				lastName = "marie",
				description = "A jedi!"
			)

		val pm2 =
			Author(
				login = "pm2",
				firstName = "peter",
				lastName = "m."
			)


		val art1 =
			Article(
				title = "My first article",
				content = "Lorem Ipsum",
				date = "2022-12-01",
				author = pm1,
			)

		val art2 =
			Article(
				title = "My second article",
				content = "Lorem Ipsum v2",
				author = pm1,
			)

		val art3 =
			Article(
				title = "The best article ever",
				content = "Lorem Ipsum POWA",
				author = pm2,
			)

		authorRepository.save(pm1)
		authorRepository.save(pm2)

		articleRepository.save(art1)
		articleRepository.save(art2)
		articleRepository.save(art3)
	}

	@Bean
	@Throws(Exception::class)
	fun filterChain(http: HttpSecurity): SecurityFilterChain? {
		http
			.authorizeHttpRequests {request ->
				request
					.requestMatchers("/articles/**").hasRole("USER")
					.anyRequest().permitAll()
			}
			.formLogin {login ->
				login
					.loginPage("/login")
					.defaultSuccessUrl("/")
			}
			.logout {logout ->
				logout
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.logoutRequestMatcher(AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/login?logout")
			}
			.exceptionHandling { exception ->
				exception
					.accessDeniedPage("/access-denied")
			}
			.httpBasic { }

		return http.build()
	}


	@Bean
	fun userDetailsService(): UserDetailsService {

		val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
		val password = encoder.encode("p")
		val manager = InMemoryUserDetailsManager()

		val visitor = User.withUsername("visitor")
			.password(password)
			.roles("VISITOR")
			.build()
		manager.createUser(visitor)

		val user = User.withUsername("user")
			.password(password)
			.roles("USER")
			.build()
		manager.createUser(user)

		val admin = User.withUsername("admin")
			.password(password)
			.roles("USER", "ADMIN")
			.build()
		manager.createUser(admin)

		return manager
	}
}