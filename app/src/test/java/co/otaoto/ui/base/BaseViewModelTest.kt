package co.otaoto.ui.base

import android.arch.core.executor.testing.InstantTaskExecutorRule
import co.otaoto.api.ApiClient
import co.otaoto.api.TestApi
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations

abstract class BaseViewModelTest<VM : BaseViewModel<V>, V : BaseContract.View> {
    companion object {
        val API_CALLER = ApiClient(TestApi)
        const val SECRET = "That's my secret, Captain"
        const val SLUG = "three-word-slug"
        const val KEY = "1234567890ABCDEF"
        const val URL = "https://otaoto.co/gate/$SLUG/$KEY"
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    protected lateinit var viewModel: VM

    protected abstract val view: V

    @Before
    fun baseSetUp() {
        MockitoAnnotations.initMocks(this)
    }
}
