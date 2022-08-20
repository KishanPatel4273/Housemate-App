package edu.vassar.cmpu203.nextgenpos;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Test;

import edu.vassar.cmpu203.nextgenpos.controller.ControllerActivity;


public class AddItemsTest {

    @org.junit.Rule
    ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    @Test
    public void testAddItems(){

        ViewInteraction lineItems = Espresso.onView(ViewMatchers.withId(R.id.lineItemsTextView));
        lineItems.check(ViewAssertions.matches(ViewMatchers.withText(R.string.line_items_text_default)));

        ViewInteraction name = Espresso.onView(ViewMatchers.withId(R.id.nameEditText))
                .perform(ViewActions.typeText("avocado"));

        ViewInteraction qty = Espresso.onView(ViewMatchers.withId(R.id.qtyEditText))
                .perform(ViewActions.typeText("900"));

        ViewInteraction button = Espresso.onView(ViewMatchers.withId(R.id.addItemButton))
                .perform(ViewActions.click());

        lineItems.check(ViewAssertions.matches(ViewMatchers.withSubstring("900 units of avocado")));

        name.perform(ViewActions.typeText("guacamole"));
        qty.perform(ViewActions.typeText("10"));
        button.perform(ViewActions.click());

        lineItems.check(ViewAssertions.matches(ViewMatchers.withSubstring("900 units of avocado")));
        lineItems.check(ViewAssertions.matches(ViewMatchers.withSubstring("10 units of guacamole")));
    }

}