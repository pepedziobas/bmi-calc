package com.example.pepe.labories1;

/**
 * Created by Pepe on 2017-03-28.
 */

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class EmptyEditTextsInfluenceOnResultTextView {

    private float[] onlyOneValueTable;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        onlyOneValueTable = new float[]{-1f,0f,-34.6f,20f,99f,34.5f,23f,0.3f,0.99f,1f};
    }

    @Test
    public void onlyOneValueInWeightKg() {
        for(int i = 0; i<onlyOneValueTable.length; i++) {
            // Type text and then press the button.
            onView(withId(R.id.editTextWeight))
                    .perform(typeText(String.valueOf(onlyOneValueTable[i])), closeSoftKeyboard());
            onView(withId(R.id.calculateBMIButton)).perform(click());

            // Check that the text was changed.
            onView(withId(R.id.textViewResult))
                    .check(matches(withText("")));

            onView(withId(R.id.editTextWeight))
                    .perform(clearText());
        }
    }

    @Test
    public void onlyOneValueInWeightLbs() {

        onView(withId(R.id.switchBtn2)).perform(click());

        for(int i = 0; i<onlyOneValueTable.length; i++) {
            // Type text and then press the button.
            onView(withId(R.id.editTextWeight))
                    .perform(typeText(String.valueOf(onlyOneValueTable[i])), closeSoftKeyboard());
            onView(withId(R.id.calculateBMIButton)).perform(click());

            // Check that the text was changed.
            onView(withId(R.id.textViewResult))
                    .check(matches(withText("")));

            onView(withId(R.id.editTextWeight))
                    .perform(clearText());
        }
    }
}
