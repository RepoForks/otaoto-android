package co.otaoto.ui.show

import co.otaoto.api.MockApi
import co.otaoto.ui.base.BaseViewModelTest
import co.otaoto.ui.base.MockView
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Spy

class ShowViewModelTest : BaseViewModelTest<ShowViewModel, ShowContract.View>() {
    abstract class MockShowView : MockView(), ShowContract.View

    @Spy
    override lateinit var view: MockShowView

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun init_rendersGate_ifPathGate() {
        setupDefaultModel("gate")

        verify(view).renderGate()
    }

    @Test
    fun init_rendersGate_ifPathShow() {
        setupDefaultModel("show")

        verify(view).renderGate()
    }

    @Test
    fun init_rendersGone_ifPathGone() {
        setupDefaultModel("gone")

        verify(view).renderGone()
    }

    @Test
    fun init_rendersGone_ifPathArbitrary() {
        setupDefaultModel("lol")

        verify(view).renderGone()
    }

    @Test
    fun init_rendersGone_ifPathShort() {
        val pathSegments = listOf("gate")
        viewModel = ShowViewModel(API, pathSegments)
        viewModel.init(view)

        verify(view).renderGone()
    }

    @Test
    fun init_rendersGone_ifPathLong() {
        val pathSegments = listOf("gate", "foo", "bar", "baz")
        viewModel = ShowViewModel(API, pathSegments)
        viewModel.init(view)

        verify(view).renderGone()
    }

    @Test
    fun clickReveal_showsSecret_ifSuccess() = runBlocking {
        setupDefaultModel("gate")
        viewModel.clickReveal()

        with(inOrder(view)) {
            verify(view).showLoadingDialog()
            verify(view).hideLoadingDialog()
            verify(view).showSecret(MockApi.SECRET)
            verify(view).renderShow()
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun clickReveal_showsGone_ifFailure() = runBlocking {
        val pathSegments = listOf("gate", MockApi.ERROR, KEY)
        viewModel = ShowViewModel(API, pathSegments)
        viewModel.init(view)
        viewModel.clickReveal()

        with(inOrder(view)) {
            verify(view).showLoadingDialog()
            verify(view).hideLoadingDialog()
            verify(view).renderGone()
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun clickAnother_movesToSecret() {
        setupDefaultModel("gate")
        viewModel.clickCreateAnother()

        verify(view).moveToCreateScreen()
    }

    private fun setupDefaultModel(path: String) {
        val pathSegments = listOf(path, SLUG, KEY)
        viewModel = ShowViewModel(API, pathSegments)
        viewModel.init(view)
    }
}
