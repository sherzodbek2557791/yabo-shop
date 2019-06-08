package uz.kvikk.file.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "storage")
data class StorageProperties(var uploadDir: String, var uploadUrl:String) {
  constructor() : this("./", "http://localhost")
}
