package warehouse.fh_muenster.de.warehouse;

import android.content.Context;
import android.support.test.espresso.Espresso;
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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class EspressoWarehousemanTest {

    private Context mockContext;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Before
    public void setMock() {
        Config.setIsMock(true);
        Config.setIsMockScanner(true);
    }

    /**
     * Testet, ob der Login für den Lageristen funktioniert.
     */
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
     * Tests, ob der Login für den Lageristen mit einem falschen Kennwort fehlschlägt.
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
     * Testet, ob der Lagerist über das Menu auf die Lagerbestände zugreifen kann.
     */
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

    /**
     * Testet, ob der Lagerist auf einen Artikel in den Lagerbeständen klicken kann.
     */
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

    /**
     * Testet, ob der Lagerist eine Menge ändern kann.
     */
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
            onView(withText("Menge wirklich ändern?")).check(matches(isDisplayed()));
            onView(withId(android.R.id.button1)).perform(click());
            intended(hasComponent(Stock.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intents.release();
        }
    }

    /**
     * Testet, ob man sich mit einem Doppelklick auf die Zurück-Taste aus den Lagerbeständen ausloggen kann.
     */
    @Test
    public void testLogoutInStock() {
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("2"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
            onView(withText("Lagerbestände")).perform(click());
            Espresso.pressBack();
            Espresso.pressBack();
            intended(hasComponent(LoginActivity.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intents.release();
        }
    }

    /**
     * Testet, ob sich der Nutzer bei einfachem Klick auf die Zurück-Taste nicht auslogt.
     */
    @Test
    public void testLogoutInStockFail() {
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("2"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
            onView(withText("Lagerbestände")).perform(click());
            Espresso.pressBack();
            intended(hasComponent(Stock.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intents.release();
        }
    }
}