package ch.obermuhlner.ammonites.user

import ch.obermuhlner.ammonites.jooq.Tables.CONFIRMATION_TOKENS
import ch.obermuhlner.ammonites.jooq.tables.pojos.ConfirmationTokens
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class ConfirmationTokenRepository(private val dsl: DSLContext) {

    fun saveToken(token: ConfirmationTokens) {
        dsl.insertInto(CONFIRMATION_TOKENS)
            .set(CONFIRMATION_TOKENS.TOKEN, token.token)
            .set(CONFIRMATION_TOKENS.USER_ID, token.userId)
            .set(CONFIRMATION_TOKENS.EXPIRY_DATE, token.expiryDate)
            .execute()
    }

    fun findByToken(token: String): ConfirmationTokens? {
        return dsl.selectFrom(CONFIRMATION_TOKENS)
            .where(CONFIRMATION_TOKENS.TOKEN.eq(token))
            .fetchOneInto(ConfirmationTokens::class.java)
    }

    fun deleteToken(id: Long) {
        dsl.deleteFrom(CONFIRMATION_TOKENS)
            .where(CONFIRMATION_TOKENS.ID.eq(id))
            .execute()
    }
}
