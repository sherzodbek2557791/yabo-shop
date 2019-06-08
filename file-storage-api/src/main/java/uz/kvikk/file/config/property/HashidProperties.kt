package uz.kvikk.file.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties(prefix = "hashids")
data class HashidProperties( var salt: String,
                             var minLength: Int,
                             var alphabet: String) {
  constructor() : this("salt", 6, "abcdef0123456789")
}
