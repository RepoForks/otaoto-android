package co.otaoto.util

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.support.design.widget.CheckableImageButton
import android.support.design.widget.TextInputLayout
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.withParent
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import android.view.View
import android.widget.TextView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher


class OrientationAction private constructor(private val orientation: Int) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return any(View::class.java)
    }

    override fun getDescription(): String {
        return "change orientation to " + orientation
    }

    override fun perform(uiController: UiController, view: View) {
        uiController.loopMainThreadUntilIdle()
        val activity = view.context as Activity
        activity.requestedOrientation = orientation

        val resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
        if (resumedActivities.isEmpty()) {
            throw RuntimeException("Could not change orientation")
        }
    }

    companion object {

        fun landscapeRotation(): ViewAction {
            return OrientationAction(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        }

        fun portraitRotation(): ViewAction {
            return OrientationAction(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }
    }
}

inline private fun <reified T : View> withTextInputLayoutHelper(matcher: Matcher<View>) = allOf(
        instanceOf(T::class.java),
        withParent(withParent(matcher))
)

fun withTextInputLayoutHint(expectedHintText: Int): Matcher<View> = withTextInputLayoutHelper<TextView>(
        object : TypeSafeMatcher<View>() {
            private var resources: Resources? = null

            override fun matchesSafely(view: View): Boolean {
                resources = view.resources
                if (view !is TextInputLayout) {
                    return false
                }

                val hint = view.hint ?: return false
                return view.resources.getString(expectedHintText) == hint.toString()
            }

            override fun describeTo(description: Description) {
                description.appendText("with text input layout hint: ${resources?.getString(expectedHintText) ?: expectedHintText}")
            }
        }
)

fun isTextInputLayoutPasswordToggle() = withTextInputLayoutHelper<CheckableImageButton>(instanceOf(TextInputLayout::class.java))
