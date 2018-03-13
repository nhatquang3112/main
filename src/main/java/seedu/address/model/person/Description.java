package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's description in the address book.
 * Guarantees: immutable; is always valid
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Person descriptions can take any values, can even be blank";

    public final String value;

    public Description(String description) {
        requireNonNull(description);
        this.value = description;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
