package co.otaoto.ui.show

import android.arch.lifecycle.Observer
import co.otaoto.api.TestApi
import co.otaoto.ui.base.BaseViewModelTest
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class ShowViewModelTest : BaseViewModelTest<ShowViewModel>() {

    @Mock
    private lateinit var stateObserver: Observer<ShowContract.State>
    @Mock
    private lateinit var secretObserver: Observer<String>

    @Test
    fun init_rendersGate_ifPathGate() {
        setupDefaultModel("gate")

        verify(stateObserver).onChanged(ShowContract.State.GATE)
        verifyZeroInteractions(secretObserver)
    }

    @Test
    fun init_rendersGate_ifPathShow() {
        setupDefaultModel("show")

        verify(stateObserver).onChanged(ShowContract.State.GATE)
        verifyZeroInteractions(secretObserver)
    }

    @Test
    fun init_rendersGone_ifPathGone() {
        setupDefaultModel("gone")

        verify(stateObserver).onChanged(ShowContract.State.GONE)
        verifyZeroInteractions(secretObserver)
    }

    @Test
    fun init_rendersGone_ifPathArbitrary() {
        setupDefaultModel("lol")

        verify(stateObserver).onChanged(ShowContract.State.GONE)
        verifyZeroInteractions(secretObserver)
    }

    @Test
    fun init_rendersGone_ifPathShort() {
        val pathSegments = listOf("gate")
        viewModel = ShowViewModel(API_CLIENT, pathSegments)
        setupObservers()

        verify(stateObserver).onChanged(ShowContract.State.GONE)
        verifyZeroInteractions(secretObserver)
    }

    @Test
    fun init_rendersGone_ifPathLong() {
        val pathSegments = listOf("gate", "foo", "bar", "baz")
        viewModel = ShowViewModel(API_CLIENT, pathSegments)
        setupObservers()

        verify(stateObserver).onChanged(ShowContract.State.GONE)
        verifyZeroInteractions(secretObserver)
    }

    @Test
    fun clickReveal_showsSecret_ifSuccess() = runBlocking {
        setupDefaultModel("gate")
        val loadingObserver = testObserver<Boolean>()
        viewModel.loadingDialogVisible.observeForever(loadingObserver)

        viewModel.clickReveal()

        with(inOrder(secretObserver, stateObserver, loadingObserver)) {
            verify(loadingObserver).onChanged(true)
            verify(secretObserver).onChanged(SECRET)
            verify(stateObserver).onChanged(ShowContract.State.SHOW)
            verify(loadingObserver).onChanged(false)
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun clickReveal_showsGone_ifException() = runBlocking {
        val pathSegments = listOf("gate", TestApi.ERROR, KEY)
        viewModel = ShowViewModel(API_CLIENT, pathSegments)
        setupObservers()
        val loadingObserver = testObserver<Boolean>()
        viewModel.loadingDialogVisible.observeForever(loadingObserver)

        viewModel.clickReveal()

        with(inOrder(secretObserver, stateObserver, loadingObserver)) {
            verify(loadingObserver).onChanged(true)
            verify(secretObserver, never()).onChanged(notNull())
            verify(stateObserver).onChanged(ShowContract.State.GONE)
            verify(loadingObserver).onChanged(false)
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun clickReveal_showsGone_ifFailure() = runBlocking {
        val pathSegments = listOf("gate", SLUG, TestApi.ERROR)
        viewModel = ShowViewModel(API_CLIENT, pathSegments)
        setupObservers()
        val loadingObserver = testObserver<Boolean>()
        viewModel.loadingDialogVisible.observeForever(loadingObserver)

        viewModel.clickReveal()

        with(inOrder(secretObserver, stateObserver, loadingObserver)) {
            verify(loadingObserver).onChanged(true)
            verify(secretObserver, never()).onChanged(notNull())
            verify(stateObserver).onChanged(ShowContract.State.GONE)
            verify(loadingObserver).onChanged(false)
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun clickAnother_movesToSecret() {
        val observer = testObserver<Unit>()
        setupDefaultModel("gate")
        viewModel.moveToCreateTrigger.observeForever(observer)

        viewModel.clickCreateAnother()

        verify(observer).onChanged(Unit)
    }

    private fun setupDefaultModel(path: String) {
        val pathSegments = listOf(path, SLUG, KEY)
        viewModel = ShowViewModel(API_CLIENT, pathSegments)
        setupObservers()
    }

    private fun setupObservers() {
        stateObserver = testObserver()
        secretObserver = testObserver()
        viewModel.state.observeForever(stateObserver)
        viewModel.secret.observeForever(secretObserver)
    }
}
