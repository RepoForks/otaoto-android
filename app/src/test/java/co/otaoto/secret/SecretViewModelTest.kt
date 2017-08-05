package co.otaoto.secret

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SecretViewModelTest {
    private lateinit var model: SecretViewModel

    @Mock
    private lateinit var view: SecretViewModel.View

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        model = SecretViewModel()
    }

    @Test
    fun checkPasswordVisibleHack_onlyFirstTime() {
        model.checkPasswordVisibleHack(view)
        model.checkPasswordVisibleHack(view)
        model.checkPasswordVisibleHack(view)

        verify(view, times(1)).performPasswordVisibleHack()
    }
}
