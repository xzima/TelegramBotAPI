package dev.inmo.tgbotapi.types.CallbackQuery

import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import dev.inmo.tgbotapi.types.message.content.abstracts.MessageContent

sealed interface MessageCallbackQuery : CallbackQuery {
    val message: ContentMessage<MessageContent>
}
