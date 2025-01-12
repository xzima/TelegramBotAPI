package dev.inmo.tgbotapi.types.message.content.abstracts

import dev.inmo.tgbotapi.types.message.content.*
import dev.inmo.tgbotapi.types.message.content.media.*
import dev.inmo.tgbotapi.types.message.payments.InvoiceContent
import dev.inmo.tgbotapi.utils.RiskFeature
import kotlinx.serialization.modules.*

interface MessageContent: ResendableContent {
    companion object {
        @RiskFeature("This serialization module can be changed in near releases")
        fun serializationModule(
            additionalBuilder: PolymorphicModuleBuilder<MessageContent>.() -> Unit = {}
        ) = SerializersModule {
            polymorphic(MessageContent::class) {

                subclass(ContactContent::class)
                subclass(VenueContent::class)
                subclass(PollContent::class)
                subclass(DiceContent::class)
                subclass(TextContent::class)

                subclass(LocationContent::class, LocationContentSerializer)

                subclass(PhotoContent::class)
                subclass(VideoContent::class)
                subclass(AudioContent::class)
                subclass(DocumentContent::class)

                subclass(VoiceContent::class)
                subclass(VideoNoteContent::class)
                subclass(AnimationContent::class)
                subclass(StickerContent::class)
                subclass(InvoiceContent::class)

                additionalBuilder()
            }
        }
    }
}
