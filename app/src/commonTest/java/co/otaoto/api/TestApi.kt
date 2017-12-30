package co.otaoto.api

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import java.io.IOException

object TestApi : OtaotoApi {
    const val ERROR = "error"
    const val SLUG = "three-word-slug"
    const val KEY = "1234567890ABCDEF"
    const val SECRET = "That's my secret, Captain."
    val EXCEPTION = IOException("exception")

    override fun create(body: CreateRequest): Deferred<CreateResponse> = async {
        if (body.secret.plain_text == ERROR) {
            throw EXCEPTION
        } else {
            CreateResponse(CreateResponse.Secret(SLUG, "", KEY))
        }
    }

    override fun show(slug: String, key: String): Deferred<ShowResponse> = async {
        when {
            slug == ERROR -> throw EXCEPTION
            key == ERROR -> ShowResponse(errors = ERROR)
            else -> ShowResponse(plain_text = SECRET)
        }
    }
}

