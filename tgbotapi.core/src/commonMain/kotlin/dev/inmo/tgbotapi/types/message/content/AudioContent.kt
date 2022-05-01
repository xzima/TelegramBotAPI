package dev.inmo.tgbotapi.types.message.content

import dev.inmo.tgbotapi.requests.abstracts.Request
import dev.inmo.tgbotapi.requests.send.media.SendAudio
import dev.inmo.tgbotapi.types.ChatIdentifier
import dev.inmo.tgbotapi.types.media.TelegramMediaAudio
import dev.inmo.tgbotapi.types.media.toTelegramMediaAudio
import dev.inmo.tgbotapi.types.message.textsources.TextSourcesList
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.files.AudioFile
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import kotlinx.serialization.Serializable

@Serializable
data class AudioContent(
    override val media: AudioFile,
    override val text: String? = null,
    override val textSources: TextSourcesList = emptyList()
) : AudioMediaGroupContent {
    override fun createResend(
        chatId: ChatIdentifier,
        disableNotification: Boolean,
        protectContent: Boolean,
        replyToMessageId: MessageIdentifier?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: KeyboardMarkup?
    ): Request<ContentMessage<AudioContent>> = SendAudio(
        chatId,
        media.fileId,
        media.thumb ?.fileId,
        textSources,
        media.duration,
        media.performer,
        media.title,
        disableNotification,
        protectContent,
        replyToMessageId,
        allowSendingWithoutReply,
        replyMarkup
    )

    override fun toMediaGroupMemberTelegramMedia(): TelegramMediaAudio = asTelegramMedia()

    override fun asTelegramMedia(): TelegramMediaAudio = media.toTelegramMediaAudio(textSources)
}
