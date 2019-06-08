package uz.kvikk.file.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer



@Configuration
@EnableWebMvc
class WebMvcConfig : WebMvcConfigurer {

  override fun addCorsMappings(registry: CorsRegistry) {
    registry.addMapping("/**").allowedOrigins("*").allowedMethods("*")
  }

  override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
    configurer.favorPathExtension(false)
  }
}
