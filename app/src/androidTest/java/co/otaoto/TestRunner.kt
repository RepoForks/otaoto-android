package co.otaoto

import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

class TestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): android.app.Application =
            super.newApplication(cl, Application::class.java.name, context)
}
