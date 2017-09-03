package com.example.jiyoung.firebaseexample;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.jiyoung.firebaseexample.ui.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


/**
 * UI를 테스트 하기위해서는 Espresso, Activity 및 Service 테스틑는 JUnit4 사용
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.jiyoung.firebaseexample", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule(LoginActivity.class);

    @Test
    public void listGoesOverTheFold() {
        onView(withText(R.string.main_sign_in)).check(matches(isDisplayed())); // 해당 텍스트를 가진 뷰가 표시되었는지 확
        onView(withId(R.id.et_email)).check(matches(isDisplayed()));  // 해당뷰가 화면에 표시되었는지 확인
        onView(withId(R.id.btn_ok)).perform(click()); // 해당뷰 클릭
    }

}
