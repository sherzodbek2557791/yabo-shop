package uz.kvikk.yabo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.telegram.telegrambots.ApiContextInitializer


@SpringBootApplication
@EnableConfigurationProperties
class Application

fun main(args: Array<String>){
    ApiContextInitializer.init()
    runApplication<Application>(*args)
}