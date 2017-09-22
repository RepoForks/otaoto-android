package co.otaoto.ui.create

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import co.otaoto.R
import co.otaoto.util.OrientationAction.Companion.landscapeRotation
import co.otaoto.util.OrientationAction.Companion.portraitRotation
import co.otaoto.util.isTextInputLayoutPasswordToggle
import co.otaoto.util.withTextInputLayoutHint
import org.junit.Rule
import org.junit.Test

class CreateActivityTest {
    companion object {
        const val SECRET = "Shh! Don't tell anyone!"
    }

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(CreateActivity::class.java)

    @Test
    fun smokeTest() {
        val passwordToggle = onView(isTextInputLayoutPasswordToggle())
        passwordToggle.check(matches(isChecked()))
        passwordToggle.perform(landscapeRotation())
        passwordToggle.check(matches(isChecked()))
        passwordToggle.perform(portraitRotation())
        passwordToggle.check(matches(isChecked()))

        val inputField = onView(withTextInputLayoutHint(R.string.create_input_hint))
        inputField.check(matches(isDisplayed()))
        inputField.perform(typeText(SECRET))
        inputField.check(matches(withText(SECRET)))
        inputField.perform(closeSoftKeyboard())

        val submitButton = onView(withText(R.string.create_submit))
        submitButton.check(matches(isDisplayed()))
        submitButton.perform(click())

        val confirmText = onView(withText(R.string.confirm_created))
        confirmText.check(matches(isDisplayed()))
    }
}
