package uz.kvikk.file


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import uz.kvikk.file.config.property.StorageProperties

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties::class)
class Application

fun main(args: Array<String>) {
  runApplication<Application>(*args)
}


