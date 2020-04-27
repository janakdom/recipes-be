package cz.st52530.recipes.security

import cz.st52530.recipes.service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
        @Autowired
        private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
        @Autowired
        private val userService: IUserService,
        @Autowired
        private val jwtRequestFilter: JwtRequestFilter
) : WebSecurityConfigurerAdapter() {

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        val swaggerPaths = arrayOf(
                "/v3/api-docs/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/swagger"
        )
        // We don't need CSRF for this example
        httpSecurity.csrf().disable()
                .authorizeRequests()
                // dont authenticate this particular request
                .antMatchers(
                        "/api/authenticate",
                        *swaggerPaths
                ).permitAll()
                // all other requests need to be authenticated
                .anyRequest().authenticated().and().exceptionHandling()
                // make sure we use stateless session; session won't be used to store user's state.
                .authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}