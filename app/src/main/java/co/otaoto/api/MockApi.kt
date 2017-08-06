package co.otaoto.api

class MockApi : Api {
    companion object {
        const val ERROR = "error"
        const val SLUG = "three-word-slug"
        const val KEY = "1234567890ABCDEF"
        const val SECRET = "That's my secret, Captain."
    }

    override suspend fun create(secret: String): CreateResult = when (secret) {
        ERROR -> CreateError
        else -> CreateSuccess(SLUG, KEY)
    }

    override suspend fun show(slug: String, key: String): ShowResult = when (slug) {
        ERROR -> ShowError(ERROR)
        else -> ShowSuccess(SECRET)
    }
}
