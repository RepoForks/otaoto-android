package co.otaoto.ui.confirm

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ConfirmViewModelTest {
    companion object {
        const val SECRET = "That's my secret, Captain"
        const val SLUG = "three-word-slug"
        const val KEY = "1234567890ABCDEF"
        const val URL = "https://otaoto.co/gate/$SLUG/$KEY"
    }

    private lateinit var model: ConfirmViewModel

    @Mock
    private lateinit var view: ConfirmViewModel.View

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        model = ConfirmViewModel()

        model.init(view, SECRET, SLUG, KEY)
    }

    @Test
    fun init_setsUrl() {
        verify(view).setLinkUrl(URL)
    }

    @Test
    fun setSecretVisible_showsSecret_ifTrue() {
        model.setSecretVisible(view, true)

        verify(view).showSecret()
        verify(view).setSecretText(SECRET)
    }

    @Test
    fun setSecretVisible_hidesSecret_ifFalse() {
        model.setSecretVisible(view, false)

        verify(view).hideSecret()
        verify(view).setSecretText("")
    }

    @Test
    fun clickLink_copiesUrl() {
        model.clickLink(view)

        verify(view).copyUrl(URL)
    }

    @Test
    fun lol() {
        model.clickCreateAnother(view)

        verify(view).moveToSecretScreen()
    }
}
