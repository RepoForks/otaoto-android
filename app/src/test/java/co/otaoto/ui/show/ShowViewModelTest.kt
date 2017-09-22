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
        model = ShowViewModel()
    }

    @Test
    fun init_rendersGate_ifPathGate() {
        val pathSegments = listOf("gate", SLUG, KEY)
        model.init(view, pathSegments, API)

        verify(view).renderGate()
    }

    @Test
    fun init_rendersGate_ifPathShow() {
        val pathSegments = listOf("show", SLUG, KEY)
        model.init(view, pathSegments, API)

        verify(view).renderGate()
    }

    @Test
    fun init_rendersGone_ifPathGone() {
        val pathSegments = listOf("gone", SLUG, KEY)
        model.init(view, pathSegments, API)

        verify(view).renderGone()
    }

    @Test
    fun init_rendersGone_ifPathArbitrary() {
        val pathSegments = listOf("lol", SLUG, KEY)
        model.init(view, pathSegments, API)

        verify(view).renderGone()
    }

    @Test
    fun init_rendersGone_ifPathShort() {
        val pathSegments = listOf("gate")
        model.init(view, pathSegments, API)

        verify(view).renderGone()
    }

    @Test
    fun init_rendersGone_ifPathLong() {
        val pathSegments = listOf("gate", "foo", "bar", "baz")
        model.init(view, pathSegments, API)

        verify(view).renderGone()
    }

    @Test
    fun clickReveal_showsSecret_ifSuccess() {
        runBlocking {
            model.init(view, listOf("gate", SLUG, KEY), API)
            model.clickReveal(view)

            verify(view).renderShow()
            verify(view).showSecret(MockApi.SECRET)
        }
    }

    @Test
    fun clickReveal_showsSecret_ifFailure() = runBlocking {
        model.init(view, listOf("gate", MockApi.ERROR, KEY), API)
        model.clickReveal(view)

        verify(view).renderGone()
    }

    @Test
    fun clickAnother_movesToSecret() {
        model.clickCreateAnother(view)

        verify(view).moveToCreateScreen()
    }
}
