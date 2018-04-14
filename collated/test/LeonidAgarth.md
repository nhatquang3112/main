# LeonidAgarth
###### \java\seedu\address\logic\commands\AddEventCommandTest.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagNotFoundException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;
import seedu.address.testutil.EventBuilder;

public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = getAddEventCommandForEvent(validEvent, modelStub).execute();

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateEventException();
        Event validEvent = new EventBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEventCommand.MESSAGE_DUPLICATE_EVENT);

        getAddEventCommandForEvent(validEvent, modelStub).execute();
    }

    @Test
    public void equals() {
        Event f1Race = new EventBuilder().build();
        Event iLight = new Event("iLight", "Marina Bay", "01/04/2018", "1930", "2359");
        AddEventCommand addF1Command = new AddEventCommand(f1Race);
        AddEventCommand addILightCommand = new AddEventCommand(iLight);

        // same object -> returns true
        assertTrue(addF1Command.equals(addF1Command));

        // same values -> returns true
        AddEventCommand addF1CommandCopy = new AddEventCommand(f1Race);
        assertTrue(addF1Command.equals(addF1CommandCopy));

        // different types -> returns false
        assertFalse(addF1Command.equals(1));

        // null -> returns false
        assertFalse(addF1Command.equals(null));

        // different event -> returns false
        assertFalse(addF1Command.equals(addILightCommand));
    }

    /**
     * Generates a new AddEventCommand with the details of the given event.
     */
    private AddEventCommand getAddEventCommandForEvent(Event event, Model model) {
        AddEventCommand command = new AddEventCommand(event);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) throws DuplicateEventException {
            fail("This method should not be called.");
        }

        @Override
        public void addToDo(ToDo todo) throws DuplicateToDoException {
            fail("This method should not be called.");
        }

        @Override
        public void addGroup(Group group) throws DuplicateGroupException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTag(Tag target, Tag editedTag) throws TagNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void removeTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData
        ) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteToDo(ToDo target) throws ToDoNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteGroup(Group target) throws GroupNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void updateToDo(ToDo target, ToDo editedToDo) throws DuplicateToDoException, ToDoNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateGroup(Group target, Group editedGroup) throws DuplicateGroupException,
                GroupNotFoundException {
            fail("This method should not be called.");
        }


        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ToDo> getFilteredToDoList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredToDoList(Predicate<ToDo> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void indicateCalendarChanged() {
            fail("This method should not be called.");
        }

        @Override
        public void indicateTimetableChanged() {
            fail("This method should not be called.");
        }

        @Override
        public boolean calendarIsViewed() {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void switchView() {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateEventException when trying to add a event.
     */
    private class ModelStubThrowingDuplicateEventException extends ModelStub {
        @Override
        public void addEvent(Event event) throws DuplicateEventException {
            throw new DuplicateEventException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public void addEvent(Event event) throws DuplicateEventException {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### \java\seedu\address\logic\commands\ChangeTagColorCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for ChangeTagColorCommand.
 */
public class ChangeTagColorCommandTest {
    private Model model;

    @Test
    public void execute_correctFields_success() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person editedPerson = new PersonBuilder().build();
        ChangeTagColorCommand command = prepareCommand(VALID_TAG_FRIEND, VALID_TAG_COLOR_RED);

        String expectedMessage =
                String.format(ChangeTagColorCommand.MESSAGE_EDIT_TAG_SUCCESS, VALID_TAG_FRIEND, VALID_TAG_COLOR_RED);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Tag oldTag = new Tag(VALID_TAG_FRIEND);
        Tag newTag = new Tag(VALID_TAG_FRIEND, VALID_TAG_COLOR_RED);
        expectedModel.updateTag(oldTag, newTag);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagNameNotInList_failure() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person editedPerson = new PersonBuilder().build();
        ChangeTagColorCommand command = prepareCommand(VALID_TAG_HUSBAND, VALID_TAG_COLOR_RED);

        assertCommandFailure(command, model, ChangeTagColorCommand.MESSAGE_TAG_NOT_IN_LIST);
    }

    @Test
    public void equals_test() throws Exception {
        ChangeTagColorCommand command1 = prepareCommand(VALID_TAG_HUSBAND, VALID_TAG_COLOR_RED);
        ChangeTagColorCommand command2 = prepareCommand(VALID_TAG_FRIEND, VALID_TAG_COLOR_RED);
        ChangeTagColorCommand command3 = prepareCommand(VALID_TAG_FRIEND, VALID_TAG_COLOR_RED);

        assertEquals(command1, command1);
        assertEquals(command2, command3);
        assertNotEquals(command3, 1);
        assertNotEquals(command1, command2);
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private ChangeTagColorCommand prepareCommand(String name, String color) {
        ChangeTagColorCommand command = new ChangeTagColorCommand(name, color);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\ScheduleGroupCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalGroups.GROUP_A;
import static seedu.address.testutil.TypicalGroups.GROUP_C;
import static seedu.address.testutil.TypicalGroups.GROUP_G;
import static seedu.address.testutil.TypicalGroups.GROUP_H;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.events.ui.TimetableChangedEvent;
import seedu.address.database.module.Module;
import seedu.address.database.module.Schedule;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.WeeklyEvent;
import seedu.address.model.group.Group;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ScheduleGroupCommand}.
 */
public class ScheduleGroupCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validGroup_success() {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        ArrayList<WeeklyEvent> freeA = new ArrayList<>();
        Module mod = new Module("Free", "", null);
        for (String day : daysOfWeek) {
            for (int s = 800; s < 1800; s += 100) {
                Schedule sch = new Schedule("", "", "", day, "" + s, "" + (s + 100), "");
                freeA.add(new WeeklyEvent(mod, sch));
            }
        }
        assertExecutionSuccess(GROUP_A, freeA);

        ArrayList<WeeklyEvent> freeH = new ArrayList<>();
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Monday", "800", "900", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Monday", "1600", "1700", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Monday", "1700", "1800", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Tuesday", "1400", "1500", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Tuesday", "1500", "1600", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "800", "900", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "1200", "1300", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "1300", "1400", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "1600", "1700", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "1700", "1800", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "800", "900", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "900", "1000", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "1300", "1400", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "1400", "1500", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "1500", "1600", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Friday", "1200", "1300", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Friday", "1300", "1400", "")));
        assertExecutionSuccess(GROUP_H, freeH);
    }

    @Test
    public void execute_groupNotFound_failure() {
        assertExecutionFailure(GROUP_C,
                String.format(ScheduleGroupCommand.MESSAGE_GROUP_NOT_FOUND, GROUP_C.getInformation()));
        assertExecutionFailure(GROUP_G,
                String.format(ScheduleGroupCommand.MESSAGE_GROUP_NOT_FOUND, GROUP_G.getInformation()));
    }

    @Test
    public void equals() {
        ScheduleGroupCommand groupACommand = new ScheduleGroupCommand(GROUP_A);
        ScheduleGroupCommand groupHCommand = new ScheduleGroupCommand(GROUP_H);

        // same object -> returns true
        assertTrue(groupACommand.equals(groupACommand));

        // same values -> returns true
        ScheduleGroupCommand selectFirstCommandCopy = new ScheduleGroupCommand(GROUP_A);
        assertTrue(groupACommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(groupACommand.equals(1));

        // null -> returns false
        assertFalse(groupACommand.equals(null));

        // different person -> returns false
        assertFalse(groupACommand.equals(groupHCommand));
    }

    /**
     * Executes a {@code ScheduleGroupCommand} with the given {@code index},
     * and checks that {@code JumpToListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Group group, ArrayList<WeeklyEvent> freeSlots) {
        ScheduleGroupCommand scheduleGroupCommand = prepareCommand(group);

        try {
            CommandResult commandResult = scheduleGroupCommand.execute();
            assertEquals(String.format(ScheduleGroupCommand.MESSAGE_SUCCESS, group.getInformation()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        TimetableChangedEvent lastEvent = (TimetableChangedEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertTrue(lastEvent != null);
        assertTrue(lastEvent.timetable.equals(freeSlots));
    }

    /**
     * Executes a {@code ScheduleGroupCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Group group, String expectedMessage) {
        ScheduleGroupCommand scheduleGroupCommand = prepareCommand(group);

        try {
            scheduleGroupCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
            assertEquals(expectedMessage, ce.getMessage());
        }
    }

    /**
     * Returns a {@code ScheduleGroupCommand} with parameters {@code index}.
     */
    private ScheduleGroupCommand prepareCommand(Group group) {
        ScheduleGroupCommand scheduleGroupCommand = new ScheduleGroupCommand(group);
        scheduleGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return scheduleGroupCommand;
    }
}
```
###### \java\seedu\address\logic\commands\SwitchCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SwitchCommand.
 */
public class SwitchCommandTest {
    private Model model;
    private Model expectedModel;
    private SwitchCommand switchCommand;

    @Test
    public void execute_calendarToTimetable_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.switchView();

        switchCommand = new SwitchCommand();
        switchCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(switchCommand, model, SwitchCommand.MESSAGE_SUCCESS_TIMETABLE, expectedModel);
    }

    @Test
    public void execute_timetableToCalendar_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.switchView();
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        switchCommand = new SwitchCommand();
        switchCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(switchCommand, expectedModel, SwitchCommand.MESSAGE_SUCCESS_CALENDAR, model);
    }
}
```
###### \java\seedu\address\logic\parser\AddEventCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_END_TIME_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_TIME_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_TIME_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_VENUE_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_VENUE_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_NDP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder().withName(VALID_EVENT_NAME_NDP).withVenue(VALID_EVENT_VENUE_NDP)
                .withDate(VALID_EVENT_DATE_NDP).withStartTime(VALID_EVENT_START_TIME_NDP)
                .withEndTime(VALID_EVENT_END_TIME_NDP).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple names - last name accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_F1 + EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple venues - last venue accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_F1 + EVENT_VENUE_DESC_NDP
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple dates - last date accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_F1
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple start times - last time accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_F1 + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple tags - all accepted
        Event expectedEventMultipleTags = new EventBuilder().withName(VALID_EVENT_NAME_NDP)
                .withVenue(VALID_EVENT_VENUE_NDP).withDate(VALID_EVENT_DATE_NDP)
                .withStartTime(VALID_EVENT_START_TIME_NDP).withEndTime(VALID_EVENT_END_TIME_NDP).build();
        assertParseSuccess(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEventMultipleTags));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_EVENT_NAME_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP, expectedMessage);

        // missing venue prefix
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + VALID_EVENT_VENUE_NDP + EVENT_DATE_DESC_NDP
                + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + VALID_EVENT_DATE_NDP
                + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP, expectedMessage);

        // missing start time prefix
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                + VALID_EVENT_START_TIME_NDP + EVENT_END_TIME_DESC_NDP, expectedMessage);

        // missing end time prefix
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                + EVENT_START_TIME_DESC_NDP + VALID_EVENT_END_TIME_NDP, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EVENT_NAME_NDP + VALID_EVENT_VENUE_NDP + VALID_EVENT_DATE_NDP
                + VALID_EVENT_START_TIME_NDP + VALID_EVENT_END_TIME_NDP, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                Event.MESSAGE_NAME_CONSTRAINTS);

        // invalid venue
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + INVALID_EVENT_VENUE_DESC + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                Event.MESSAGE_VENUE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + INVALID_EVENT_DATE_DESC
                        + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                Event.MESSAGE_DATE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + INVALID_EVENT_START_TIME_DESC + EVENT_END_TIME_DESC_NDP,
                Event.MESSAGE_TIME_CONSTRAINTS);

        // invalid end time
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_NDP + INVALID_EVENT_END_TIME_DESC,
                Event.MESSAGE_TIME_CONSTRAINTS);

        // endTime before Start
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + " " + PREFIX_END_TIME + "0000" + EVENT_START_TIME_DESC_NDP,
                AddEventCommand.MESSAGE_END_BEFORE_START);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                + INVALID_EVENT_START_TIME_DESC + EVENT_END_TIME_DESC_NDP, Event.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_addEvent() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(EventUtil.getAddEventCommand(event));
        assertEquals(new AddEventCommand(event), command);
    }

    @Test
    public void parseCommand_addEventAlias() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(AddEventCommand.COMMAND_ALIAS + " "
                + EventUtil.getEventDetails(event));
        assertEquals(new AddEventCommand(event), command);
    }

    @Test
    public void parseCommand_changeTagColor() throws Exception {
        String tagName = "friends";
        String tagColor = "red";
        ChangeTagColorCommand command = (ChangeTagColorCommand) parser.parseCommand(ChangeTagColorCommand.COMMAND_WORD
                + " " + tagName + " " + tagColor);
        assertEquals(new ChangeTagColorCommand(tagName, tagColor), command);
    }

    @Test
    public void parseCommand_changeTagColorAlias() throws Exception {
        String tagName = "friends";
        String tagColor = "red";
        ChangeTagColorCommand command = (ChangeTagColorCommand) parser.parseCommand(ChangeTagColorCommand.COMMAND_ALIAS
                + " " + tagName + " " + tagColor);
        assertEquals(new ChangeTagColorCommand(tagName, tagColor), command);
    }

    @Test
    public void parseCommand_switch() throws Exception {
        assertTrue(parser.parseCommand(SwitchCommand.COMMAND_WORD) instanceof SwitchCommand);
        assertTrue(parser.parseCommand(SwitchCommand.COMMAND_WORD + " view") instanceof SwitchCommand);
    }

    @Test
    public void parseCommand_switchAlias() throws Exception {
        assertTrue(parser.parseCommand(SwitchCommand.COMMAND_ALIAS) instanceof SwitchCommand);
        assertTrue(parser.parseCommand(SwitchCommand.COMMAND_ALIAS + " view") instanceof SwitchCommand);
    }

```
###### \java\seedu\address\logic\parser\ChangeTagColorCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.model.tag.Tag;

/**
 * Tests for the parsing of input arguments and creating a new ChangeTagColorCommand object
 */
public class ChangeTagColorCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeTagColorCommand.MESSAGE_USAGE);

    private ChangeTagColorCommandParser parser = new ChangeTagColorCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no color specified
        assertParseFailure(parser, VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // unsupported color specified
        assertParseFailure(parser, VALID_TAG_FRIEND + INVALID_TAG_COLOR,
                Tag.MESSAGE_TAG_COLOR_CONSTRAINTS);

        // invalid tag name
        assertParseFailure(parser, INVALID_TAG_DESC + " " + VALID_TAG_COLOR_RED,
                Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_validValue_success() {
        // unsupported color specified
        assertParseSuccess(parser, VALID_TAG_FRIEND + " " + VALID_TAG_COLOR_RED,
                new ChangeTagColorCommand(VALID_TAG_FRIEND, VALID_TAG_COLOR_RED));
    }
}
```
###### \java\seedu\address\logic\parser\ScheduleGroupCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INFORMATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFORMATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ScheduleGroupCommand;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;

/**
 * Tests for the parsing of input arguments and creating a new ScheduleGroupCommand object
 */
public class ScheduleGroupCommandParserTest {

    private ScheduleGroupCommandParser parser = new ScheduleGroupCommandParser();

    @Test
    public void parse_validArgs_returnsScheduleGroupCommand() {
        Group group = new Group(new Information(VALID_INFORMATION));
        assertParseSuccess(parser, VALID_INFORMATION, new ScheduleGroupCommand(group));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_INFORMATION,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleGroupCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\event\EventTest.java
``` java
package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_NDP;

import org.junit.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.EventBuilder;

public class EventTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Event(null, null, null, null, null));
    }

    @Test
    public void isValidEventName_null_throwsNullPointerException() {
        // null event name
        Assert.assertThrows(NullPointerException.class, () -> Event.isValidName(null));
    }

    @Test
    public void isValidName() {
        assertTrue(Event.isValidName(VALID_EVENT_NAME_F1));
        assertTrue(Event.isValidName(VALID_EVENT_NAME_NDP));
        assertFalse(Event.isValidName(INVALID_EVENT_NAME_DESC));
    }

    @Test
    public void isValidDate() {
        assertTrue(Event.isValidDate(VALID_EVENT_DATE_F1));
        assertTrue(Event.isValidDate(VALID_EVENT_DATE_NDP));
        assertFalse(Event.isValidDate(INVALID_EVENT_DATE_DESC));
    }

    @Test
    public void isValidTime() {
        assertTrue(Event.isValidTime(VALID_EVENT_START_TIME_F1));
        assertTrue(Event.isValidTime(VALID_EVENT_START_TIME_NDP));
        assertTrue(Event.isValidTime(VALID_EVENT_END_TIME_F1));
        assertTrue(Event.isValidTime(VALID_EVENT_END_TIME_NDP));
        assertFalse(Event.isValidTime(INVALID_EVENT_START_TIME_DESC));
        assertFalse(Event.isValidTime(INVALID_EVENT_END_TIME_DESC));
    }

    @Test
    public void equals() {
        Event f1Race1 = new EventBuilder().build();
        Event f1Race2 = new EventBuilder().withName(VALID_EVENT_NAME_F1).withDate(VALID_EVENT_DATE_F1)
                .withStartTime(VALID_EVENT_START_TIME_F1).withEndTime(VALID_EVENT_END_TIME_F1).build();

        assertTrue(f1Race1.equals(f1Race1));
        assertFalse(f1Race1.equals(1));
        assertTrue(f1Race1.equals(f1Race2));
        assertFalse(f1Race1.equals(new Event()));
    }

    @Test
    public void toString_test() {
        Event f1Race1 = new EventBuilder().build();
        Event f1Race2 = new EventBuilder().withName(VALID_EVENT_NAME_F1).withDate(VALID_EVENT_DATE_F1)
                .withStartTime(VALID_EVENT_START_TIME_F1).withEndTime(VALID_EVENT_END_TIME_F1).build();

        assertTrue(f1Race1.toString().equals(f1Race1.toString()));
        assertTrue(f1Race1.toString().equals(f1Race2.toString()));
        assertFalse(f1Race1.toString().equals(new Event().toString()));
    }
}
```
###### \java\seedu\address\model\event\WeeklyEventTest.java
``` java
package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_NDP;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

import seedu.address.database.module.Module;
import seedu.address.database.module.Schedule;

public class WeeklyEventTest {

    private WeeklyEvent event1 = new WeeklyEvent("CS2101", "COM1", "1500", "1600", "WEDNESDAY");
    private WeeklyEvent event2 = new WeeklyEvent(new Module("CS2103", "Software Engineer"), new Schedule());
    private WeeklyEvent event3 = new WeeklyEvent(new Module("CS2103", "Software Engineer"), new Schedule());
    private WeeklyEvent event4 = new WeeklyEvent("CS2103T", "I3", "1500", "1700", "WEDNESDAY");
    private WeeklyEvent event5 = new WeeklyEvent("CS2102", "COM2", "2000", "2100", "WEDNESDAY");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WeeklyEvent(null, null, null, null, (String[]) null));
        assertThrows(NullPointerException.class, () -> new WeeklyEvent(null, null));
    }

    @Test
    public void isValidWeeklyEventName_null_throwsNullPointerException() {
        // null event name
        assertThrows(NullPointerException.class, () -> WeeklyEvent.isValidName(null));
    }

    @Test
    public void isValidName() {
        assertTrue(WeeklyEvent.isValidName(VALID_EVENT_NAME_F1));
        assertTrue(WeeklyEvent.isValidName(VALID_EVENT_NAME_NDP));
        assertFalse(WeeklyEvent.isValidName(INVALID_EVENT_NAME_DESC));
    }

    @Test
    public void isValidDate() {
        assertTrue(WeeklyEvent.isValidDate(VALID_EVENT_DATE_F1));
        assertTrue(WeeklyEvent.isValidDate(VALID_EVENT_DATE_NDP));
        assertFalse(WeeklyEvent.isValidDate(INVALID_EVENT_DATE_DESC));
    }

    @Test
    public void isValidTime() {
        assertTrue(WeeklyEvent.isValidTime(VALID_EVENT_START_TIME_F1));
        assertTrue(WeeklyEvent.isValidTime(VALID_EVENT_START_TIME_NDP));
        assertTrue(WeeklyEvent.isValidTime(VALID_EVENT_END_TIME_F1));
        assertTrue(WeeklyEvent.isValidTime(VALID_EVENT_END_TIME_NDP));
        assertFalse(WeeklyEvent.isValidTime(INVALID_EVENT_START_TIME_DESC));
        assertFalse(WeeklyEvent.isValidTime(INVALID_EVENT_END_TIME_DESC));
    }

    @Test
    public void equals_test() {
        assertTrue(event1.equals(event1));
        assertTrue(event2.equals(event3));
        assertFalse(event1.equals(1));
        assertFalse(event1.equals(event2));
    }

    @Test
    public void clash() {
        assertTrue(event1.clash(event1));
        assertTrue(event1.clash(event4));
        assertTrue(event2.clash(event3));
        assertFalse(event1.clash(event2));
        assertFalse(event1.clash(event5));
        assertFalse(event3.clash(event5));
    }

    @Test
    public void toString_test() {
        assertTrue(event1.toString().equals(event1.toString()));
        assertFalse(event1.toString().equals(event2.toString()));
    }
}
```
###### \java\seedu\address\model\group\GroupTest.java
``` java
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Group groupA = new GroupBuilder().withInformation("Group A").build();
    private Group groupA2 = new GroupBuilder().withInformation("Group A").build();
    private Group groupB = new GroupBuilder().withInformation("Group B").build();


    @Test
    public void hashCodeAndString_test() {
        assertEquals(groupA.hashCode(), groupA.hashCode());
        assertEquals(groupA.hashCode(), groupA2.hashCode());
        assertNotEquals(groupA.hashCode(), groupB.hashCode());

        assertEquals(groupA.toString(), groupA.toString());
        assertEquals(groupA.toString(), groupA2.toString());
        assertNotEquals(groupA.toString(), groupB.toString());
    }

    @Test
    public void addPerson() throws Exception {
        groupA.addPerson(ALICE);
        assertNotEquals(groupA, groupA2);
        groupA2.addPerson(ALICE);
        assertEquals(groupA, groupA2);

        groupA.addPerson(BENSON);
        groupA2.addPerson(CARL);
        assertNotEquals(groupA, groupA2);
        groupA.addPerson(CARL);
        groupA2.addPerson(BENSON);
        assertEquals(groupA, groupA2);
    }

    @Test
    public void addPerson_duplicatePerson_throwsException() throws Exception {
        groupA.addPerson(ALICE);
        groupA.addPerson(BENSON);
        groupA.addPerson(CARL);
        thrown.expect(DuplicatePersonException.class);
        groupA.addPerson(BENSON);
    }

    @Test
    public void removePerson() throws Exception {
        groupA.addPerson(ALICE);
        groupA.addPerson(BENSON);
        groupA.addPerson(CARL);
        groupA2.addPerson(ALICE);
        groupA2.addPerson(BENSON);
        groupA2.addPerson(CARL);

        groupA.removePerson(ALICE);
        assertNotEquals(groupA, groupA2);
        groupA2.removePerson(ALICE);
        assertEquals(groupA, groupA2);

        groupA.removePerson(BENSON);
        groupA2.removePerson(CARL);
        assertNotEquals(groupA, groupA2);
        groupA.removePerson(CARL);
        groupA2.removePerson(BENSON);
        assertEquals(groupA, groupA2);
    }

    @Test
    public void removePerson_personNotFound_throwsException() throws Exception {
        groupA.addPerson(ALICE);
        groupA.addPerson(CARL);
        thrown.expect(PersonNotFoundException.class);
        groupA.removePerson(BENSON);
    }

```
###### \java\seedu\address\model\person\AddressTest.java
``` java
    @Test
    public void hashCode_variousTest() {
        Address address1 = new Address("Blk 456, Den Road, #01-355");
        Address address2 = new Address("Blk 456, Den Road, #01-355");
        Address address3 = new Address("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA");

        assertEquals(address1.hashCode(), address1.hashCode());
        assertEquals(address1.hashCode(), address2.hashCode());
        assertNotEquals(address2.hashCode(), address3.hashCode());
    }
}
```
###### \java\seedu\address\model\person\DetailTest.java
``` java
    @Test
    public void hashCode_variousTest() {
        Detail detail1 = new Detail("Likes tennis");
        Detail detail2 = new Detail("Likes tennis");
        Detail detail3 = new Detail("Has 3 dogs");

        assertEquals(detail1.hashCode(), detail1.hashCode());
        assertEquals(detail1.hashCode(), detail2.hashCode());
        assertNotEquals(detail2.hashCode(), detail3.hashCode());
    }
}
```
###### \java\seedu\address\model\person\EmailTest.java
``` java
    @Test
    public void hashCode_variousTest() {
        Email email1 = new Email("PeterJack_1190@example.com");
        Email email2 = new Email("PeterJack_1190@example.com");
        Email email3 = new Email("a@bc");

        assertEquals(email1.hashCode(), email1.hashCode());
        assertEquals(email1.hashCode(), email2.hashCode());
        assertNotEquals(email2.hashCode(), email3.hashCode());
    }
}
```
###### \java\seedu\address\model\person\NameTest.java
``` java
    @Test
    public void hashCode_variousTest() {
        Name name1 = new Name("Peter Jack");
        Name name2 = new Name("Peter Jack");
        Name name3 = new Name("Capital Tan");

        assertEquals(name1.hashCode(), name1.hashCode());
        assertEquals(name1.hashCode(), name2.hashCode());
        assertNotEquals(name2.hashCode(), name3.hashCode());
    }
}
```
###### \java\seedu\address\model\person\PhoneTest.java
``` java
    @Test
    public void hashCode_variousTest() {
        Phone phone1 = new Phone("93121534");
        Phone phone2 = new Phone("93121534");
        Phone phone3 = new Phone("124293874203154");

        assertEquals(phone1.hashCode(), phone1.hashCode());
        assertEquals(phone1.hashCode(), phone2.hashCode());
        assertNotEquals(phone2.hashCode(), phone3.hashCode());
    }
}
```
###### \java\seedu\address\model\person\TimeTableLinkTest.java
``` java
    @Test
    public void hashCode_variousTest() {
        TimeTableLink timeTableLink1 = new TimeTableLink("http://modsn.us/MYwiD");
        TimeTableLink timeTableLink2 = new TimeTableLink("http://modsn.us/MYwiD");
        TimeTableLink timeTableLink3 = new TimeTableLink("http://modsn.us/FumdA");

        assertEquals(timeTableLink1.hashCode(), timeTableLink1.hashCode());
        assertEquals(timeTableLink1.hashCode(), timeTableLink2.hashCode());
        assertNotEquals(timeTableLink2.hashCode(), timeTableLink3.hashCode());
    }
}
```
###### \java\seedu\address\model\UniqueEventListTest.java
``` java
package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_NDP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.testutil.EventBuilder;

public class UniqueEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asObservableList().remove(0);
    }

    @Test
    public void equals_sameList_true() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        assertEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList2.add(new EventBuilder().build());
        assertEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList2.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        assertEquals(uniqueEventList1, uniqueEventList2);
    }

    @Test
    public void equals_differentList_false() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        uniqueEventList2.add(new EventBuilder().build());
        assertNotEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        assertNotEquals(uniqueEventList1, uniqueEventList2);
    }

    @Test
    public void hashCode_sameList_sameResult() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        assertEquals(uniqueEventList1.hashCode(), uniqueEventList2.hashCode());

        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList2.add(new EventBuilder().build());
        assertEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList2.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        assertEquals(uniqueEventList1, uniqueEventList2);
    }

    @Test
    public void hashCode_differentList_differentResult() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        uniqueEventList2.add(new EventBuilder().build());
        assertNotEquals(uniqueEventList1.hashCode(), uniqueEventList2.hashCode());

        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        assertNotEquals(uniqueEventList1.hashCode(), uniqueEventList2.hashCode());
    }

    @Test
    public void duplicateEvent() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
    }

    @Test
    public void setEvent_editedEvent_success() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        Event ndp = new EventBuilder().withName(VALID_EVENT_NAME_NDP).build();
        Event f1 = new EventBuilder().build();
        uniqueEventList.add(ndp);
        uniqueEventList.setEvent(ndp, f1);
        uniqueEventList2.add(f1);
        assertEquals(uniqueEventList, uniqueEventList2);
    }

    @Test
    public void setEvent_wrongEvent_throwsEventNotFoundException() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event ndp = new EventBuilder().withName(VALID_EVENT_NAME_NDP).build();
        Event f1 = new EventBuilder().build();
        uniqueEventList.add(ndp);
        thrown.expect(EventNotFoundException.class);
        uniqueEventList.setEvent(f1, ndp);
    }

    @Test
    public void setEvent_duplicateEvent_throwsDuplicateEventException() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event ndp = new EventBuilder().withName(VALID_EVENT_NAME_NDP).build();
        Event f1 = new EventBuilder().build();
        uniqueEventList.add(ndp);
        uniqueEventList.add(f1);
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.setEvent(f1, ndp);
    }

    @Test
    public void removeEvent_wrongEvent_throwsEventNotFoundException() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event ndp = new EventBuilder().withName(VALID_EVENT_NAME_NDP).build();
        uniqueEventList.add(ndp);
        thrown.expect(EventNotFoundException.class);
        uniqueEventList.removeEvent(new EventBuilder().build());
    }

    @Test
    public void removeEvent_correctEvent_success() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(new EventBuilder().build());
        uniqueEventList.removeEvent(new EventBuilder().build());
        assertEquals(uniqueEventList, new UniqueEventList());
    }

    @Test
    public void setEvents_correctParameters_success() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();

        uniqueEventList2.add(new EventBuilder().build());
        uniqueEventList1.setEvents(uniqueEventList2);
        assertEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList2.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        uniqueEventList1.setEvents(uniqueEventList2);
        assertEquals(uniqueEventList1, uniqueEventList2);

        List<Event> events = new ArrayList<Event>();
        events.add(new EventBuilder().build());
        events.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        events.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        uniqueEventList1.setEvents(events);
        assertEquals(uniqueEventList1, uniqueEventList2);
    }

    @Test
    public void setEvents_null_throwsNullPointerException() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvents((List<Event>) null);
    }

    @Test
    public void iterator() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();

        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());

        Iterator<Event> iter = uniqueEventList1.iterator();
        while (iter.hasNext()) {
            uniqueEventList2.add(iter.next());
        }

        assertEquals(uniqueEventList1, uniqueEventList2);
    }
}
```
###### \java\seedu\address\model\UniqueTagListTest.java
``` java
    @Test
    public void equals_sameList_true() throws Exception {
        UniqueTagList uniqueTagList1 = new UniqueTagList();
        UniqueTagList uniqueTagList2 = new UniqueTagList();
        assertEquals(uniqueTagList1, uniqueTagList2);

        uniqueTagList1.add(new Tag(VALID_TAG_COLOR_RED));
        uniqueTagList2.add(new Tag(VALID_TAG_COLOR_RED));
        assertEquals(uniqueTagList1, uniqueTagList2);

        uniqueTagList1.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList1.add(new Tag(VALID_TAG_HUSBAND));
        uniqueTagList2.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList2.add(new Tag(VALID_TAG_HUSBAND));
        assertEquals(uniqueTagList1, uniqueTagList2);
    }

    @Test
    public void equals_differentList_false() throws Exception {
        UniqueTagList uniqueTagList1 = new UniqueTagList();
        UniqueTagList uniqueTagList2 = new UniqueTagList();
        uniqueTagList2.add(new Tag(VALID_TAG_COLOR_RED));
        assertNotEquals(uniqueTagList1, uniqueTagList2);

        uniqueTagList1.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList1.add(new Tag(VALID_TAG_COLOR_RED));
        uniqueTagList2.add(new Tag(VALID_TAG_FRIEND));
        assertNotEquals(uniqueTagList1, uniqueTagList2);
    }

    @Test
    public void hashCode_sameList_sameResult() throws Exception {
        UniqueTagList uniqueTagList1 = new UniqueTagList();
        UniqueTagList uniqueTagList2 = new UniqueTagList();
        assertEquals(uniqueTagList1.hashCode(), uniqueTagList2.hashCode());

        uniqueTagList1.add(new Tag(VALID_TAG_COLOR_RED));
        uniqueTagList2.add(new Tag(VALID_TAG_COLOR_RED));
        assertEquals(uniqueTagList1, uniqueTagList2);

        uniqueTagList1.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList1.add(new Tag(VALID_TAG_HUSBAND));
        uniqueTagList2.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList2.add(new Tag(VALID_TAG_HUSBAND));
        assertEquals(uniqueTagList1, uniqueTagList2);
    }

    @Test
    public void hashCode_differentList_differentResult() throws Exception {
        UniqueTagList uniqueTagList1 = new UniqueTagList();
        UniqueTagList uniqueTagList2 = new UniqueTagList();
        uniqueTagList2.add(new Tag(VALID_TAG_COLOR_RED));
        assertNotEquals(uniqueTagList1.hashCode(), uniqueTagList2.hashCode());

        uniqueTagList1.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList1.add(new Tag(VALID_TAG_COLOR_RED));
        uniqueTagList2.add(new Tag(VALID_TAG_FRIEND));
        assertNotEquals(uniqueTagList1.hashCode(), uniqueTagList2.hashCode());
    }

    @Test
    public void duplicateTag() throws Exception {
        UniqueTagList uniqueTagList = new UniqueTagList();
        uniqueTagList.add(new Tag(VALID_TAG_FRIEND));
        thrown.expect(UniqueTagList.DuplicateTagException.class);
        uniqueTagList.add(new Tag(VALID_TAG_FRIEND));
    }
}
```
###### \java\seedu\address\model\UniqueToDoListTest.java
``` java
    @Test
    public void equals_sameList_true() throws Exception {
        UniqueToDoList uniqueToDoList1 = new UniqueToDoList();
        UniqueToDoList uniqueToDoList2 = new UniqueToDoList();
        assertEquals(uniqueToDoList1, uniqueToDoList2);

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_E)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_E)));
        assertEquals(uniqueToDoList1, uniqueToDoList2);

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList1.add(new ToDo(new Content(VALID_CONTENT)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList2.add(new ToDo(new Content(VALID_CONTENT)));
        assertEquals(uniqueToDoList1, uniqueToDoList2);
    }

    @Test
    public void equals_differentList_false() throws Exception {
        UniqueToDoList uniqueToDoList1 = new UniqueToDoList();
        UniqueToDoList uniqueToDoList2 = new UniqueToDoList();
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_E)));
        assertNotEquals(uniqueToDoList1, uniqueToDoList2);

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList1.add(new ToDo(new Content(CONTENT_E)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_B)));
        assertNotEquals(uniqueToDoList1, uniqueToDoList2);
    }

    @Test
    public void hashCode_sameList_sameResult() throws Exception {
        UniqueToDoList uniqueToDoList1 = new UniqueToDoList();
        UniqueToDoList uniqueToDoList2 = new UniqueToDoList();
        assertEquals(uniqueToDoList1.hashCode(), uniqueToDoList2.hashCode());

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_E)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_E)));
        assertEquals(uniqueToDoList1, uniqueToDoList2);

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList1.add(new ToDo(new Content(VALID_CONTENT)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList2.add(new ToDo(new Content(VALID_CONTENT)));
        assertEquals(uniqueToDoList1, uniqueToDoList2);
    }

    @Test
    public void hashCode_differentList_differentResult() throws Exception {
        UniqueToDoList uniqueToDoList1 = new UniqueToDoList();
        UniqueToDoList uniqueToDoList2 = new UniqueToDoList();
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_E)));
        assertNotEquals(uniqueToDoList1.hashCode(), uniqueToDoList2.hashCode());

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList1.add(new ToDo(new Content(CONTENT_E)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_B)));
        assertNotEquals(uniqueToDoList1.hashCode(), uniqueToDoList2.hashCode());
    }
}
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void backupAddressBook_typicalAddressBook() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.backupAddressBook(original);
        ReadOnlyAddressBook backedUp = storageManager.readAddressBookBackup().get();
        assertEquals(original, new AddressBook(backedUp));
    }

    @Test
    public void backupAddressBook_withFilePath_typicalAddressBook() throws Exception {
        AddressBook original = getTypicalAddressBook();
        String filePath = storageManager.getAddressBookFilePath();
        storageManager.backupAddressBook(original, filePath);
        ReadOnlyAddressBook backedUp = storageManager.readAddressBookBackup(filePath).get();
        assertEquals(original, new AddressBook(backedUp));
    }

```
###### \java\seedu\address\storage\XmlAdaptedEventTest.java
``` java
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_F1;
import static seedu.address.storage.XmlAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.testutil.Assert;
import seedu.address.testutil.EventBuilder;

public class XmlAdaptedEventTest {
    private static final String INVALID_NAME = "Something?!";
    private static final String INVALID_VENUE = "Some&where";
    private static final String INVALID_DATE = "30/02/2000";
    private static final String INVALID_START_TIME = "2369";
    private static final String INVALID_END_TIME = "23:59";

    private static final String VALID_NAME = VALID_EVENT_NAME_F1;
    private static final String VALID_VENUE = VALID_EVENT_VENUE_F1;
    private static final String VALID_DATE = VALID_EVENT_DATE_F1;
    private static final String VALID_START_TIME = VALID_EVENT_START_TIME_F1;
    private static final String VALID_END_TIME = VALID_EVENT_END_TIME_F1;

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(new EventBuilder().build());
        assertEquals(new EventBuilder().build(), event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(INVALID_NAME, VALID_VENUE, VALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Event.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(null, VALID_VENUE, VALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidVenue_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, INVALID_VENUE, VALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Event.MESSAGE_VENUE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullVenue_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, null, VALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Venue");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, INVALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Event.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, null, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, VALID_DATE, INVALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Event.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, VALID_DATE, null, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "StartTime");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, VALID_DATE, VALID_START_TIME, INVALID_END_TIME);
        String expectedMessage = Event.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, VALID_DATE, VALID_START_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "EndTime");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void equals_test() {
        XmlAdaptedEvent event1 = new XmlAdaptedEvent(new EventBuilder().build());
        XmlAdaptedEvent event2 = new XmlAdaptedEvent(new EventBuilder().withName("Different").build());
        assertEquals(event1, event1);
        assertEquals(event1, new XmlAdaptedEvent(new EventBuilder().build()));
        assertNotEquals(event1, 1);
        assertNotEquals(event1, event2);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPersonTest.java
``` java
    @Test
    public void xmlAdaptedTagEqual() {
        XmlAdaptedTag tag1 = new XmlAdaptedTag("friends");
        XmlAdaptedTag tag2 = new XmlAdaptedTag("friends");
        Tag tag3 = new Tag("friends");
        XmlAdaptedTag tag4 = new XmlAdaptedTag("husband");

        assertEquals(tag1, tag2);
        assertNotEquals(tag1, tag3);
        assertNotEquals(tag2, tag4);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedTagTest.java
``` java
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.storage.XmlAdaptedTag.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class XmlAdaptedTagTest {
    private static final String INVALID_NAME = "Something?!";
    private static final String INVALID_COLOR = "rainbow";

    private static final String VALID_NAME = VALID_TAG_FRIEND;
    private static final String VALID_COLOR = VALID_TAG_COLOR_RED;

    @Test
    public void toModelType_validTagDetails_returnsTag() throws Exception {
        XmlAdaptedTag tag = new XmlAdaptedTag(new Tag(VALID_NAME, VALID_COLOR));
        assertEquals(new Tag(VALID_NAME, VALID_COLOR), tag.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedTag tag = new XmlAdaptedTag(INVALID_NAME);
        String expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedTag tag = new XmlAdaptedTag(null, VALID_COLOR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_invalidColor_throwsIllegalValueException() {
        XmlAdaptedTag tag =
                new XmlAdaptedTag(VALID_NAME, INVALID_COLOR);
        String expectedMessage = Tag.MESSAGE_TAG_COLOR_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_nullColor_throwsIllegalValueException() {
        XmlAdaptedTag tag =
                new XmlAdaptedTag(VALID_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Color");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void equals_test() {
        XmlAdaptedTag tag1 = new XmlAdaptedTag(VALID_TAG_FRIEND);
        XmlAdaptedTag tag2 = new XmlAdaptedTag(VALID_TAG_HUSBAND, VALID_COLOR);
        assertEquals(tag1, tag1);
        assertEquals(tag1, new XmlAdaptedTag(VALID_TAG_FRIEND));
        assertNotEquals(tag1, 1);
        assertNotEquals(tag1, tag2);
    }
}
```
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    private java.util.Optional<ReadOnlyAddressBook> readAddressBookBackup(String filePath) throws Exception {
        return new XmlAddressBookStorage(filePath).readAddressBookBackup(addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void readAddressBookBackup_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBookBackup(null);
    }

    @Test
    public void readAddressBookBackup_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBookBackup("NonExistentFile.xml.backup").isPresent());
    }

```
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    @Test
    public void readAndBackupAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        AddressBook original = getTypicalAddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        //Backup in new file and read back
        xmlAddressBookStorage.backupAddressBook(original, filePath);
        ReadOnlyAddressBook readBack = xmlAddressBookStorage.readAddressBookBackup(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        original.addToDo(TODO_D);
        original.addGroup(GROUP_D);
        xmlAddressBookStorage.backupAddressBook(original, filePath);
        readBack = xmlAddressBookStorage.readAddressBookBackup(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        //Back and read without specifying file path
        original.addPerson(IDA);
        original.addToDo(TODO_E);
        original.addGroup(GROUP_E);
        xmlAddressBookStorage.backupAddressBook(original); //file path not specified
        readBack = xmlAddressBookStorage.readAddressBookBackup().get(); //file path not specified
        assertEquals(original, new AddressBook(readBack));
    }

    @Test
    public void backupAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        backupAddressBook(null, "SomeFile.xml");
    }

    /**
     * Backs up {@code addressBook} at the specified {@code filePath}.
     */
    private void backupAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new XmlAddressBookStorage(filePath).backupAddressBook(addressBook);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void backupAddressBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        backupAddressBook(new AddressBook(), null);
    }
}
```
###### \java\seedu\address\testutil\EventBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.model.event.Event;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "F1 Race";
    public static final String DEFAULT_VENUE = "Marina Bay Street Circuit";
    public static final String DEFAULT_DATE = "19/07/2018";
    public static final String DEFAULT_START_TIME = "1000";
    public static final String DEFAULT_END_TIME = "2100";

    private String name;
    private String venue;
    private String date;
    private String startTime;
    private String endTime;

    public EventBuilder() {
        name = DEFAULT_NAME;
        venue = DEFAULT_VENUE;
        date = DEFAULT_DATE;
        startTime = DEFAULT_START_TIME;
        endTime = DEFAULT_END_TIME;
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        venue = eventToCopy.getVenue();
        date = eventToCopy.getDate();
        startTime = eventToCopy.getStartTime();
        endTime = eventToCopy.getEndTime();
    }

    /**
     * Sets the {@code String name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code String venue} of the {@code Event} that we are building.
     */
    public EventBuilder withVenue(String venue) {
        this.venue = venue;
        return this;
    }

    /**
     * Sets the {@code String date} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code String startTime} of the {@code Event} that we are building.
     */
    public EventBuilder withStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Sets the {@code String endTime} of the {@code Event} that we are building.
     */
    public EventBuilder withEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public Event build() {
        return new Event(name, venue, date, startTime, endTime);
    }

}
```
###### \java\seedu\address\testutil\EventUtil.java
``` java
package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Event;

/**
 * A utility class for Event.
 */
public class EventUtil {

    /**
     * Returns an add command string for adding the {@code event}.
     */
    public static String getAddEventCommand(Event event) {
        return AddEventCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + event.getName() + " ");
        sb.append(PREFIX_VENUE + event.getVenue() + " ");
        sb.append(PREFIX_DATE + event.getDate() + " ");
        sb.append(PREFIX_START_TIME + event.getStartTime() + " ");
        sb.append(PREFIX_END_TIME + event.getEndTime() + " ");
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TypicalEvents.java
``` java
package seedu.address.testutil;

import static seedu.address.testutil.TypicalToDos.getTypicalToDos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event F1RACE = new EventBuilder().build();
    public static final Event GSS = new EventBuilder().withName("Great Singapore Sale").withVenue("Orchard")
            .withDate("09/06/2018").withStartTime("0900").withEndTime("2300").build();
    public static final Event HARIRAYA = new EventBuilder().withName("Hari Raya Haji").withVenue("Singapore")
            .withDate("22/08/2018").withStartTime("0000").withEndTime("2359").build();
    public static final Event ILIGHT = new EventBuilder().withName("iLight").withVenue("Marina Bay")
            .withDate("01/04/2018").withStartTime("1930").withEndTime("2359").build();
    public static final Event NDP = new EventBuilder().withName("National Day Parade").withVenue("Promenade")
            .withDate("09/08/2018").withStartTime("1700").withEndTime("1900").build();

    private TypicalEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : TypicalPersons.getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        for (Event event : getTypicalEvents()) {
            try {
                ab.addEvent(event);
            } catch (DuplicateEventException e) {
                throw new AssertionError("not possible");
            }
        }
        for (ToDo toDo : getTypicalToDos()) {
            try {
                ab.addToDo(toDo);
            } catch (DuplicateToDoException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(F1RACE, GSS, HARIRAYA, ILIGHT, NDP));
    }
}
```
###### \java\seedu\address\ui\CalendarDateTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertDateDisplaysEvent;

import org.junit.Test;

import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

public class CalendarDateTest extends GuiUnitTest {

    @Test
    public void display() {
        Event event = new EventBuilder().build();
        CalendarDate date = new CalendarDate(event);
        uiPartRule.setUiPart(date);
        assertCardDisplay(date, event);
    }

    @Test
    public void equals() {
        Event event = new EventBuilder().build();
        CalendarDate date = new CalendarDate(event);

        // same to-do, same index -> returns true
        CalendarDate copy = new CalendarDate(event);
        assertEquals(date, copy);

        // same object -> returns true
        assertEquals(date, date);

        // null -> returns false
        assertNotEquals(date, null);

        // different types -> returns false
        assertNotEquals(date, 0);

        // different to-do, same index -> returns false
        Event differentEvent = new EventBuilder().withDate("21/12/2012").build();
        assertNotEquals(date, new CalendarDate(differentEvent));
    }

    /**
     * Asserts that {@code toDoCard} displays the details of {@code expectedEvent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(CalendarDate date, Event expectedEvent) {
        guiRobot.pauseForHuman();
        // verify to-do details are displayed correctly
        assertDateDisplaysEvent(expectedEvent, date);
    }
}
```
###### \java\seedu\address\ui\CalendarTest.java
``` java
package seedu.address.ui;

import org.junit.Test;

public class CalendarTest extends GuiUnitTest {

    @Test
    public void equals() {
        /*Calendar calendar = new Calendar(null);
        assertEquals(calendar, new Calendar(null));*/
    }
}
```
###### \java\systemtests\ChangeTagColorCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_BROWN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagNotFoundException;

public class ChangeTagColorCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void changeTagColor() throws Exception {
        Model model = getModel();

        /* ---------- Performing change tag color operation while an unfiltered list is being shown ------------- */

        /* Case: change tag color fields, command with leading spaces, trailing spaces and multiple spaces
         * between each field -> changed
         */
        String command = " " + ChangeTagColorCommand.COMMAND_WORD + "  " + VALID_TAG_FRIEND
                + "  " + VALID_TAG_COLOR_BROWN + " ";
        Tag changedTag = new Tag(VALID_TAG_FRIEND, VALID_TAG_COLOR_BROWN);
        assertCommandSuccess(command, changedTag);

        /* Case: undo changeTagColoring the last tag in the list -> last tag restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo changeTagColoring the last tag in the list -> last tag changeTagColored again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateTag(new Tag(VALID_TAG_FRIEND), changedTag);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: changeTagColor a tag with new values same as existing values -> changeTagColored */
        command = ChangeTagColorCommand.COMMAND_WORD + " " + VALID_TAG_FRIEND + " " + VALID_TAG_COLOR_BROWN;
        assertCommandSuccess(command, changedTag);

        /* Case: tag specified not in list -> rejected
         */
        command = ChangeTagColorCommand.COMMAND_WORD + " " + VALID_TAG_COLOR_RED + " " + VALID_TAG_COLOR_BROWN;
        assertCommandFailure(command, ChangeTagColorCommand.MESSAGE_TAG_NOT_IN_LIST);

        /* Case: color specified is not supported by application -> rejected
         */
        command = ChangeTagColorCommand.COMMAND_WORD + " " + VALID_TAG_FRIEND + " " + INVALID_TAG_COLOR;
        assertCommandFailure(command, Tag.MESSAGE_TAG_COLOR_CONSTRAINTS);
    }

    /**
     * Performs the verification: <br>
     * 1. Asserts that result display node displays the success message of executing {@code ChangeTagColorCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the tag being updated to values
     * specified in {@code changeTagColoredTag}.<br>
     */
    private void assertCommandSuccess(String command, Tag changedTag) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateTag(new Tag(VALID_TAG_FRIEND), changedTag);
            expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (TagNotFoundException tnfe) {
            throw new IllegalArgumentException("Tag isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(ChangeTagColorCommand.MESSAGE_EDIT_TAG_SUCCESS, changedTag.name, changedTag.color));
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command node displays an empty string.<br>
     * 2. Asserts that the result display node displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command node has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command node displays {@code command}.<br>
     * 2. Asserts that result display node displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command node has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\SwitchCommandSystemTest.java
``` java
package systemtests;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.logic.commands.SwitchCommand;
import seedu.address.model.Model;

public class SwitchCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void switchView() {
        final Model calendarModel = getModel();
        final Model timetableModel = getModel();
        timetableModel.switchView();

        /* Case: Application in Calendar view -> switched to Timetable view
         */
        assertCommandSuccess("   " + SwitchCommand.COMMAND_WORD + " view   ", calendarModel, timetableModel);

        assertCommandSuccess("   " + SwitchCommand.COMMAND_WORD + " view   ", timetableModel, calendarModel);
    }

    private void assertCommandSuccess(String command, Model model, Model expectedModel) {
        executeCommand(command);
        assertViewChanged(model, expectedModel);
    }

    private void assertCommandFailure(String command, Model model, Model expectedModel) {
        executeCommand(command);
        assertViewDidNotChange(model, expectedModel);
    }

    private void assertViewChanged(Model model, Model expectedModel) {
        Assert.assertEquals(model.calendarIsViewed(), expectedModel.calendarIsViewed());
    }

    private void assertViewDidNotChange(Model model, Model expectedModel) {
        Assert.assertNotEquals(model.calendarIsViewed(), expectedModel.calendarIsViewed());
    }
}
```
