package dev.inmo.tgbotapi.extensions.steps.triggers_handling


import dev.inmo.micro_utils.coroutines.safelyWithoutExceptions
import dev.inmo.micro_utils.coroutines.subscribeSafelyWithoutExceptions
import dev.inmo.tgbotapi.extensions.steps.BehaviourContext
import dev.inmo.tgbotapi.extensions.steps.BehaviourContextAndTypeReceiver
import dev.inmo.tgbotapi.extensions.steps.expectations.expectFlow
import dev.inmo.tgbotapi.extensions.utils.asContentMessage
import dev.inmo.tgbotapi.extensions.utils.asMessageUpdate
import dev.inmo.tgbotapi.extensions.utils.extensions.sourceChat
import dev.inmo.tgbotapi.types.files.abstracts.TelegramMediaFile
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import dev.inmo.tgbotapi.types.message.content.*
import dev.inmo.tgbotapi.types.message.content.abstracts.*
import dev.inmo.tgbotapi.types.message.content.media.*
import dev.inmo.tgbotapi.types.message.payments.InvoiceContent
import dev.inmo.tgbotapi.updateshandlers.FlowsUpdatesFilter
import kotlinx.coroutines.flow.filter


internal suspend inline fun <reified T : MessageContent> BehaviourContext.onContent(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    noinline additionalFilter: (suspend (ContentMessage<T>) -> Boolean)? = null,
    noinline scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<T>>
) = flowsUpdatesFilter.expectFlow(bot) {
    it.asMessageUpdate() ?.data ?.asContentMessage() ?.let { message ->
        if (message.content is T) {
            val adaptedMessage = message as ContentMessage<T>
            if (additionalFilter == null || additionalFilter(adaptedMessage)) adaptedMessage else null
        } else {
            null
        }
    }
}.subscribeSafelyWithoutExceptions(scope) { triggerMessage ->
    val (jobToCancel, scenario) = if (includeFilterByChatInBehaviourSubContext) {
        val subFilter = FlowsUpdatesFilter()
        val subBehaviourContext = copy(flowsUpdatesFilter = subFilter)

        flowsUpdatesFilter.allUpdatesFlow.filter {
            val chat = it.sourceChat() ?: return@filter false
            chat.id.chatId == triggerMessage.chat.id.chatId
        }.subscribeSafelyWithoutExceptions(scope, subFilter.asUpdateReceiver) to subBehaviourContext
    } else {
        null to this
    }
    safelyWithoutExceptions { scenario.scenarioReceiver(triggerMessage) }
    jobToCancel ?.cancel()
}

suspend fun BehaviourContext.onContact(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<ContactContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<ContactContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onDice(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<DiceContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<DiceContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onGame(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<GameContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<GameContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onLocation(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<LocationContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<LocationContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onPoll(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<PollContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<PollContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onText(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<TextContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<TextContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onVenue(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<VenueContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<VenueContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onAudioMediaGroup(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<AudioMediaGroupContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<AudioMediaGroupContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onDocumentMediaGroup(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<DocumentMediaGroupContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<DocumentMediaGroupContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onMediaCollection(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<MediaCollectionContent<TelegramMediaFile>>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<MediaCollectionContent<TelegramMediaFile>>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onMedia(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<MediaContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<MediaContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onMediaGroup(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<MediaGroupContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<MediaGroupContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onVisualMediaGroup(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<VisualMediaGroupContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<VisualMediaGroupContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onAnimation(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<AnimationContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<AnimationContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onAudio(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<AudioContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<AudioContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onDocument(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<DocumentContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<DocumentContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onPhoto(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<PhotoContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<PhotoContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onSticker(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<StickerContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<StickerContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onVideo(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<VideoContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<VideoContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onVideoNote(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<VideoNoteContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<VideoNoteContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onVoice(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<VoiceContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<VoiceContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
suspend fun BehaviourContext.onInvoice(
    includeFilterByChatInBehaviourSubContext: Boolean = true,
    additionalFilter: (suspend (ContentMessage<InvoiceContent>) -> Boolean)? = null,
    scenarioReceiver: BehaviourContextAndTypeReceiver<Unit, ContentMessage<InvoiceContent>>
) = onContent(includeFilterByChatInBehaviourSubContext, additionalFilter, scenarioReceiver)
