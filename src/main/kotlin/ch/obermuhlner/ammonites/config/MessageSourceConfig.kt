package ch.obermuhlner.ammonites.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.util.*

@Configuration
class MessageSourceConfig {
    @Bean
    fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    @Bean
    fun messageKeys(): Set<String> {
        val resolver = PathMatchingResourcePatternResolver()
        val resources = resolver.getResources("classpath*:messages*.properties")
        val keys = mutableSetOf<String>()

        for (resource in resources) {
            resource.inputStream.use { inputStream ->
                val properties = Properties()
                properties.load(inputStream)
                keys.addAll(properties.stringPropertyNames())
            }
        }

        return keys
    }
}
