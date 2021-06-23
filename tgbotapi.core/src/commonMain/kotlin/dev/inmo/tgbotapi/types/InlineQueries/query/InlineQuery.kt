package dev.inmo.tgbotapi.types.InlineQueries.query

import dev.inmo.tgbotapi.types.InlineQueryIdentifier
import dev.inmo.tgbotapi.types.User
import dev.inmo.tgbotapi.types.chat.ChatType

sealed interface InlineQuery {
    val id: InlineQueryIdentifier
    val from: User
    val query: String
    val offset: String
    val chatType: ChatType?
}
