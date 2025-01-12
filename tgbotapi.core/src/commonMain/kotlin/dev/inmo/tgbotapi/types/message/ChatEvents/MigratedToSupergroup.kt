package dev.inmo.tgbotapi.types.message.ChatEvents

import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.message.ChatEvents.abstracts.SupergroupEvent

/**
 * This event is sent when a group is converted to a supergroup.
 */
data class MigratedToSupergroup(
    val migratedFrom: ChatId
): SupergroupEvent
