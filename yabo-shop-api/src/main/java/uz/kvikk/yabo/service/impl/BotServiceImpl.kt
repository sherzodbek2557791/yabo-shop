package uz.kvikk.yabo.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import uz.kvikk.yabo.service.BotService
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Service
class BotServiceImpl(
        @Value("\${bot.channel}") val CHANNEL: String,
        @Value("\${bot.username}") val BOT_USERNAME: String,
        @Value("\${bot.apitoken}") val API_TOKEN: String
        ) : BotService, TelegramLongPollingBot() {


    override fun sendOrder(message: String) {
        execute(SendMessage().apply {
            chatId = CHANNEL
            text = message
        })
    }

    override fun getBotUsername(): String = BOT_USERNAME

    override fun getBotToken(): String = API_TOKEN

    override fun onUpdateReceived(update: Update?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @PostConstruct
    fun init(){
        sendOrder("""
            Bot started!
            You can use this bot and channel!
        """.trimIndent())
    }

    @PreDestroy
    fun destroy(){
        sendOrder("""
            The Bot is stopped!
        """.trimIndent())
    }


}