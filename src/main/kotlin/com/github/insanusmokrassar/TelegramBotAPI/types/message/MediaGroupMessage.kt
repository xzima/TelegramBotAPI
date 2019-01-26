package com.github.insanusmokrassar.TelegramBotAPI.types.message

import com.github.insanusmokrassar.TelegramBotAPI.types.MediaGroupIdentifier
import com.github.insanusmokrassar.TelegramBotAPI.types.MessageIdentifier
import com.github.insanusmokrassar.TelegramBotAPI.types.chat.Chat
import com.github.insanusmokrassar.TelegramBotAPI.types.message.abstracts.MediaGroupMessage
import com.github.insanusmokrassar.TelegramBotAPI.types.message.abstracts.Message
import com.github.insanusmokrassar.TelegramBotAPI.types.message.content.abstracts.MediaGroupContent
import org.joda.time.DateTime

data class MediaGroupMessage(
    override val messageId: MessageIdentifier,
    override val chat: Chat,
    override val date: DateTime,
    override val mediaGroupId: MediaGroupIdentifier,
    override val content: MediaGroupContent
) : Message, MediaGroupMessage