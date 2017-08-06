package co.otaoto.ui.secret

import co.otaoto.api.MockApi
import co.otaoto.injector.Injector
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SecretViewModelTest {
    private lateinit var model: SecretViewModel

    @Mock
    private lateinit var view: SecretViewModel.View

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        model = SecretViewModel()
        Injector.api = MockApi()
    }

    @Test
    fun init_performPasswordVisibleHack_onlyFirstTime() {
        model.init(view)
        model.init(view)
        model.init(view)

        verify(view, times(1)).performPasswordVisibleHack()
    }

    @Test
    fun submit_moveToConfirm_ifSuccess() = runBlocking {
        model.submit(view, MockApi.SECRET)

        verify(view).moveToConfirmScreen(anyString(), anyString(), anyString())
    }

    @Test
    fun submit_showError_ifFailure() = runBlocking {
        model.submit(view, MockApi.ERROR)

        verify(view).showError()
    }
}
