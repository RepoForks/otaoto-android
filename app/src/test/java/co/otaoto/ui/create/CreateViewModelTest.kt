package co.otaoto.ui.create

import co.otaoto.api.TestApi
import co.otaoto.ui.base.BaseViewModelTest
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class CreateViewModelTest : BaseViewModelTest<CreateViewModel>() {

    @Before
    fun setUp() {
        viewModel = CreateViewModel(API_CLIENT)
    }

    @Test
    fun performPasswordVisibleHack_nullAfterComplete() {
        val testObserver = testObserver<Unit>()
        viewModel.passwordVisibleHackTrigger.observeForever(testObserver)

        verify(testObserver).onChanged(Unit)

        viewModel.reportPasswordVisibleHackComplete()
        verify(testObserver).onChanged(null)

        verifyNoMoreInteractions(testObserver)
    }

    @Test
    fun submit_moveToConfirm_ifSuccess() = runBlocking {
        val moveObserver = testObserver<CreateContract.SecretData>()
        val errorObserver = testObserver<Throwable>()
        val loadingObserver = testObserver<Boolean>()

        with(viewModel) {
            moveToConfirmTrigger.observeForever(moveObserver)
            errorTrigger.observeForever(errorObserver)
            loadingDialogVisible.observeForever(loadingObserver)
            submit(TestApi.SECRET)
        }

        with(inOrder(moveObserver, errorObserver, loadingObserver)) {
            verify(loadingObserver).onChanged(true)
            verify(moveObserver).onChanged(notNull())
            verifyNoMoreInteractions(moveObserver)
            verifyZeroInteractions(errorObserver)
            verify(loadingObserver).onChanged(false)
        }
    }

    @Test
    fun submit_showError_ifException() = runBlocking {
        val moveObserver = testObserver<CreateContract.SecretData>()
        val errorObserver = testObserver<Throwable>()
        val loadingObserver = testObserver<Boolean>()

        with(viewModel) {
            moveToConfirmTrigger.observeForever(moveObserver)
            errorTrigger.observeForever(errorObserver)
            loadingDialogVisible.observeForever(loadingObserver)
            submit(TestApi.ERROR)
        }

        with(inOrder(moveObserver, errorObserver, loadingObserver)) {
            verify(loadingObserver).onChanged(true)
            verifyZeroInteractions(moveObserver)
            verify(errorObserver).onChanged(notNull())
            verifyNoMoreInteractions(errorObserver)
            verify(loadingObserver).onChanged(false)
        }
    }
}
