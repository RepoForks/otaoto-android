package co.otaoto.ui.confirm

import android.content.Intent
import android.net.Uri
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.transition.TransitionManager
import co.otaoto.R
import co.otaoto.api.MockApi
import co.otaoto.ui.show.ShowActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test

class ShowActivityTest {
    companion object {
        const val SLUG = "three-word-slug"
        const val KEY = "1234567890ABCDEF"
    }

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(ShowActivity::class.java, false, false)

    @Test
    fun smokeTest() {
        val path = "https://otaoto.co/gate/$SLUG/$KEY"
        activityTestRule.launchActivity(Intent(Intent.ACTION_VIEW, Uri.parse(path)))

        val revealButton = onView(withText(R.string.show_reveal))
        val secretText = onView(withId(R.id.show_secret_text))
        secretText.check(matches(withEffectiveVisibility(Visibility.GONE)))
        secretText.check(matches(withText("")))

        revealButton.perform(click())
        TransitionManager.endTransitions(activityTestRule.activity.rootView)
        secretText.check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        secretText.check(matches(withText(MockApi.SECRET)))
        revealButton.check(matches(not(isEnabled())))

        val anotherButton = onView(withText(R.string.show_create_button))
        anotherButton.perform(click())

        val secretSubmit = onView(withText(R.string.create_submit))
        secretSubmit.check(matches(isDisplayed()))
    }

    @Test
    fun smokeTest_gone() {
        val path = "https://otaoto.co/gate/${MockApi.ERROR}/$KEY"
        activityTestRule.launchActivity(Intent(Intent.ACTION_VIEW, Uri.parse(path)))

        val revealButton = onView(withText(R.string.show_reveal))
        val secretText = onView(withId(R.id.show_secret_text))
        secretText.check(matches(withEffectiveVisibility(Visibility.GONE)))
        secretText.check(matches(withText("")))

        revealButton.perform(click())
        TransitionManager.endTransitions(activityTestRule.activity.rootView)
        secretText.check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        secretText.check(matches(withText(R.string.show_gone)))
        revealButton.check(matches(not(isEnabled())))
    }
}
