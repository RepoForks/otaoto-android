package co.otaoto

import android.app.Activity
import android.app.Dialog
import android.support.annotation.IdRes
import android.view.View

fun <T : View> Activity.bindView(@IdRes id: Int) = lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(id) }
fun <T : View> Dialog.bindView(@IdRes id: Int) = lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(id) }
fun <T : View> View.bindView(@IdRes id: Int) = lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(id) }
