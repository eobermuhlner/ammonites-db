package ch.obermuhlner.ammonites.translation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/translations")
class TranslationRestController(
    @Autowired private val messageSource: MessageSource,
    @Autowired private val messageKeys: Set<String>
) {
    @GetMapping
    fun getTranslations(@RequestHeader("Accept-Language", defaultValue = "en") acceptLanguageHeader: String): Map<String, String> {
        val locale = parseAcceptLanguageHeader(acceptLanguageHeader)
        val translations = messageKeys.associateWith { messageSource.getMessage(it, null, locale) }
        return translations
    }

    @GetMapping("/token")
    fun getTranslation(@RequestParam key: String, @RequestParam lng: String = "en"): String {
        return messageSource.getMessage(key, null, Locale.forLanguageTag(lng))
    }

    private fun parseAcceptLanguageHeader(header: String): Locale {
        val firstLanguageTag = header.split(",").firstOrNull()?.split(";")?.firstOrNull() ?: "en"
        return Locale.forLanguageTag(firstLanguageTag)
    }
}
