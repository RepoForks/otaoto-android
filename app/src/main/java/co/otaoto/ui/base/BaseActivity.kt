package co.otaoto.ui.base

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import dagger.android.AndroidInjection

abstract class BaseActivity<VM : BaseViewModel<V>, in V : BaseViewModel.View> : AppCompatActivity() {

    protected abstract val viewModelFactory: BaseViewModel.Factory<VM>
    protected abstract val viewModelClass: Class<VM>
    protected val viewModel: VM by lazy(LazyThreadSafetyMode.NONE) { ViewModelProviders.of(this, viewModelFactory)[viewModelClass] }

    protected abstract val layoutRes: Int
        @LayoutRes get

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        ButterKnife.bind(this)
        @Suppress("UNCHECKED_CAST")
        viewModel.init(this as V)
    }
}
