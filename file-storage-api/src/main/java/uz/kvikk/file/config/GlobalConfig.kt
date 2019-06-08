package uz.kvikk.file.config

import org.hashids.Hashids
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uz.kvikk.file.config.property.HashidProperties

@Configuration
class GlobalConfig(private val hashidProperties: HashidProperties) {

  @Bean
  fun hashids(): Hashids {
    val (salt, minLength, alphabet) = hashidProperties
    return Hashids(alphabet, minLength, salt)
  }

}
