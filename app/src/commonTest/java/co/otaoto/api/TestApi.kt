package co.otaoto.api

import retrofit2.Call
import retrofit2.mock.Calls
import java.io.IOException

object TestApi : OtaotoApi {
    const val ERROR = "error"
    const val SLUG = "three-word-slug"
    const val KEY = "1234567890ABCDEF"
    const val SECRET = "That's my secret, Captain."
    val EXCEPTION = IOException("exception")

    override fun create(body: CreateRequest): Call<CreateResponse> {
        return if (body.secret.plain_text == ERROR) {
            Calls.failure(EXCEPTION)
        } else {
            Calls.response(CreateResponse(CreateResponse.Secret(SLUG, "", KEY)))
        }
    }

    override fun show(slug: String, key: String): Call<ShowResponse> {
        return when {
            slug == ERROR -> Calls.failure(EXCEPTION)
            key == ERROR -> Calls.response(ShowResponse(errors = ERROR))
            else -> Calls.response(ShowResponse(plain_text = SECRET))
        }
    }
}

