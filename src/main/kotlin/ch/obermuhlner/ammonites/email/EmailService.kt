package ch.obermuhlner.ammonites.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service


@Service
class EmailService(
    @Autowired private val mailSender: JavaMailSender
) {
    fun sendEmail(to: String?, subject: String?, text: String?) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.subject = subject
        message.text = text
        mailSender.send(message)
    }
}
