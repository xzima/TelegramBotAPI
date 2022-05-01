package dev.inmo.tgbotapi.extensions.utils.types.files

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.requests.DownloadFileStream
import dev.inmo.tgbotapi.requests.abstracts.FileId
import dev.inmo.tgbotapi.requests.get.GetFile
import dev.inmo.tgbotapi.types.files.PathedFile
import dev.inmo.tgbotapi.types.files.TelegramMediaFile
import dev.inmo.tgbotapi.types.message.content.MediaContent
import dev.inmo.tgbotapi.utils.*

@Deprecated("StorageFile now is not necessary")
suspend fun convertToStorageFile(
    downloadStreamAllocator: ByteReadChannelAllocator,
    pathedFile: PathedFile
): StorageFile {
    return downloadStreamAllocator.asStorageFile(
        pathedFile.fileName
    )
}

@Deprecated("StorageFile now is not necessary")
suspend fun TelegramBot.convertToStorageFile(
    pathedFile: PathedFile
): StorageFile = convertToStorageFile(
    execute(DownloadFileStream(pathedFile.filePath)),
    pathedFile
)

@Deprecated("StorageFile now is not necessary")
suspend fun TelegramBot.convertToStorageFile(
    fileId: FileId
): StorageFile = convertToStorageFile(execute(GetFile(fileId)))

@Deprecated("StorageFile now is not necessary")
suspend fun TelegramBot.convertToStorageFile(
    file: TelegramMediaFile
): StorageFile = convertToStorageFile(file.fileId)

@Deprecated("StorageFile now is not necessary")
suspend fun TelegramBot.convertToStorageFile(
    content: MediaContent
): StorageFile = convertToStorageFile(content.media)