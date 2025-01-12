package dev.inmo.tgbotapi.webapps

import dev.inmo.micro_utils.crypto.CryptoJS
import dev.inmo.tgbotapi.utils.TelegramAPIUrlsKeeper

external class WebApp {
    val initData: String
    val initDataUnsafe: WebAppInitData

    @JsName("colorScheme")
    val colorSchemeRaw: String
    val themeParams: ThemeParams

    val isExpanded: Boolean
    val viewportHeight: Float
    val viewportStableHeight: Float

    @JsName("MainButton")
    val mainButton: MainButton

    internal fun onEvent(type: String, callback: () -> Unit)
    @JsName("onEvent")
    internal fun onEventWithBoolean(type: String, callback: (ViewportChangedData) -> Unit)

    fun offEvent(type: String, callback: () -> Unit)
    @JsName("offEvent")
    fun offEventWithBoolean(type: String, callback: (ViewportChangedData) -> Unit)

    fun sendData(data: String)

    fun ready()
    fun expand()
    fun close()
}

val WebApp.colorScheme: ColorScheme
    get() = when (colorSchemeRaw) {
        "light" -> ColorScheme.LIGHT
        "dark" -> ColorScheme.DARK
        else -> ColorScheme.LIGHT
    }

/**
 * @return The callback which should be used in case you want to turn off events handling
 */
fun WebApp.onEvent(type: EventType, eventHandler: EventHandler) = {
    eventHandler(js("this").unsafeCast<WebApp>())
}.also {
    onEvent(
        type.typeName,
        callback = it
    )
}

/**
 * @return The callback which should be used in case you want to turn off events handling
 */
fun WebApp.onEvent(type: EventType.ViewportChanged, eventHandler: ViewportChangedEventHandler) = { it: ViewportChangedData ->
    eventHandler(js("this").unsafeCast<WebApp>(), it)
}.also {
    onEventWithBoolean(
        type.typeName,
        callback = it
    )
}

/**
 * @return The callback which should be used in case you want to turn off events handling
 */
fun WebApp.onThemeChanged(eventHandler: EventHandler) = onEvent(EventType.ThemeChanged, eventHandler)
/**
 * @return The callback which should be used in case you want to turn off events handling
 */
fun WebApp.onMainButtonClicked(eventHandler: EventHandler) = onEvent(EventType.MainButtonClicked, eventHandler)
/**
 * @return The callback which should be used in case you want to turn off events handling
 */
fun WebApp.onViewportChanged(eventHandler: ViewportChangedEventHandler) = onEvent(EventType.ViewportChanged, eventHandler)

fun WebApp.isInitDataSafe(botToken: String) = TelegramAPIUrlsKeeper(botToken).checkWebAppLink(
    initData,
    initDataUnsafe.hash
)
