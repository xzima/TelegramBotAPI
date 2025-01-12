package dev.inmo.tgbotapi.types.CallbackQuery

import dev.inmo.tgbotapi.types.CallbackQueryIdentifier
import dev.inmo.tgbotapi.types.User
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import dev.inmo.tgbotapi.types.message.content.abstracts.MessageContent

data class MessageDataCallbackQuery(
    override val id: CallbackQueryIdentifier,
    override val from: User,
    override val chatInstance: String,
    override val message: ContentMessage<MessageContent>,
    override val data: String
) : DataCallbackQuery, MessageCallbackQuery
