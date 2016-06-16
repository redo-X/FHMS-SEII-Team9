package warehouse.fh_muenster.de.warehouse;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.test.ApplicationTestCase;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class LoginTest {

    private Context mockContext;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    /**
     * Checks if the Login is successful
     */
    @Test
    public void testLogin(){
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("123"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("123"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            intended(hasComponent(CommissioningOverview.class.getName()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            Intents.release();
        }
    }

    /**
     * Checks if the Login Fail with wrong user data
     */
    @Test
    public void testLoginFail(){
        onView(withId(R.id.mitarbeiterNr_txt))
                .perform(typeText("456"), closeSoftKeyboard());
        onView(withId(R.id.password_txt))
                .perform(typeText("auhkfsduhjklf"), closeSoftKeyboard());
        onView(withId(R.id.login_btn)).perform(click());
        onView(withId(R.id.login_btn)).check(matches(isDisplayed()));
    }


    /**
     * Checks if the Scanner start by clicking the Scanner Button
     */
    @Test
    public void testScanner(){
        try {
            Intents.init();
            onView(withId(R.id.loginScann_btn)).perform(click());
            intended(hasComponent(Scanner.class.getName()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            Intents.release();
        }
    }


}