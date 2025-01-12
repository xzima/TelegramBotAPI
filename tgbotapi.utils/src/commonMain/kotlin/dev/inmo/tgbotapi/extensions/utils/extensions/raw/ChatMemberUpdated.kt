package dev.inmo.tgbotapi.extensions.utils.extensions.raw

import dev.inmo.tgbotapi.types.ChatInviteLink
import dev.inmo.tgbotapi.types.ChatMember.abstracts.ChatMember
import dev.inmo.tgbotapi.types.ChatMemberUpdated
import dev.inmo.tgbotapi.utils.RiskFeature

@RiskFeature(RawFieldsUsageWarning)
val ChatMemberUpdated.old_chat_member: ChatMember
    get() = oldChatMemberState
@RiskFeature(RawFieldsUsageWarning)
val ChatMemberUpdated.new_chat_member: ChatMember
    get() = newChatMemberState
@RiskFeature(RawFieldsUsageWarning)
val ChatMemberUpdated.invite_link: ChatInviteLink?
    get() = inviteLink
