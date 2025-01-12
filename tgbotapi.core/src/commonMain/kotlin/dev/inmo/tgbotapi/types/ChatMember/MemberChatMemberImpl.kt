package dev.inmo.tgbotapi.types.ChatMember

import dev.inmo.tgbotapi.types.*
import dev.inmo.tgbotapi.types.ChatMember.abstracts.MemberChatMember
import kotlinx.serialization.*

@Serializable
data class MemberChatMemberImpl(@SerialName(userField) override val user: User) : MemberChatMember {
    @SerialName(statusField)
    @Required
    private val type: String = "member"
}
