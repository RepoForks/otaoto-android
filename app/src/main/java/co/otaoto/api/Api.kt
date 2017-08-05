package co.otaoto.api

import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.gildor.coroutines.retrofit.await

object Api {
    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    private val api: OtaotoApi by lazy { retrofit.create(OtaotoApi::class.java) }

    suspend fun create(secret: String): CreateResult {
        try {
            val response = api.create(CreateRequest(CreateRequest.Secret(secret))).await()
            return CreateSuccess(slug = response.secret.slug, key = response.secret.key)
        } catch (e: HttpException) {
            e.printStackTrace()
            return CreateError
        } catch (e: Throwable) {
            e.printStackTrace()
            return CreateError
        }
    }

    suspend fun show(slug: String, key: String): ShowResult {
        try {
            val response = api.show(slug, key).await()
            return response.plain_text?.let { ShowSuccess(it) } ?: ShowError(response.errors ?: "")
        } catch (e: HttpException) {
            e.printStackTrace()
            return ShowError("")
        } catch (e: Throwable) {
            e.printStackTrace()
            return ShowError("")
        }
    }
}

sealed class CreateResult
data class CreateSuccess(val slug: String, val key: String) : CreateResult()
object CreateError : CreateResult()

sealed class ShowResult
data class ShowSuccess(val plainText: String) : ShowResult()
data class ShowError(val error: String) : ShowResult()

private val BASE_URL = "https://otaoto.co/api/"

private interface OtaotoApi {
    @POST("create")
    fun create(@Body body: CreateRequest): Call<CreateResponse>

    @GET("show/{slug}/{key}")
    fun show(@Query("slug") slug: String, @Query("key") key: String): Call<ShowResponse>
}

private data class CreateRequest(val secret: Secret) {
    data class Secret(val plain_text: String)
}

private data class CreateResponse(val secret: Secret) {
    data class Secret(
            val slug: String,
            val link: String,
            val key: String
    )
}

private data class ShowResponse(
        val plain_text: String?,
        val errors: String?
)
