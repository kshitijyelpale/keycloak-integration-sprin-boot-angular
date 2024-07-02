package org.example.testflyway.config

import org.example.testflyway.jwt.CustomJwt
import org.example.testflyway.jwt.CustomJwtConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun security(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { Customizer.withDefaults<Any>() }
            .authorizeHttpRequests { it.anyRequest().authenticated() }
            .oauth2ResourceServer {
                it.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(customJwtConverter())
                }
            }
        return http.build()
    }

    private fun customJwtConverter(): Converter<Jwt, CustomJwt> {
        return CustomJwtConverter()
    }
}
