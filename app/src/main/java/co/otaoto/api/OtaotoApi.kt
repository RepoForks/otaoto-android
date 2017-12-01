package co.otaoto.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OtaotoApi {
    @POST("create")
    fun create(@Body body: CreateRequest): Call<CreateResponse>

    @GET("show/{slug}/{key}")
    fun show(@Path("slug") slug: String, @Path("key") key: String): Call<ShowResponse>
}

val retrofit: Retrofit by lazy {
    Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
}

const val BASE_URL = "https://otaoto.co/api/"

data class CreateRequest(val secret: Secret) {
    data class Secret(val plain_text: String)
}

data class CreateResponse(val secret: Secret) {
    data class Secret(
            val slug: String,
            val link: String,
            val key: String
    )
}

data class ShowResponse(
        val plain_text: String? = null,
        val errors: String? = null
)
