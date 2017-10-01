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

abstract class BaseActivity<VM : BaseViewModel<V>, V : BaseViewModel.View> : AppCompatActivity(), BaseViewModel.View {
    protected abstract val viewModelFactory: BaseViewModel.Factory<VM>
    protected abstract val viewModelClass: Class<VM>
    protected val viewModel: VM by lazy(LazyThreadSafetyMode.NONE) { ViewModelProviders.of(this, viewModelFactory)[viewModelClass] }

    protected abstract val layoutRes: Int
        @LayoutRes get

    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        ButterKnife.bind(this)
        @Suppress("UNCHECKED_CAST")
        viewModel.init(this as V)
    }

    override fun <T> observe(liveData: LiveData<T>, observer: Observer<T>) {
        liveData.observe(this, observer)
    }

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
