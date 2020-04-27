package cz.st52530.recipes.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

const val SWAGGER_AUTH_KEY = "bearerAuth"

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        val scheme = SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        return OpenAPI()
                .components(Components().addSecuritySchemes(SWAGGER_AUTH_KEY, scheme))
    }
}