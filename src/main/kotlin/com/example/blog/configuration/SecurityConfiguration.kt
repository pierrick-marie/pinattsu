package com.example.blog.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
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

//	@Bean
//	fun filterChain(http: HttpSecurity): SecurityFilterChain {
//		http
//			.httpBasic()
//			.and()
//			.requiresChannel().anyRequest().requiresSecure()
//			.and()
//			.authorizeHttpRequests()
//			.requestMatchers("/").permitAll()
//			.requestMatchers("/authors").hasRole("USER")
//			.and()
//			.formLogin().loginPage("/login")
//
//		return http.build()
//	}

	@Bean
	fun filterChain(http: HttpSecurity): SecurityFilterChain {

		http
			.requiresChannel()
				.anyRequest()
				.requiresSecure()
				.and()
			.authorizeHttpRequests()
			.requestMatchers("/resources/**", "/access-denied.html", "/login*", "/")
				.permitAll()
			.requestMatchers("/authors", "/author/**", "/articles", "/article/**")
				.hasRole("USER")
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.and()
			.logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true) // Default behaviour
				.logoutRequestMatcher(AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.and()
			.exceptionHandling()
				.accessDeniedPage("/access-denied.html")

		return http.build()
	}

	@Bean
	fun userDetailsService(): UserDetailsService {

		val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
		val password = encoder.encode("password")
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