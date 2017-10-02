package co.otaoto.ui.confirm

import android.app.Instrumentation
import android.content.Intent.*
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.transition.TransitionManager
import android.widget.ToggleButton
import co.otaoto.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Rule
import org.junit.Test


class ConfirmActivityTest {
    companion object {
        const val SECRET = "That's my secret, Captain"
        const val SLUG = "three-word-slug"
        const val KEY = "1234567890ABCDEF"
        const val URL = "https://otaoto.co/gate/$SLUG/$KEY"
    }

    @Rule
    @JvmField
    val activityTestRule = object : IntentsTestRule<ConfirmActivity>(ConfirmActivity::class.java) {
        override fun getActivityIntent() = ConfirmActivity.newIntent(InstrumentationRegistry.getTargetContext(), SECRET, SLUG, KEY)
    }

    @Test
    fun smokeTest() {
        val linkText = onView(hasLinks())
        linkText.check(matches(withText(URL)))

        val result = Instrumentation.ActivityResult(0, null)
        intending(hasAction(ACTION_CHOOSER)).respondWith(result)
        linkText.perform(click())
        intended(allOf(
                hasAction(ACTION_CHOOSER),
                hasExtras(allOf(
                        hasEntry(EXTRA_TITLE, activityTestRule.activity.getString(R.string.label_share_url)),
                        hasEntry(EXTRA_INTENT, allOf(
                                hasAction(ACTION_SEND),
                                hasType("text/*"),
                                hasExtra(EXTRA_TEXT, URL)
                        ))
                ))
        ))

        val toggleButton = onView(instanceOf(ToggleButton::class.java))
        val secretText = onView(withId(R.id.confirm_secret_text))
        toggleButton.check(matches(isNotChecked()))
        secretText.check(matches(withEffectiveVisibility(Visibility.GONE)))

        toggleButton.perform(click())
        TransitionManager.endTransitions(activityTestRule.activity.rootView)
        toggleButton.check(matches(isChecked()))
        secretText.check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        toggleButton.perform(click())
        TransitionManager.endTransitions(activityTestRule.activity.rootView)
        toggleButton.check(matches(isNotChecked()))
        secretText.check(matches(withEffectiveVisibility(Visibility.GONE)))

        val anotherButton = onView(withText(R.string.confirm_create_another))
        anotherButton.perform(click())
        val secretSubmit = onView(withText(R.string.create_submit))
        secretSubmit.check(matches(isDisplayed()))
    }
}
