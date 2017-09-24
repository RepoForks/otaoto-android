package co.otaoto.ui.show

import android.arch.core.executor.testing.InstantTaskExecutorRule
import co.otaoto.api.MockApi
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ShowViewModelTest {
    companion object {
        const val SLUG = "three-word-slug"
        const val KEY = "1234567890ABCDEF"
        val API = MockApi()
    }

    private lateinit var model: ShowViewModel

    @Mock
    private lateinit var view: ShowViewModel.View

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun init_rendersGate_ifPathGate() {
        setupDefaultModel("gate")
        model.init(view)

        verify(view).renderGate()
    }

    @Test
    fun init_rendersGate_ifPathShow() {
        setupDefaultModel("show")
        model.init(view)

        verify(view).renderGate()
    }

    @Test
    fun init_rendersGone_ifPathGone() {
        setupDefaultModel("gone")
        model.init(view)

        verify(view).renderGone()
    }

    @Test
    fun init_rendersGone_ifPathArbitrary() {
        setupDefaultModel("lol")
        model.init(view)

        verify(view).renderGone()
    }

    @Test
    fun init_rendersGone_ifPathShort() {
        val pathSegments = listOf("gate")
        model = ShowViewModel(API, pathSegments)
        model.init(view)

        verify(view).renderGone()
    }

    @Test
    fun init_rendersGone_ifPathLong() {
        val pathSegments = listOf("gate", "foo", "bar", "baz")
        model = ShowViewModel(API, pathSegments)
        model.init(view)

        verify(view).renderGone()
    }

    @Test
    fun clickReveal_showsSecret_ifSuccess() {
        runBlocking {
            setupDefaultModel("gate")
            model.clickReveal(view)

            verify(view).renderShow()
            verify(view).showSecret(MockApi.SECRET)
        }
    }

    @Test
    fun clickReveal_showsSecret_ifFailure() = runBlocking {
        val pathSegments = listOf("gate", MockApi.ERROR, KEY)
        model = ShowViewModel(API, pathSegments)
        model.clickReveal(view)

        verify(view).renderGone()
    }

    @Test
    fun clickAnother_movesToSecret() {
        setupDefaultModel("gate")
        model.clickCreateAnother(view)

        verify(view).moveToCreateScreen()
    }

    private fun setupDefaultModel(path: String) {
        val pathSegments = listOf(path, SLUG, KEY)
        model = ShowViewModel(API, pathSegments)
    }
}
