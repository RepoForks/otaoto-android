package co.otaoto.api

import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.mock.Calls
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit

object MockApi : OtaotoApi {
    private const val SLUG = "three-word-slug"
    private const val KEY = "1234567890ABCDEF"
    private const val SECRET = "That's my secret, Captain."

    private val networkBehavior: NetworkBehavior = NetworkBehavior.create().apply {
        setDelay(1, TimeUnit.SECONDS)
        setFailurePercent(25)
        setErrorPercent(25)
    }
    private val mockRetrofit: MockRetrofit = MockRetrofit.Builder(retrofit)
            .networkBehavior(networkBehavior)
            .build()
    private val behaviorDelegate = mockRetrofit.create(OtaotoApi::class.java)

    override fun create(body: CreateRequest): Deferred<CreateResponse> {
        val call = Calls.response(CreateResponse(CreateResponse.Secret(SLUG, "", KEY)))
        return behaviorDelegate.returning(call).create(body)
    }

    override fun show(slug: String, key: String): Deferred<ShowResponse> {
        val call = Calls.response(ShowResponse(plain_text = SECRET))
        return behaviorDelegate.returning(call).show(slug, key)
    }
}
