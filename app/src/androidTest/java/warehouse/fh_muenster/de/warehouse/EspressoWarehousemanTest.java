package warehouse.fh_muenster.de.warehouse;

import android.content.Context;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import warehouse.fh_muenster.de.warehouse.Server.Config;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagKey;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class EspressoWarehousemanTest {

    private Context mockContext;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    /**
     * Checks if the Login is successful
     */
    @Before
    public void setMock() {
        Config.setIsMock(true);
        Config.setIsMockScanner(true);
    }

    @Test
    public void testLogin() {
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("2"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            intended(hasComponent(CommissioningOverview.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intents.release();
        }
    }

    /**
     * Checks if the Login Fail with wrong user data
     */
    @Test
    public void testLoginFail() {
        onView(withId(R.id.mitarbeiterNr_txt))
                .perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.password_txt))
                .perform(typeText("falschesPasswort"), closeSoftKeyboard());
        onView(withId(R.id.login_btn)).perform(click());
        onView(withId(R.id.login_btn)).check(matches(isDisplayed()));
    }

    /**
     * Checks if the Scanner start by clicking the Scanner Button
     */
    @Test
    public void testScanner() {
        try {
            Intents.init();
            onView(withId(R.id.loginScann_btn)).perform(click());
            intended(hasComponent(Scanner.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intents.release();
        }
    }

    @Test
    public void testStartStock() {
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("2"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
            onView(withText("Lagerbestände")).perform(click());
            onView(withId(R.id.stock_table_layout)).check(matches(isDisplayed()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intents.release();
        }
    }

    @Test
    public void testStartStockAmendment() {
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("2"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
            onView(withText("Lagerbestände")).perform(click());
            onView(withText("Test")).perform(click());
            intended(hasComponent(StockAmendment.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intents.release();
        }
    }

    @Test
    public void testAddStockQuantity() {
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("2"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
            onView(withText("Lagerbestände")).perform(click());
            onView(withText("Test")).perform(click());
            onView(withId(R.id.quantity_txt))
                    .perform(typeText("10"), closeSoftKeyboard());
            onView(withId(R.id.button_alter)).perform(click());
            intended(hasComponent(Stock.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intents.release();
        }
    }

    @Test
    public void testLogout() {
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("2"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
            onView(withText("Logout")).perform(click());
            intended(hasComponent(LoginActivity.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intents.release();
        }
    }


}