package co.otaoto.ui.create

import co.otaoto.api.MockApi
import co.otaoto.ui.base.BaseViewModelTest
import co.otaoto.ui.base.MockView
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import org.mockito.Spy

class CreateViewModelTest : BaseViewModelTest<CreateViewModel, CreateViewModel.View>() {
    abstract class MockCreateView : MockView(), CreateViewModel.View

    @Spy
    override lateinit var view: MockCreateView

    @Before
    fun setUp() {
        viewModel = CreateViewModel(API)
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
        viewModel.submit(MockApi.SECRET)

        with(inOrder(view)) {
            verify(view).showLoadingDialog()
            verify(view).hideLoadingDialog()
            verify(view).moveToConfirmScreen(anyString(), anyString(), anyString())
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun submit_showError_ifFailure() = runBlocking {
        viewModel.submit(MockApi.ERROR)

        with(inOrder(view)) {
            verify(view).showLoadingDialog()
            verify(view).hideLoadingDialog()
            verify(view).showError()
            verifyNoMoreInteractions()
        }
    }
}
