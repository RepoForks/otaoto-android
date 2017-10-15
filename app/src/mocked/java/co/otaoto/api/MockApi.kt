package co.otaoto.api

import kotlinx.coroutines.experimental.delay
import java.util.concurrent.TimeUnit

class MockApi : Api {
    companion object {
        private const val ERROR = "error"
        private const val SLUG = "three-word-slug"
        private const val KEY = "1234567890ABCDEF"
        private const val SECRET = "That's my secret, Captain."
    }

    override suspend fun create(secret: String): CreateResult {
        delay(1, TimeUnit.SECONDS)
        return when (secret) {
            ERROR -> CreateError
            else -> CreateSuccess(SLUG, KEY)
        }
    }

    override suspend fun show(slug: String, key: String): ShowResult {
        delay(1, TimeUnit.SECONDS)
        return when (slug) {
            ERROR -> ShowError(ERROR)
            else -> ShowSuccess(SECRET)
        }
    }
}
