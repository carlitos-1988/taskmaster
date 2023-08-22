package com.jco.taskmaster;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTestFinal {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTestFinal() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.SetUsernameActivityButton), withContentDescription("small image button to bring you to the set username activity\n"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.SettingsActivityEditInputTextUsername), withText("definedUserName"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Juan"));

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.SettingsActivityEditInputTextUsername), withText("Juan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText2.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.ActivitySaveUsernameButton), withText("Set new username\n"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        pressBack();

        ViewInteraction textView = onView(
                allOf(withId(R.id.MainActivityUsernameTextView), withText("Juan"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("Juan")));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.MainActivityAddTask), withText("Add Task"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.CreateActivityTaskTitleInput),
                        childAtPosition(
                                allOf(withId(R.id.AddTaskActivityPage),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("Test Task"), closeSoftKeyboard());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.CreateActivityTaskDescriptionInput),
                        childAtPosition(
                                allOf(withId(R.id.AddTaskActivityPage),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Test Assertion"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.TaskSpinnerSelector),
                        childAtPosition(
                                allOf(withId(R.id.AddTaskActivityPage),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatTextView.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.CreateTaskActivitySubmitButton), withText("Submit Task"),
                        childAtPosition(
                                allOf(withId(R.id.AddTaskActivityPage),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatButton3.perform(click());

        pressBack();

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.taskFragmentTextView), withText("1 Test Task\nIN_PROGRESS"),
                        withParent(withParent(withId(R.id.MainActivityTaskRecyclerView))),
                        isDisplayed()));
        textView2.check(matches(withText("1 Test Task IN_PROGRESS")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.taskFragmentTextView), withText("1 Test Task\nIN_PROGRESS"),
                        withParent(withParent(withId(R.id.MainActivityTaskRecyclerView))),
                        isDisplayed()));
        textView3.check(matches(withText("1 Test Task IN_PROGRESS")));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.MainActivityTaskRecyclerView),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                6)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.TaskDetailsActivityTitle), withText("Test Task"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView4.check(matches(withText("Test Task")));
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
