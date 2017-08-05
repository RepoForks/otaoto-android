package co.otaoto.api

interface Api {
    suspend fun create(secret: String): CreateResult
    suspend fun show(slug: String, key: String): ShowResult
}

sealed class CreateResult
data class CreateSuccess(val slug: String, val key: String) : CreateResult()
object CreateError : CreateResult()

sealed class ShowResult
data class ShowSuccess(val plainText: String) : ShowResult()
data class ShowError(val error: String) : ShowResult()
