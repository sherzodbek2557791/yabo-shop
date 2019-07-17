package uz.kvikk.yabo.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import uz.kvikk.yabo.model.model.PersonalInfo
import uz.kvikk.yabo.service.BotService
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Service
class BotServiceImpl(
        @Value("\${bot.channel}") val CHANNEL: String,
        @Value("\${bot.username}") val BOT_USERNAME: String,
        @Value("\${bot.apitoken}") val API_TOKEN: String
) : BotService, TelegramLongPollingBot() {


    override fun sendOrder(message: String): Message? {
        return execute(SendMessage().apply {
            chatId = CHANNEL
            text = message
        })
    }

    override fun sendOrderWithPersonalInfo(message: String, payer: PersonalInfo?, guarantor: PersonalInfo?) {
        if (payer != null && guarantor != null) {
            execute(SendMessage().apply {
                setParseMode("HTML")
                chatId = CHANNEL
                text = """<a href="${payer.passportFrontUrl}">&#8203;</a>
<pre>$message</pre>
<code>Payer info ------- :</code> 
<a href="${payer.passportFrontUrl}">1) Пасспорт лицевая сторона</a>
<a href="${payer.passportBackUrl}">2) Пасспорт прописка</a> 
<a href="${payer.salaryReportUrl}">3) Справка о доходах</a>
<code>Guarantor info ------- :</code>
<a href="${guarantor.passportFrontUrl}">1) Пасспорт лицевая сторона</a>
<a href="${guarantor.passportBackUrl}">2) Пасспорт прописка</a> 
<a href="${guarantor.salaryReportUrl}">3) Справка о доходах</a>""".trimIndent()
            })
        } else {
            execute(SendMessage().apply {
                setParseMode("HTML")
                chatId = CHANNEL
                text = "<pre>$message</pre>"
            })
        }
    }

    override fun getBotUsername(): String = BOT_USERNAME

    override fun getBotToken(): String = API_TOKEN

    override fun onUpdateReceived(update: Update?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @PostConstruct
    fun init() {
        sendOrder("""
            Bot started!
            You can use this bot and channel!
        """.trimIndent())
    }

    @PreDestroy
    fun destroy() {
        sendOrder("""
            The Bot is stopped!
        """.trimIndent())
    }


}