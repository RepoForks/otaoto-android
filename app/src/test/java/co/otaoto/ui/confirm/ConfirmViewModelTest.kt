package co.otaoto.ui.confirm

import co.otaoto.ui.base.BaseViewModelTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class ConfirmViewModelTest : BaseViewModelTest<ConfirmViewModel>() {

    @Before
    fun setUp() {
        viewModel = ConfirmViewModel(SECRET, SLUG, KEY)
    }

    @Test
    fun initialState() {
        assertEquals(URL, viewModel.url.value)
        assertEquals(false, viewModel.secretVisible.value)
        assertEquals(SECRET, viewModel.secretValue.value)
    }

    @Test
    fun setSecretVisible() {
        val observer = testObserver<Boolean>()
        val inOrder = inOrder(observer)

        viewModel.secretVisible.observeForever(observer)
        inOrder.verify(observer, never()).onChanged(true)

        viewModel.setSecretVisible(true)
        inOrder.verify(observer).onChanged(true)

        viewModel.setSecretVisible(false)
        inOrder.verify(observer).onChanged(false)
    }

    @Test
    fun clickLink_sharesUrl() {
        val observer = testObserver<Unit>()
        viewModel.shareTrigger.observeForever(observer)
        verifyZeroInteractions(observer)

        viewModel.clickLink()
        verify(observer).onChanged(Unit)
    }

    @Test
    fun clickCreateAnother_movesToCreate() {
        val observer = testObserver<Unit>()
        viewModel.moveToCreateTrigger.observeForever(observer)
        verifyZeroInteractions(observer)

        viewModel.clickCreateAnother()
        verify(observer).onChanged(Unit)
    }
}
