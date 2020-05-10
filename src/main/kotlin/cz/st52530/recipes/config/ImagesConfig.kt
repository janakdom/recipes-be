package cz.st52530.recipes.config

import com.cloudinary.Cloudinary
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ImagesConfig(
        @Value("\${cloudinary.cloud_name}")
        private val cloudName: String,
        @Value("\${cloudinary.api_key}")
        private val apiKey: String,
        @Value("\${cloudinary.api_secret}")
        private val apiSecret: String
) {

    @Bean
    fun provideCloudinary(): Cloudinary {
        val config = mapOf(
                "cloud_name" to cloudName,
                "api_key" to apiKey,
                "api_secret" to apiSecret
        )
        return Cloudinary(config)
    }
}