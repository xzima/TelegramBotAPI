package dev.inmo.tgbotapi.extensions.api.send

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.requests.send.SendDice
import dev.inmo.tgbotapi.types.ChatIdentifier
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.chat.abstracts.Chat
import dev.inmo.tgbotapi.types.dice.DiceAnimationType

suspend fun TelegramBot.sendDice(
    chatId: ChatIdentifier,
    animationType: DiceAnimationType? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: KeyboardMarkup? = null
) = execute(
    SendDice(chatId, animationType, disableNotification, replyToMessageId, allowSendingWithoutReply, replyMarkup)
)

suspend fun TelegramBot.sendDice(
    chat: Chat,
    animationType: DiceAnimationType? = null,
    disableNotification: Boolean = false,
    replyToMessageId: MessageIdentifier? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: KeyboardMarkup? = null
) = sendDice(chat.id, animationType, disableNotification, replyToMessageId, allowSendingWithoutReply, replyMarkup)
