package co.otaoto.ui.confirm

import co.otaoto.ui.base.BaseViewModelTest
import co.otaoto.ui.base.MockView
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Spy

class ConfirmViewModelTest : BaseViewModelTest<ConfirmViewModel, ConfirmViewModel.View>() {
    abstract class MockConfirmView : MockView(), ConfirmViewModel.View

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
        viewModel.setSecretVisible(view, true)

        verify(view).showSecret()
        verify(view).setSecretText(SECRET)
    }

    @Test
    fun setSecretVisible_hidesSecret_ifFalse() {
        viewModel.setSecretVisible(view, false)

        verify(view).hideSecret()
        verify(view).setSecretText("")
    }

    @Test
    fun clickLink_sharesUrl() {
        viewModel.clickLink(view)

        verify(view).shareUrl(URL)
    }

    @Test
    fun clickCreateAnother_movesToCreate() {
        viewModel.clickCreateAnother(view)

        verify(view).moveToCreateScreen()
    }
}
