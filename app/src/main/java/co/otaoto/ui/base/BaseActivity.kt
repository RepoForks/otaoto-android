package co.otaoto.ui.base

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleActivity

@SuppressLint("Registered")
open class BaseActivity<VM : BaseViewModel<V>, V : BaseViewModel.View> : LifecycleActivity()
