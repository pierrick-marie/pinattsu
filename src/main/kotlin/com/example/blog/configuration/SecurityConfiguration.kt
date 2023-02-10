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


//@Configuration
//@EnableWebSecurity
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


}