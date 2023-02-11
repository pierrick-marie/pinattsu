package com.example.blog.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

	@Bean
	@Throws(Exception::class)
	fun filterChain(http: HttpSecurity): SecurityFilterChain? {
		http
			.authorizeHttpRequests { request ->
				request
					.requestMatchers("/admin").hasRole("ADMIN")
					.requestMatchers("/", "/login", "/index.html").permitAll()
					.anyRequest().hasRole("USER")
			}
			.formLogin { login ->
				login
					.loginPage("/login")
					.defaultSuccessUrl("/")
					.failureUrl("/access-denied")
			}
			.logout { logout ->
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