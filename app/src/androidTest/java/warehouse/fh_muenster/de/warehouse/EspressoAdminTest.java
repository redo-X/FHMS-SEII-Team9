package warehouse.fh_muenster.de.warehouse;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.widget.DrawerLayout;
import android.test.ApplicationTestCase;

import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import warehouse.fh_muenster.de.warehouse.Server.Config;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Marco on 18.06.2016.
 */
public class EspressoAdminTest {

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
     * Testet, ob der Login für den Admin funktioniert.
     */
    @Test
    public void testLogin() {
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("3"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            intended(hasComponent(AdminActivity.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intents.release();
        }
    }

    /**
     * Testet, ob der Login für den Admin mit einem falschen Kennwort fehlschlägt.
     */
    @Test
    public void testLoginFail() {
        onView(withId(R.id.mitarbeiterNr_txt))
                .perform(typeText("3"), closeSoftKeyboard());
        onView(withId(R.id.password_txt))
                .perform(typeText("Kennwort"), closeSoftKeyboard());
        onView(withId(R.id.login_btn)).perform(click());
        onView(withId(R.id.login_btn)).check(matches(isDisplayed()));
    }

    /**
     * Testet, ob man sich mit einem Doppelklick auf die Zurück-Taste in der AdminActivity ausloggen kann.
     */
    @Test
    public void testLogoutInAdmin() {

            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("3"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            pressBack();
            pressBack();
    }

    /**
     * Testet, ob sich der Admin bei einfachem Klick auf die Zurück-Taste nicht auslogt.
     */
    @Test
    public void testLogoutInAdminFail() {
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("3"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            pressBack();
            intended(hasComponent(AdminActivity.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intents.release();
        }
    }

    /**
     * Testet, ob der Admin neue Artikel hinzufügen kann.
     */
    @Test
    public void AdminTestAdd() {
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("3"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            intended(hasComponent(AdminActivity.class.getName()));
            onView(withId(R.id.admin_article_code))
                    .perform(typeText("12345"), closeSoftKeyboard());
            onView(withId(R.id.admin_article_name))
                    .perform(typeText("Artikelname"), closeSoftKeyboard());
            onView(withId(R.id.admin_article_lagerort))
                    .perform(typeText("Lager5"), closeSoftKeyboard());
            onView(withId(R.id.admin_article_save)).perform(click());
            intended(hasComponent(AdminActivity.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intents.release();
        }
    }
}
