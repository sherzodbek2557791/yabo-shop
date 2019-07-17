package uz.kvikk.yabo.service

import org.telegram.telegrambots.meta.api.objects.Message
import uz.kvikk.yabo.model.model.PersonalInfo

interface BotService{

    fun sendOrder(message: String): Message?

    fun sendOrderWithPersonalInfo(message: String, payer: PersonalInfo?, guarantor: PersonalInfo?)

}
