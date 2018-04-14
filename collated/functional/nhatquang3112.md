# nhatquang3112
###### \java\seedu\address\commons\events\ui\ToDoPanelSelectionChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.ToDoCard;

/**
 * Represents a selection change in the ToDo List Panel
 */
public class ToDoPanelSelectionChangedEvent extends BaseEvent {


    private final ToDoCard newSelection;

    public ToDoPanelSelectionChangedEvent(ToDoCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\AddToDoCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;

/**
 * Adds a to-do to the address book.
 */
public class AddToDoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addToDo";
    public static final String COMMAND_ALIAS = "aTD";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a to-do to the address book. "
            + "Parameters: "
            + "CONTENT\n"
            + "Example: " + COMMAND_WORD + " "
            + "Organize a meeting";

    public static final String MESSAGE_SUCCESS = "New to-do added: %1$s";
    public static final String MESSAGE_DUPLICATE_TODO = "This to-do already exists in the address book";

    private final ToDo toAdd;

    /**
     * Creates an AddToDoCommand to add the specified {@code ToDo}
     */
    public AddToDoCommand(ToDo todo) {
        requireNonNull(todo);
        toAdd = todo;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addToDo(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateToDoException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TODO);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddToDoCommand // instanceof handles nulls
                && toAdd.equals(((AddToDoCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\CheckToDoCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddToDoCommand.MESSAGE_DUPLICATE_TODO;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TODOS;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

/**
 * Check an existing ToDo in the address book as done.
 */
public class CheckToDoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "check";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks the to-do identified as done "
            + "by the index number used in the last to-do listing. "
            + "Status of the to-do will be overwritten as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_CHECK_TODO_SUCCESS = "Checked to-do: %1$s";
    public static final String MESSAGE_NOT_CHECKED = "Checked to-do failed.";

    private final Index index;

    private ToDo toDoToCheck;
    private ToDo checkedToDo;

    /**
     * @param index of the ToDo in the filtered ToDo list to check
     */
    public CheckToDoCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateToDo(toDoToCheck, checkedToDo);
        } catch (ToDoNotFoundException tnfe) {
            throw new AssertionError("The target ToDo cannot be missing");
        } catch (DuplicateToDoException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TODO);
        }
        model.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        return new CommandResult(String.format(MESSAGE_CHECK_TODO_SUCCESS, checkedToDo));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<ToDo> lastShownList = model.getFilteredToDoList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        toDoToCheck = lastShownList.get(index.getZeroBased());
        checkedToDo = createCheckedToDo(toDoToCheck);
    }

    /**
     * Creates and returns a {@code ToDo} with the content of {@code toDoToCheck}
     * checked as done.
     */
    private static ToDo createCheckedToDo(ToDo toDoToCheck) {
        assert toDoToCheck != null;

        Content updatedContent = toDoToCheck.getContent();
        Status updatedStatus = new Status("done");

        return new ToDo(updatedContent, updatedStatus);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CheckToDoCommand)) {
            return false;
        }

        // state check
        CheckToDoCommand e = (CheckToDoCommand) other;
        return index.equals(e.index)
                && Objects.equals(toDoToCheck, e.toDoToCheck);
    }
}
```
###### \java\seedu\address\logic\commands\DeleteToDoCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

/**
 * Deletes a to-do identified using it's last displayed index from the address book.
 */
public class DeleteToDoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteToDo";
    public static final String COMMAND_ALIAS = "dTD";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the to-do identified by the index number used in the last to-do listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TODO_SUCCESS = "Deleted to-do: %1$s";

    private final Index targetIndex;

    private ToDo toDoToDelete;

    public DeleteToDoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(toDoToDelete);
        try {
            model.deleteToDo(toDoToDelete);
        } catch (ToDoNotFoundException tnfe) {
            throw new AssertionError("The target to-do cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TODO_SUCCESS, toDoToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<ToDo> lastShownList = model.getFilteredToDoList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        toDoToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteToDoCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteToDoCommand) other).targetIndex) // state check
                && Objects.equals(this.toDoToDelete, ((DeleteToDoCommand) other).toDoToDelete));
    }
}
```
###### \java\seedu\address\logic\commands\EditToDoCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TODOS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;


/**
 * Edit the content of an existing ToDo in the address book.
 */
public class EditToDoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editToDo";
    public static final String COMMAND_ALIAS = "eTD";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the content of the to-do identified "
            + "by the index number used in the last to-do listing. "
            + "Content of the to-do will be overwritten by the input value.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_CONTENT + "CONTENT\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CONTENT + "Submit presentation scripts";

    public static final String MESSAGE_EDIT_TODO_SUCCESS = "Edited to-do: %1$s";
    public static final String MESSAGE_NOT_EDITED_TODO = "The new to-do content to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TODO = "This to-do already exists in the address book.";

    private final Index index;
    private final EditToDoDescriptor editToDoDescriptor;

    private ToDo toDoToEdit;
    private ToDo editedToDo;

    /**
     * @param index of the to-do in the filtered to-do list to edit
     * @param editToDoDescriptor details to edit the to-do with
     */
    public EditToDoCommand(Index index, EditToDoDescriptor editToDoDescriptor) {
        requireNonNull(index);
        requireNonNull(editToDoDescriptor);

        this.index = index;
        this.editToDoDescriptor = new EditToDoDescriptor(editToDoDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateToDo(toDoToEdit, editedToDo);
        } catch (DuplicateToDoException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TODO);
        } catch (ToDoNotFoundException tnfe) {
            throw new AssertionError("The target to-do cannot be missing");
        }
        model.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        return new CommandResult(String.format(MESSAGE_EDIT_TODO_SUCCESS, editedToDo));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<ToDo> lastShownList = model.getFilteredToDoList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        toDoToEdit = lastShownList.get(index.getZeroBased());
        editedToDo = createEditedToDo(toDoToEdit, editToDoDescriptor);
    }

    /**
     * Creates and returns a {@code ToDo} with the details of {@code toDoToEdit}
     * edited with {@code editToDoDescriptor}
     * Status of toDoToEdit is unchanged.
     */
    private static ToDo createEditedToDo(ToDo toDoToEdit, EditToDoDescriptor editToDoDescriptor) {
        assert toDoToEdit != null;

        Content updatedContent = editToDoDescriptor.getContent().orElse(toDoToEdit.getContent());
        Status updatedStatus = toDoToEdit.getStatus();

        return new ToDo(updatedContent, updatedStatus);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditToDoCommand)) {
            return false;
        }

        // state check
        EditToDoCommand e = (EditToDoCommand) other;
        return index.equals(e.index)
                && editToDoDescriptor.equals(e.editToDoDescriptor)
                && Objects.equals(toDoToEdit, e.toDoToEdit);
    }

    /**
     * Stores the content to edit the to-do with.
     * The content should not be empty.
     */
    public static class EditToDoDescriptor {
        private Content content;

        public EditToDoDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditToDoDescriptor(EditToDoDescriptor toCopy) {
            setContent(toCopy.content);
        }

        /**
         * Returns true if the content field is edited.
         */
        public boolean isContentFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.content);
        }

        public void setContent(Content content) {
            this.content = content;
        }

        public Optional<Content> getContent() {
            return Optional.ofNullable(content);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditToDoDescriptor)) {
                return false;
            }

            // state check
            EditToDoDescriptor e = (EditToDoDescriptor) other;

            return getContent().equals(e.getContent());
        }
    }
}
```
###### \java\seedu\address\logic\commands\UnCheckToDoCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddToDoCommand.MESSAGE_DUPLICATE_TODO;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TODOS;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

/**
 * Uncheck an existing ToDo in the address book as done.
 */
public class UnCheckToDoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "uncheck";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unchecks the to-do identified as undone "
            + "by the index number used in the last to-do listing. "
            + "Status of the to-do will be overwritten as undone.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_UNCHECK_TODO_SUCCESS = "Unchecked to-do: %1$s";
    public static final String MESSAGE_NOT_UNCHECKED = "Unchecked to-do failed.";

    private final Index index;

    private ToDo toDoToUnCheck;
    private ToDo unCheckedToDo;

    /**
     * @param index of the ToDo in the filtered ToDo list to uncheck
     */
    public UnCheckToDoCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateToDo(toDoToUnCheck, unCheckedToDo);
        } catch (ToDoNotFoundException tnfe) {
            throw new AssertionError("The target ToDo cannot be missing");
        } catch (DuplicateToDoException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TODO);
        }
        model.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        return new CommandResult(String.format(MESSAGE_UNCHECK_TODO_SUCCESS, unCheckedToDo));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<ToDo> lastShownList = model.getFilteredToDoList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        toDoToUnCheck = lastShownList.get(index.getZeroBased());
        unCheckedToDo = createUnCheckedToDo(toDoToUnCheck);
    }

    /**
     * Creates and returns a {@code ToDo} with the content of {@code toDoToUnCheck}
     * unchecked as done.
     */
    private static ToDo createUnCheckedToDo(ToDo toDoToUnCheck) {
        assert toDoToUnCheck != null;

        Content updatedContent = toDoToUnCheck.getContent();
        Status updatedStatus = new Status("undone");

        return new ToDo(updatedContent, updatedStatus);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnCheckToDoCommand)) {
            return false;
        }

        // state check
        UnCheckToDoCommand e = (UnCheckToDoCommand) other;
        return index.equals(e.index)
                && Objects.equals(toDoToUnCheck, e.toDoToUnCheck);
    }
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<ToDo> getFilteredToDoList() {
        return model.getFilteredToDoList();
    }

    @Override
    public double getToDoListCompleteRatio() {
        return model.getAddressBook().getToDoListCompleteRatio();
    }
```
###### \java\seedu\address\logic\parser\AddToDoCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.ToDo;

/**
 * Parses input arguments and creates a new AddToDoCommand object
 */
public class AddToDoCommandParser implements Parser<AddToDoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddToDoCommand
     * and returns an AddToDoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddToDoCommand parse(String args) throws ParseException {

        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToDoCommand.MESSAGE_USAGE));
        }

        try {
            Content content = ParserUtil.parseContent(args);

            ToDo todo = new ToDo(content);

            return new AddToDoCommand(todo);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\address\logic\parser\CheckToDoCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CheckToDoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CheckToDoCommand object
 */
public class CheckToDoCommandParser implements Parser<CheckToDoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CheckToDoCommand
     * and returns an CheckToDoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CheckToDoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CheckToDoCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckToDoCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\DeleteToDoCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteToDoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteToDoCommand object
 */
public class DeleteToDoCommandParser implements Parser<DeleteToDoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteToDoCommand
     * and returns an DeleteToDoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteToDoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteToDoCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteToDoCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\EditToDoCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditToDoCommand;
import seedu.address.logic.commands.EditToDoCommand.EditToDoDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditToDoCommand object
 */
public class EditToDoCommandParser implements Parser<EditToDoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditToDoCommand
     * and returns an EditToDoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditToDoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CONTENT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditToDoCommand.MESSAGE_USAGE));
        }

        EditToDoDescriptor editToDoDescriptor = new EditToDoDescriptor();
        try {
            ParserUtil.parseContent(argMultimap.getValue(PREFIX_CONTENT)).ifPresent(editToDoDescriptor::setContent);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editToDoDescriptor.isContentFieldEdited()) {
            throw new ParseException(EditToDoCommand.MESSAGE_NOT_EDITED_TODO);
        }

        return new EditToDoCommand(index, editToDoDescriptor);
    }
}
```
###### \java\seedu\address\logic\parser\UnCheckToDoCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UnCheckToDoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnCheckToDoCommand object
 */
public class UnCheckToDoCommandParser implements Parser<UnCheckToDoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnCheckToDoCommand
     * and returns an UnCheckToDoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnCheckToDoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnCheckToDoCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnCheckToDoCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    //// to-do-level operations
    /**
     * Adds a to-do to the address book.
     *
     * @throws DuplicateToDoException if an equivalent to-do already exists.
     */
    public void addToDo(ToDo todo) throws DuplicateToDoException {
        todos.add(todo);
    }

    @Override
    public ObservableList<ToDo> getToDoList() {
        return todos.asObservableList();
    }

    @Override
    public double getToDoListCompleteRatio() {
        return todos.getCompleteRatio();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     *
     * @throws ToDoNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeToDo(ToDo key) throws ToDoNotFoundException {
        if (todos.remove(key)) {
            return true;
        } else {
            throw new ToDoNotFoundException();
        }
    }

    /**
     * Replaces the given ToDo {@code target} in the list with {@code editedToDo}.
     *
     * @throws DuplicateToDoException if updating the ToDo's details causes the ToDo to be equivalent to
     *                                  another existing ToDo in the list.
     * @throws ToDoNotFoundException  if {@code target} could not be found in the list.
     */
    public void updateToDo(ToDo target, ToDo editedToDo)
            throws DuplicateToDoException, ToDoNotFoundException {
        requireNonNull(editedToDo);

        todos.setToDo(target, editedToDo);
    }

    public void setToDos(List<ToDo> todos) throws DuplicateToDoException {
        this.todos.setToDos(todos);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void deleteToDo(ToDo target) throws ToDoNotFoundException {
        addressBook.removeToDo(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addToDo(ToDo toDo) throws DuplicateToDoException {
        addressBook.addToDo(toDo);
        indicateAddressBookChanged();
    }

    @Override
    public void updateToDo(ToDo target, ToDo editedToDo)
            throws DuplicateToDoException, ToDoNotFoundException {
        requireAllNonNull(target, editedToDo);

        addressBook.updateToDo(target, editedToDo);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    //=========== Filtered ToDo List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ToDo} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ToDo> getFilteredToDoList() {
        return FXCollections.unmodifiableObservableList(filteredToDos);
    }

    @Override
    public void updateFilteredToDoList(Predicate<ToDo> predicate) {
        requireNonNull(predicate);
        filteredToDos.setPredicate(predicate);
    }
```
###### \java\seedu\address\model\person\Detail.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's detail in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDetail(String)}
 */
public class Detail {

    public static final String MESSAGE_DETAIL_CONSTRAINTS =
            "Person detail should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the detail must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DETAIL_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String detail;

    /**
     * Constructs a {@code Detail}.
     *
     * @param detail A valid detail.
     */
    public Detail(String detail) {
        requireNonNull(detail);
        checkArgument(isValidDetail(detail), MESSAGE_DETAIL_CONSTRAINTS);
        this.detail = detail;
    }

    /**
     * Returns true if a given string is a valid person detail.
     */
    public static boolean isValidDetail(String test) {
        return test.matches(DETAIL_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return detail;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Detail // instanceof handles nulls
                && this.detail.equals(((Detail) other).detail)); // state check
    }

    @Override
    public int hashCode() {
        return detail.hashCode();
    }

}
```
###### \java\seedu\address\model\todo\Content.java
``` java
package seedu.address.model.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a ToDo's content in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidContent(String)}
 */
public class Content {

    public static final String MESSAGE_CONTENT_CONSTRAINTS =
            "To-do content should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the content must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CONTENT_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code Content}.
     *
     * @param content A valid content.
     */
    public Content(String content) {
        requireNonNull(content);
        checkArgument(isValidContent(content), MESSAGE_CONTENT_CONSTRAINTS);
        this.value = content;
    }

    /**
     * Returns true if a given string is a valid to-do content.
     */
    public static boolean isValidContent(String test) {
        return test.matches(CONTENT_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Content // instanceof handles nulls
                && this.value.equals(((Content) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\todo\exceptions\DuplicateToDoException.java
``` java
package seedu.address.model.todo.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate ToDo objects.
 */
public class DuplicateToDoException extends DuplicateDataException {
    public DuplicateToDoException() {
        super("Operation would result in duplicate to-dos");
    }
}
```
###### \java\seedu\address\model\todo\exceptions\ToDoNotFoundException.java
``` java
package seedu.address.model.todo.exceptions;

/**
 * Signals that the operation is unable to find the specified ToDo.
 */
public class ToDoNotFoundException extends Exception {}
```
###### \java\seedu\address\model\todo\Status.java
``` java
package seedu.address.model.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a ToDo's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_STATUS_CONSTRAINTS =
            "To-do status should have only two cases: done, undone.It should not be blank";

    /*
     * The first character of the status must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String STATUS_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_STATUS_CONSTRAINTS);
        this.value = status;
    }

    /**
     * Returns true if a given string is a valid to-do status.
     */
    public static boolean isValidStatus(String test) {
        if (test.matches(STATUS_VALIDATION_REGEX) && (
                test.equals("done") || test.equals("undone"))) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.value.equals(((Status) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\todo\ToDo.java
``` java
package seedu.address.model.todo;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a ToDo in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ToDo {

    private final Content content;
    private Status status;

    /**
     * Every field must be present and not null.
     * Constructs an {@code ToDo} with the given details.
     * Status is "undone" by default
     */
    public ToDo(Content content) {
        requireAllNonNull(content);
        this.content = content;
        this.status = new Status("undone");
    }

    /**
     * Every field must be present and not null.
     * Constructs an {@code ToDo} with the given details.
     */
    public ToDo(Content content, Status status) {
        requireAllNonNull(content);
        requireAllNonNull(status);
        this.content = content;
        this.status = status;
    }

    public Content getContent() {
        return content;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ToDo)) {
            return false;
        }

        ToDo otherToDo = (ToDo) other;
        return otherToDo.getContent().equals(this.getContent());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getContent());
        return builder.toString();
    }
}
```
###### \java\seedu\address\model\todo\UniqueToDoList.java
``` java
package seedu.address.model.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

/**
 * A list of to-dos that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see ToDo#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueToDoList implements Iterable<ToDo> {

    private final ObservableList<ToDo> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent to-do as the given argument.
     */
    public boolean contains(ToDo toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Returns the ratio of done to-dos over the total number of to-dos in the list.
     */
    public double getCompleteRatio() {
        int numberOfDoneToDos = 0;
        for (ToDo todo: internalList) {
            if (todo.getStatus().toString().equals("done")) {
                numberOfDoneToDos++;
            }
        }
        double completeRatio = ((double) numberOfDoneToDos) / ((double) internalList.size());
        return completeRatio;
    }

    /**
     * Adds a to-do to the list.
     *
     * @throws DuplicateToDoException if the to-do to add is a duplicate of an existing to-do in the list.
     */
    public void add(ToDo toAdd) throws DuplicateToDoException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateToDoException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the to-do {@code target} in the list with {@code editedToDo}.
     *
     * @throws DuplicateToDoException if the replacement is equivalent to another existing to-do in the list.
     * @throws ToDoNotFoundException if {@code target} could not be found in the list.
     */
    public void setToDo(ToDo target, ToDo editedToDo)
            throws DuplicateToDoException, ToDoNotFoundException {
        requireNonNull(editedToDo);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ToDoNotFoundException();
        }

        if (!target.equals(editedToDo) && internalList.contains(editedToDo)) {
            throw new DuplicateToDoException();
        }

        internalList.set(index, editedToDo);
    }

    public void setToDos(UniqueToDoList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setToDos(List<ToDo> todos) throws DuplicateToDoException {
        requireAllNonNull(todos);
        final UniqueToDoList replacement = new UniqueToDoList();
        for (final ToDo todo : todos) {
            replacement.add(todo);
        }
        setToDos(replacement);
    }

    /**
     * Removes the equivalent to-do from the list.
     *
     * @throws ToDoNotFoundException if no such to-do could be found in the list.
     */
    public boolean remove(ToDo toRemove) throws ToDoNotFoundException {
        requireNonNull(toRemove);
        final boolean toDoFoundAndDeleted = internalList.remove(toRemove);
        if (!toDoFoundAndDeleted) {
            throw new ToDoNotFoundException();
        }
        return toDoFoundAndDeleted;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ToDo> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<ToDo> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueToDoList // instanceof handles nulls
                && this.internalList.equals(((UniqueToDoList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedToDo.java
``` java
package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;

/**
 * JAXB-friendly version of the To-do.
 */
public class XmlAdaptedToDo {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ToDo's %s field is missing!";

    @XmlElement(required = true)
    private String content;

    @XmlElement(required = true)
    private String status;

    /**
     * Constructs an XmlAdaptedToDo.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedToDo() {}

    /**
     * Constructs an {@code XmlAdaptedToDo} with the given details.
     */
    public XmlAdaptedToDo(String content, String status) {
        this.content = content;
        this.status = status;
    }

    /**
     * Constructs an {@code XmlAdaptedToDo} with the given details.
     * Status is "undone" by default
     */
    public XmlAdaptedToDo(String content) {
        this.content = content;
        this.status = "undone";
    }

    /**
     * Converts a given To-do into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedToDo
     */
    public XmlAdaptedToDo(ToDo source) {

        content = source.getContent().value;
        status = source.getStatus().value;
    }

    /**
     * Converts this jaxb-friendly adapted to-do object into the model's To-do object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted to-do
     */
    public ToDo toModelType() throws IllegalValueException {
        if (this.content == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName()));
        }
        if (!Content.isValidContent(this.content)) {
            throw new IllegalValueException(Content.MESSAGE_CONTENT_CONSTRAINTS);
        }
        final Content content = new Content(this.content);

        if (this.status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(this.status)) {
            throw new IllegalValueException(Status.MESSAGE_STATUS_CONSTRAINTS);
        }
        final Status status = new Status(this.status);

        return new ToDo(content, status);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedToDo)) {
            return false;
        }

        XmlAdaptedToDo otherToDo = (XmlAdaptedToDo) other;
        return Objects.equals(content, otherToDo.content);
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        toDoListPanel = new ToDoListPanel(logic.getFilteredToDoList());
        toDoListPanelPlaceholder.getChildren().add(toDoListPanel.getRoot());

        progressIndicatorLabel = new Label(PROGRESS_INDICATOR_LABEL_NAME);
        progressIndicatorLabel.setStyle(PROGRESS_INDICATOR_LABEL_COLOR);
        progressIndicatorPlaceholder.getChildren().add(progressIndicatorLabel);

        progressIndicator = new ProgressIndicator();
        progressIndicator.setStyle(PROGRESS_INDICATOR_COLOR);
        progressIndicator.setPrefSize(PROGRESS_INDICATOR_WIDTH, PROGRESS_INDICATOR_HEIGHT);
        progressIndicator.setProgress(logic.getToDoListCompleteRatio());
        progressIndicatorPlaceholder.getChildren().add(progressIndicator);
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    void updateProgressIndicator() {
        progressIndicator.setProgress(logic.getToDoListCompleteRatio());
    }
```
###### \java\seedu\address\ui\ProgressIndicatorProperties.java
``` java
package seedu.address.ui;

/**
 * Contains properties to initialize Progress Indicator and Progress Indicator Label
 */
public class ProgressIndicatorProperties {
    public static final String PROGRESS_INDICATOR_LABEL_NAME = "TO-DO COMPLETION";
    public static final String PROGRESS_INDICATOR_LABEL_COLOR = "-fx-text-fill: black;";
    public static final String PROGRESS_INDICATOR_COLOR = "-fx-progress-color: #4DA194;";
    public static final int PROGRESS_INDICATOR_WIDTH = 150;
    public static final int PROGRESS_INDICATOR_HEIGHT = 150;
}
```
###### \java\seedu\address\ui\ToDoCard.java
``` java
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.todo.ToDo;

/**
 * An UI component that displays information of a {@code ToDo}.
 */
public class ToDoCard extends UiPart<Region> {

    private static final String FXML = "ToDoListCard.fxml";

    public final ToDo toDo;

    @FXML
    private HBox cardPane;
    @FXML
    private Label content;
    @FXML
    private Label status;
    @FXML
    private Label id;

    public ToDoCard(ToDo toDo, int displayedIndex) {
        super(FXML);
        this.toDo = toDo;
        id.setText(displayedIndex + ". ");
        content.setText(toDo.getContent().value);
        status.setText(toDo.getStatus().value);
    }

    public boolean isDone() {
        return toDo.getStatus().value.equals("done");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ToDoCard)) {
            return false;
        }

        // state check
        ToDoCard card = (ToDoCard) other;
        return id.getText().equals(card.id.getText())
                && toDo.equals(card.toDo);
    }
}
```
###### \java\seedu\address\ui\ToDoListPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ToDoPanelSelectionChangedEvent;
import seedu.address.model.todo.ToDo;

/**
 * Panel containing the list of to-dos.
 */
public class ToDoListPanel extends UiPart<Region> {
    private static final String FXML = "ToDoListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ToDoListPanel.class);

    @FXML
    private ListView<ToDoCard> toDoListView;

    public ToDoListPanel(ObservableList<ToDo> toDoList) {
        super(FXML);
        setConnections(toDoList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ToDo> toDoList) {
        ObservableList<ToDoCard> mappedList = EasyBind.map(
                toDoList, (toDo) -> new ToDoCard(toDo, toDoList.indexOf(toDo) + 1));
        toDoListView.setItems(mappedList);
        toDoListView.setCellFactory(listView -> new ToDoListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        toDoListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in to-do list panel changed to : '" + newValue + "'");
                        raise(new ToDoPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ToDoCard}.
     */
    class ToDoListViewCell extends ListCell<ToDoCard> {

        @Override
        protected void updateItem(ToDoCard toDo, boolean empty) {
            super.updateItem(toDo, empty);

            if (empty || toDo == null) {
                setGraphic(null);
                setText(null);
                return;
            }

            this.getStylesheets().clear();
            if (toDo.isDone()) {
                this.getStylesheets().add("view/ToDoDone.css");
            } else {
                this.getStylesheets().add("view/ToDoUnDone.css");
            }
            setGraphic(toDo.getRoot());
        }
    }
}
```
###### \resources\view\ToDoDone.css
``` css
.list-cell:filled:even {
    -fx-background-color: #4DA194;
    -fx-background-radius: 25px;
    -fx-border-radius: 20px;
    -fx-border-width: 2px;
    -fx-border-color: derive(#1d1d1d, 20%);
}

.list-cell:filled:odd {
    -fx-background-color: #4DA194;
    -fx-background-radius: 25px;
    -fx-border-radius: 20px;
    -fx-border-width: 2px;
    -fx-border-color: derive(#1d1d1d, 20%);
}

.list-cell .label {
    -fx-text-fill: #DEF0EE;
}
```
###### \resources\view\ToDoListCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.VBox?>
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
          <padding>
            <Insets top="5" right="5" bottom="5" left="15" />
          </padding>
      <HBox spacing="5" alignment="CENTER_LEFT">
        <Label fx:id="id" styleClass="cell_big_label">
          <minWidth>
            <!-- Ensures that the label text is never truncated -->
            <Region fx:constant="USE_PREF_SIZE" />
          </minWidth>
        </Label>
        <Label fx:id="content" text="\$content" styleClass="cell_big_label" />
      </HBox>
      <Label fx:id="status" text="\$status" styleClass="cell_small_label" />
    </VBox>
  </GridPane>
</HBox>
```
###### \resources\view\ToDoListPanel.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.text.Font?>
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <Label  prefHeight="23.0" prefWidth="95.0" text="TO-DO LIST" >
        <font>
            <Font name="System Bold" size="16.0" />
        </font>
    </Label>
    <ListView fx:id="toDoListView" VBox.vgrow="ALWAYS" />
</VBox>
```
###### \resources\view\ToDoUnDone.css
``` css
.list-cell:filled:even {
    -fx-background-color: #EFEFEF;
    -fx-background-radius: 25px;
    -fx-border-radius: 20px;
    -fx-border-width: 2px;
    -fx-border-color: derive(#1d1d1d, 20%);
}

.list-cell:filled:odd {
    -fx-background-color: #EFEFEF;
    -fx-background-radius: 25px;
    -fx-border-radius: 20px;
    -fx-border-width: 2px;
    -fx-border-color: derive(#1d1d1d, 20%);
}

.list-cell .label {
    -fx-text-fill: black;
}
```
