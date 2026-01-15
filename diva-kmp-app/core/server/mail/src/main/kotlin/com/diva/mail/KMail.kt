package com.diva.mail

import com.resend.Resend
import com.resend.services.emails.model.CreateEmailOptions
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.tryResult
import kotlinx.html.BODY

class KMail(
    private val config: KMailConfig,
) {
    private val resend = Resend(config.apiKey)

    fun sendEmail(to: String, subject: String, html: String): DivaResult<Unit, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError("sendMail") }
        ) {
            val request = CreateEmailOptions.builder()
                .from(config.fromEmail)
                .to(listOf(to))
                .subject(subject)
                .html(html)
                .build()

            val response = resend.emails().send(request)
            response.id?.let {
                DivaResult.success(Unit)
            } ?: return@tryResult DivaResult.failure(
                DivaError.exception(Exception("No email ID returned"), "sendMail")
            )
        }
    }

    fun sendEmail(to: String, subject: String, block: BODY.() -> Unit): DivaResult<Unit, DivaError> {
        val html: String = buildEmailHTML(block)
        return sendEmail(to, subject, html)
    }
}
