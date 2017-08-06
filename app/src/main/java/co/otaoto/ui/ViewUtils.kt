package co.otaoto.ui

import android.app.Activity
import android.app.Dialog
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View

fun <T : View> Activity.bindView(@IdRes id: Int) = lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(id) }
fun <T : View> Dialog.bindView(@IdRes id: Int) = lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(id) }
fun <T : View> View.bindView(@IdRes id: Int) = lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(id) }

fun <T : ViewModel> FragmentActivity.bindViewModel(modelClass: Class<T>)
        = lazy(LazyThreadSafetyMode.NONE) { ViewModelProviders.of(this)[modelClass] }

fun <T : ViewModel> Fragment.bindViewModel(modelClass: Class<T>)
        = lazy(LazyThreadSafetyMode.NONE) { ViewModelProviders.of(this)[modelClass] }

fun <T : ViewModel> Fragment.bindActivityViewModel(modelClass: Class<T>)
        = lazy(LazyThreadSafetyMode.NONE) { ViewModelProviders.of(activity)[modelClass] }
