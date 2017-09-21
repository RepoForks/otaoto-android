package co.otaoto.ui.base

import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<VM : BaseViewModel<V>, V : BaseViewModel.View> : AppCompatActivity()
