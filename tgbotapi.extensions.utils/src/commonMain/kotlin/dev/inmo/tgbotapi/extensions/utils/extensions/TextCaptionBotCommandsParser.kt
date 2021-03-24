package dev.inmo.tgbotapi.extensions.utils.extensions

import dev.inmo.tgbotapi.CommonAbstracts.*
import dev.inmo.tgbotapi.types.MessageEntity.textsources.BotCommandTextSource
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import dev.inmo.tgbotapi.types.message.content.TextContent
import dev.inmo.tgbotapi.types.message.content.abstracts.MessageContent


val defaultArgsSeparator = Regex(" ")
/**
 * Parse commands and their args. Logic will find command, get all subsequent data as args until new command
 */
fun List<TextSource>.parseCommandsWithParams(
    argsSeparator: Regex = defaultArgsSeparator
): MutableMap<String, Array<String>> {
    val result = mutableMapOf<String, Array<String>>()
    var currentBotCommandSource: BotCommandTextSource? = null
    var currentArgs = ""
    fun includeCurrent() = currentBotCommandSource ?.let {
        result[it.command] = currentArgs.split(argsSeparator).toTypedArray()
        currentArgs = ""
    }
    for (textSource in this) {
        if (textSource is BotCommandTextSource) {
            includeCurrent()
            currentBotCommandSource = textSource
        } else {
            currentArgs += textSource.source
        }
    }
    includeCurrent()
    return result
}

/**
 * Parse commands and their args. Logic will find command, get all subsequent data as args until new command
 */
fun TextedInput.parseCommandsWithParams(
    argsSeparator: Regex = defaultArgsSeparator
) = textSources.parseCommandsWithParams(argsSeparator)

/**
 * Parse commands and their args. Logic will find command, get all subsequent data as args until new command
 */
fun TextedOutput.parseCommandsWithParams(
    argsSeparator: Regex = defaultArgsSeparator
) = entities ?.parseCommandsWithParams(argsSeparator) ?: emptyMap()

/**
 * Parse commands and their args. Logic will find command, get all subsequent data as args until new command
 */
fun CaptionedInput.parseCommandsWithParams(
    argsSeparator: Regex = defaultArgsSeparator
) = textSources.parseCommandsWithParams(argsSeparator)

fun ContentMessage<TextContent>.parseCommandsWithParams(
    argsSeparator: Regex = defaultArgsSeparator
) = content.parseCommandsWithParams(argsSeparator)

fun <T> ContentMessage<T>.parseCommandsWithParams(
    argsSeparator: Regex = defaultArgsSeparator
) where T : CaptionedInput, T : MessageContent = content.parseCommandsWithParams(argsSeparator)