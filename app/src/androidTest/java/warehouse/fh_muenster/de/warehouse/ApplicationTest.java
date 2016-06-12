package warehouse.fh_muenster.de.warehouse;

import android.app.Application;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.test.ApplicationTestCase;
import static android.support.test.espresso.intent.Intents.intended;


import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest  {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    /**
     * Checks if the Login is successful
     */
    @Test
    public void loginTest(){
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("123"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("123"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            intended(hasComponent(mockMenue.class.getName()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            Intents.release();
        }
    }



}