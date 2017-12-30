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

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(), BaseContract.View {
    protected abstract val viewModelFactory: BaseViewModel.Factory<VM>
    protected abstract val viewModelClass: Class<VM>
    protected abstract val layoutRes: Int
        @LayoutRes get

    protected val viewModel: VM by lazy(LazyThreadSafetyMode.NONE) { ViewModelProviders.of(this, viewModelFactory)[viewModelClass] }

    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        ButterKnife.bind(this)

        viewModel.loadingDialogVisible.observe { if (it == true) showLoadingDialog() else hideLoadingDialog() }
    }

    override fun <T> LiveData<T>.observe(observer: (T?) -> Unit) {
        observe(this@BaseActivity, Observer { observer(it) })
    }

    override fun <T> LiveData<T>.observeNonNull(observer: (T) -> Unit) {
        observe(this@BaseActivity, Observer { it?.let { observer(it) } })
    }

    private fun showLoadingDialog() {
        loadingDialog?.let { return }
        val dialog = Dialog(this).apply {
            setContentView(ProgressBar(this@BaseActivity))
        }
        dialog.show()
        loadingDialog = dialog
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}
