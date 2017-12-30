package co.otaoto.api

import co.otaoto.ui.base.BaseTest
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class ApiClientTest : BaseTest() {

    private val api = TestApi

    private lateinit var apiClient: ApiClient

    @Before
    fun setup() {
        apiClient = ApiClient(api)
    }

    @Test
    fun create_success() = runBlocking {
        val result = apiClient.create(TestApi.SECRET)
        assertTrue { result is CreateSuccess }
    }

    @Test
    fun create_failure() = runBlocking {
        val result = apiClient.create(TestApi.ERROR)
        assertTrue { result is CreateError }
    }

    @Test
    fun show_success() = runBlocking {
        val result = apiClient.show(TestApi.SLUG, TestApi.KEY)
        assertTrue { result is ShowSuccess }
    }

    @Test
    fun show_failure() = runBlocking {
        val result = apiClient.show(TestApi.SLUG, TestApi.ERROR)
        assertTrue { result is ShowError }
    }

    @Test
    fun show_exception() = runBlocking {
        val result = apiClient.show(TestApi.ERROR, TestApi.KEY)
        assertTrue { result is ShowException }
    }

}
