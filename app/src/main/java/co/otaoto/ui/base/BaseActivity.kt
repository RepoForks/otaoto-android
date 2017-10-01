package co.otaoto.ui.base

import android.app.Dialog
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import butterknife.ButterKnife
import dagger.android.AndroidInjection

abstract class BaseActivity<VM : BaseViewModel<V>, out P : BasePresenter<V>, V : BaseView> : AppCompatActivity(), BaseView {
    protected abstract val viewModelFactory: BaseViewModel.Factory<VM>
    protected abstract val viewModelClass: Class<VM>
    private val viewModel: VM by lazy(LazyThreadSafetyMode.NONE) { ViewModelProviders.of(this, viewModelFactory)[viewModelClass] }
    @Suppress("UNCHECKED_CAST")
    protected val presenter: P
        get() = viewModel as P

    protected abstract val layoutRes: Int
        @LayoutRes get

    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        ButterKnife.bind(this)
        @Suppress("UNCHECKED_CAST")
        presenter.init(this as V)
    }

    override fun <T> observe(liveData: LiveData<T>, observer: Observer<T>) = liveData.observe(this, observer)

    override fun showLoadingDialog() {
        loadingDialog?.let { return }
        val dialog = Dialog(this).apply {
            setContentView(ProgressBar(this@BaseActivity))
        }
        dialog.show()
        loadingDialog = dialog
    }

    override fun hideLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}
