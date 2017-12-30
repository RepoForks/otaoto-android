package co.otaoto.api

import retrofit2.HttpException
import javax.inject.Inject

class ApiClient @Inject constructor(val api: OtaotoApi) {
    suspend fun create(secret: String): CreateResult {
        return try {
            val response = api.create(CreateRequest(CreateRequest.Secret(secret))).await()
            CreateSuccess(slug = response.secret.slug, key = response.secret.key)
        } catch (e: HttpException) {
            CreateError(e)
        } catch (e: Throwable) {
            CreateError(e)
        }
    }

    suspend fun show(slug: String, key: String): ShowResult {
        return try {
            val response = api.show(slug, key).await()
            response.plain_text?.let { ShowSuccess(it) } ?: ShowError(response.errors ?: "")
        } catch (e: HttpException) {
            ShowException(e)
        } catch (e: Throwable) {
            ShowException(e)
        }
    }
}

sealed class CreateResult
data class CreateSuccess(val slug: String, val key: String) : CreateResult()
data class CreateError(val exception: Throwable) : CreateResult()

sealed class ShowResult
data class ShowSuccess(val plainText: String) : ShowResult()
data class ShowError(val error: String) : ShowResult()
data class ShowException(val exception: Throwable) : ShowResult()
