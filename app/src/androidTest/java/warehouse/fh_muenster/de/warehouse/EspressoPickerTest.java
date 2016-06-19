package warehouse.fh_muenster.de.warehouse;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.widget.DrawerLayout;
import android.test.ApplicationTestCase;

import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
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
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class EspressoPickerTest {

    private Context mockContext;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    /**
     * Testet, ob der Login funktioniert.
     */
    @Before
    public void setMock(){
        Config.setIsMock(true);
        Config.setIsMockScanner(true);
    }

    @Test
    /**
     * Testet, ob der Login erfolgreich durchgeführt werden kann.
     */
    public void testLogin(){
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("1"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
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
     * Testet, ob der Login mit falschen Benutzerdaten fehlschlägt.
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
     * Testet, ob die Scanner-Activity startet.
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

    @Test
    /**
     * Testet, ob eine Kommission mit der Id 90000 gestartet wird.
     */
    public void testStartCommission(){
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("1"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            onView(withId(90000)).perform(click());
            intended(hasComponent(CommissionArtikel.class.getName()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            Intents.release();
        }
    }

    @Test
    /**
     * Testet, ob durch das Menü die offenen Kommissionen angzeigt werden können.
     */
    public void testOpenCommissions(){
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("1"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
            onView(withText("Offene Kommissionen")).perform(click());
            onView(withId(R.id.commissioningOverview_table_head)).check(matches(isDisplayed()));;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            Intents.release();
        }
    }
    @Test
    /**
     * Testet, ob der Logout über das Menü funktioniert.
     */
    public void testLogout(){
        try {

            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("1"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
            onView(withText("Logout")).perform(click());
            intended(hasComponent(LoginActivity.class.getName()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            Intents.release();
        }
    }

    @Test
    /**
     * Testet, ob eine Kommission komplett durchgeführt werden kann.
     */
    public void testStartCommissionFinished(){
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("1"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            onView(withId(90000)).perform(click());
            onView(withId(R.id.commission_articel_scann_btn)).perform(click());
            onView(withId(R.id.commission_artikel_artikel_commession_edit))
                    .perform(typeText("20"), closeSoftKeyboard());
            onView(withId(R.id.weiter_btn)).perform(click());
            intended(hasComponent(CommissioningOverview.class.getName()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            Intents.release();
        }
    }
    @Test
    /**
     * Testet, ob eine Kommission angenommen werden kann.
     */
    public void testAcceptCommission(){
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("1"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
            onView(withText("Offene Kommissionen")).perform(click());
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.close());
            onView(withId(90001)).perform(click());
            onView(withId(90001)).check(doesNotExist());
    }

    @Test
    /**
     * Testet, ob eine Fehlmenge übersendet werden kann.
     */
    public void testStockOut(){
        try {
            Intents.init();
            onView(withId(R.id.mitarbeiterNr_txt))
                    .perform(typeText("1"), closeSoftKeyboard());
            onView(withId(R.id.password_txt))
                    .perform(typeText("geheim"), closeSoftKeyboard());
            onView(withId(R.id.login_btn)).perform(click());
            onView(withId(90000)).perform(click());
            onView(withId(R.id.commission_articel_scann_btn)).perform(click());
            onView(withId(R.id.commission_artikel_fehlmengeMelden)).perform(click());
            onView(withId(R.id.stock_out_istMenge_txt))
                    .perform(typeText("20"), closeSoftKeyboard());
            onView(withId(R.id.stock_out_melden_btn)).perform(click());

            intended(hasComponent(CommissionArtikel.class.getName()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            Intents.release();
        }
    }

}