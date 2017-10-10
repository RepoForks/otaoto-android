package co.otaoto.ui.confirm

import co.otaoto.ui.base.BaseViewModelTest
import co.otaoto.ui.base.MockView
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Spy

class ConfirmViewModelTest : BaseViewModelTest<ConfirmViewModel, ConfirmContract.View>() {
    abstract class MockConfirmView : MockView(), ConfirmContract.View

    @Spy
    override lateinit var view: MockConfirmView

    @Before
    fun setUp() {
        viewModel = ConfirmViewModel(SECRET, SLUG, KEY)
        viewModel.init(view)
    }

    @Test
    fun init_setsUrl() {
        verify(view).setLinkUrl(URL)
    }

    @Test
    fun setSecretVisible_showsSecret_ifTrue() {
        viewModel.setSecretVisible(true)

        verify(view).showSecret()
        verify(view).setSecretText(SECRET)
    }

    @Test
    fun setSecretVisible_hidesSecret_ifFalse() {
        viewModel.setSecretVisible(false)

        verify(view).hideSecret()
        verify(view).setSecretText("")
    }

    @Test
    fun clickLink_sharesUrl() {
        viewModel.clickLink()

        verify(view).shareUrl(URL)
    }

    @Test
    fun clickCreateAnother_movesToCreate() {
        viewModel.clickCreateAnother()

        verify(view).moveToCreateScreen()
    }
}
