package cat.itb.tanderapp.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.itb.tanderapp.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NavigationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void login_go_to_profile_fragment_go_home_fragment_and_finally_go_chat_fragment() throws InterruptedException {
        ViewInteraction button = onView(
                allOf(withId(R.id.button_login), withText("LOG IN"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0),
                                4),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editText_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_fragment),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("jonylecha@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editText_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_fragment),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("joanlecha"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editText_password), withText("joanlecha"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_fragment),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(pressImeActionButton());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.log_in_button), withText("LOG IN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_fragment),
                                        0),
                                5),
                        isDisplayed()));
        button2.perform(click());
        Thread.sleep(3000);

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.home_page), withContentDescription("Home"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.top_app_bar),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.profile_page), withContentDescription("Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.top_app_bar),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.chat_page), withContentDescription("Chat"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.top_app_bar),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());
    }
    @Test
    public void login_go_to_profile_fragment_press_settings_go_back_press_editprofile_go_to_see_profile() throws InterruptedException {
        ViewInteraction button = onView(
                allOf(withId(R.id.button_login), withText("LOG IN"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0),
                                4),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editText_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_fragment),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("jonylechaa@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editText_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_fragment),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("joanlecha"), closeSoftKeyboard());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.log_in_button), withText("LOG IN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_fragment),
                                        0),
                                5),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editText_email), withText("jonylechaa@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_fragment),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("jonylecha@gmail.com"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editText_email), withText("jonylecha@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_fragment),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.log_in_button), withText("LOG IN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_fragment),
                                        0),
                                5),
                        isDisplayed()));
        button3.perform(click());
        Thread.sleep(3000);

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.profile_page), withContentDescription("Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.top_app_bar),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.profile_edit),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0),
                                5),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.preview_page), withContentDescription("Preview"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.top_app_edit_bar),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.edit_page), withContentDescription("Edit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.top_app_edit_bar),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());
        pressBack();

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.profile_settings),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton2.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
