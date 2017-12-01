package co.otaoto.ui.create

import co.otaoto.api.TestApi
import co.otaoto.ui.base.BaseViewModelTest
import co.otaoto.ui.base.MockView
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import org.mockito.Spy

class CreateViewModelTest : BaseViewModelTest<CreateViewModel, CreateContract.View>() {
    abstract class MockCreateView : MockView(), CreateContract.View

    @Spy
    override lateinit var view: MockCreateView

    @Before
    fun setUp() {
        viewModel = CreateViewModel(API_CALLER)
        viewModel.init(view)
    }

    @Test
    fun init_performPasswordVisibleHack_onlyFirstTime() {
        viewModel.init(view)
        viewModel.init(view)

        verify(view, times(1)).performPasswordVisibleHack()
    }

    @Test
    fun submit_moveToConfirm_ifSuccess() = runBlocking {
        viewModel.submit(TestApi.SECRET)

        with(inOrder(view)) {
            verify(view).showLoadingDialog()
            verify(view).hideLoadingDialog()
            verify(view).moveToConfirmScreen(anyString(), anyString(), anyString())
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun submit_showError_ifException() = runBlocking {
        viewModel.submit(TestApi.ERROR)

        with(inOrder(view)) {
            verify(view).showLoadingDialog()
            verify(view).hideLoadingDialog()
            verify(view).showError(TestApi.EXCEPTION)
            verifyNoMoreInteractions()
        }
    }
}
