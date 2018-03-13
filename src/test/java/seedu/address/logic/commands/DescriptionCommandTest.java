package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Description;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DescriptionCommand.
 */
public class DescriptionCommandTest {

    public static final String DESCRIPTION_STUB = "Some description";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addDescriptionUnfilteredList_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withDescription(DESCRIPTION_STUB).build();

        DescriptionCommand descriptionCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getDescription().value);

        String expectedMessage = String.format(DescriptionCommand.MESSAGE_ADD_DESCRIPTION_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);

        assertCommandSuccess(descriptionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteDescriptionUnfilteredList_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withDescription("").build();

        DescriptionCommand descriptionCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getDescription().toString());

        String expectedMessage = String.format(DescriptionCommand.MESSAGE_DELETE_DESCRIPTION_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);

        assertCommandSuccess(descriptionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withDescription(DESCRIPTION_STUB).build();

        DescriptionCommand descriptionCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getDescription().value);

        String expectedMessage = String.format(DescriptionCommand.MESSAGE_ADD_DESCRIPTION_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);

        assertCommandSuccess(descriptionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DescriptionCommand descriptionCommand = prepareCommand(outOfBoundIndex, VALID_DESCRIPTION_BOB);

        assertCommandFailure(descriptionCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DescriptionCommand descriptionCommand = prepareCommand(outOfBoundIndex, VALID_DESCRIPTION_BOB);

        assertCommandFailure(descriptionCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withDescription(DESCRIPTION_STUB).build();
        DescriptionCommand descriptionCommand = prepareCommand(INDEX_FIRST_PERSON, DESCRIPTION_STUB);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // description -> first person description changed
        descriptionCommand.execute();
        undoRedoStack.push(descriptionCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person modified again
        expectedModel.updatePerson(personToModify, modifiedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DescriptionCommand descriptionCommand = prepareCommand(outOfBoundIndex, "");

        // execution failed -> descriptionCommand not pushed into undoRedoStack
        assertCommandFailure(descriptionCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Person#description} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DescriptionCommand descriptionCommand = prepareCommand(INDEX_FIRST_PERSON, DESCRIPTION_STUB);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withDescription(DESCRIPTION_STUB).build();
        // description -> modifies second person in unfiltered person list / first person in filtered person list
        descriptionCommand.execute();
        undoRedoStack.push(descriptionCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToModify, modifiedPerson);
        assertNotEquals(personToModify, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> modifies same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final DescriptionCommand standardCommand = new DescriptionCommand(INDEX_FIRST_PERSON, new Description(VALID_DESCRIPTION_AMY));

        // same values -> returns true
        DescriptionCommand commandWithSameValues = new DescriptionCommand(INDEX_FIRST_PERSON, new Description(VALID_DESCRIPTION_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DescriptionCommand(INDEX_SECOND_PERSON, new Description(VALID_DESCRIPTION_AMY))));

        // different description -> returns false
        assertFalse(standardCommand.equals(new DescriptionCommand(INDEX_FIRST_PERSON, new Description(VALID_DESCRIPTION_BOB))));
    }

    /**
     * Returns an {@code DescriptionCommand} with parameters {@code index} and {@code description}.
     */
    private DescriptionCommand prepareCommand(Index index, String description) {
        DescriptionCommand descriptionCommand = new DescriptionCommand(index, new Description(description));
        descriptionCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return descriptionCommand;
    }
}
