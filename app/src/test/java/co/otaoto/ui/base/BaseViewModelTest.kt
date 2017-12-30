package co.otaoto.ui.base

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import co.otaoto.api.ApiClient
import co.otaoto.api.TestApi
import org.junit.Rule
import org.mockito.Mockito.mock

abstract class BaseViewModelTest<VM : BaseViewModel> : BaseTest() {
    companion object {
        val API_CLIENT = ApiClient(TestApi)
        const val SECRET = TestApi.SECRET
        const val SLUG = TestApi.SLUG
        const val KEY = TestApi.KEY
        const val URL = "https://otaoto.co/gate/$SLUG/$KEY"
    }

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    protected lateinit var viewModel: VM

    @Suppress("UNCHECKED_CAST")
    protected fun <T> testObserver(): Observer<T> = mock(Observer::class.java) as Observer<T>
}
