package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void equals() {
        Description description = new Description("Hello");

        // same object -> returns true
        assertTrue(description.equals(description));

        // same values -> returns true
        Description descriptionCopy = new Description(description.value);
        assertTrue(description.equals(descriptionCopy));

        // different types -> returns false
        assertFalse(description.equals(1));

        // null -> returns false
        assertFalse(description.equals(null));

        // different description -> returns false
        Description differentDescription = new Description("Bye");
        assertFalse(description.equals(differentDescription));
    }
}
